package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurfaceBase
import com.example.viewmodel.Screen
import com.example.viewmodel.TerminalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TerminalApp()
            }
        }
    }
}

@Composable
fun TerminalApp(
    viewModel: TerminalViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Sync drawerState with uiState
    LaunchedEffect(uiState.isDrawerOpen) {
        if (uiState.isDrawerOpen && drawerState.isClosed) {
            drawerState.open()
        } else if (!uiState.isDrawerOpen && drawerState.isOpen) {
            drawerState.close()
        }
    }

    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen != uiState.isDrawerOpen) {
            viewModel.toggleDrawer(drawerState.isOpen)
        }
    }

    // Snackbar notifications
    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.dismissSnackbar()
        }
    }

    // Back handler
    BackHandler(enabled = drawerState.isOpen || uiState.currentScreen != Screen.Terminal) {
        if (drawerState.isOpen) {
            viewModel.toggleDrawer(false)
        } else if (uiState.currentScreen != Screen.Terminal) {
            viewModel.navigateTo(Screen.Terminal)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = SurfaceBase
            ) {
                TerminalDrawerContent(
                    currentScreen = uiState.currentScreen,
                    onNavigate = { screen -> viewModel.navigateTo(screen) },
                    onClose = { viewModel.toggleDrawer(false) },
                    onItemClick = { note ->
                        viewModel.toggleDrawer(false)
                        viewModel.showToast(note)
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceBase),
            containerColor = SurfaceBase,
            topBar = {
                TerminalTopBar(
                    currentScreen = uiState.currentScreen,
                    onDrawerToggle = { viewModel.toggleDrawer() },
                    onSearchClick = { viewModel.toggleSearch(true) },
                    onNotificationsClick = { viewModel.toggleNotifications(true) },
                    onTickerSelect = { symbol ->
                        viewModel.selectStock(symbol)
                        viewModel.navigateTo(Screen.Markets)
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                TerminalBottomNav(
                    currentScreen = uiState.currentScreen,
                    onScreenSelect = { screen -> viewModel.navigateTo(screen) },
                    onMoreClick = { viewModel.toggleDrawer(true) }
                )
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(bottom = 70.dp)
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(SurfaceBase)
            ) {
                Crossfade(targetState = uiState.currentScreen, label = "screen_crossfade") { screen ->
                    when (screen) {
                        Screen.Terminal -> TerminalOverviewScreen(
                            uiState = uiState,
                            onToggleFlashWire = { viewModel.toggleFlashWire() },
                            onQuickAction = { action -> viewModel.triggerQuickAction(action) },
                            onIndexClick = { symbol ->
                                viewModel.selectStock(symbol)
                                viewModel.navigateTo(Screen.Markets)
                            },
                            onCommodityClick = {
                                viewModel.navigateTo(Screen.Commodities)
                            },
                            onDeepDiveClick = {
                                viewModel.navigateTo(Screen.AiAnalyst)
                            }
                        )
                        Screen.Markets -> MarketsScreen(
                            uiState = uiState,
                            onSelectStock = { symbol -> viewModel.selectStock(symbol) },
                            onTimeframeChange = { tf -> viewModel.setTimeframe(tf) },
                            onChartTypeChange = { type -> viewModel.setChartType(type) },
                            onToggleIndicator = { ind -> viewModel.toggleIndicator(ind) },
                            onWatchlistCategoryChange = { cat -> viewModel.setWatchlistCategory(cat) },
                            onScreenerFilterChange = { filter ->
                                viewModel.setScreenerFilter(filter)
                                viewModel.showToast("Filter applied: $filter")
                            },
                            onOpenSearch = { viewModel.toggleSearch(true) },
                            onBuyClick = { viewModel.openOrderDialog(isBuy = true) },
                            onSellClick = { viewModel.openOrderDialog(isBuy = false) },
                            onShareQuote = { sym -> viewModel.showToast("Telemetry export ready for $sym") },
                            onToggleWatchlistStar = { sym -> viewModel.showToast("$sym watchlist preference saved") }
                        )
                        Screen.Commodities -> OilIntelligenceScreen(
                            uiState = uiState,
                            onCurveViewChange = { v -> viewModel.setOilCurveView(v) },
                            onSectorClick = { sector ->
                                viewModel.selectSectorDetail(sector)
                                viewModel.showToast("${sector.sectorName}: ${sector.verdict} (${sector.verdictSub})")
                            },
                            onShareTelemetry = { viewModel.showToast("Oil Intelligence matrix copied") }
                        )
                        Screen.AiAnalyst -> AiAnalystScreen(
                            uiState = uiState,
                            onSelectPreset = { id -> viewModel.selectAiPreset(id) },
                            onCustomPromptChange = { p -> viewModel.updateCustomPrompt(p) },
                            onSubmitPrompt = { p -> viewModel.submitCustomPrompt(p) },
                            onFeedback = { pos -> viewModel.setFeedback(pos) },
                            onCopyBrief = { viewModel.showToast("Institutional synthesis brief copied") },
                            onExportCsv = { viewModel.showToast("Telemetry CSV exported to Downloads") },
                            onSendToDesk = { viewModel.showToast("Macro brief routed to Trading Desk") }
                        )
                        Screen.Research -> ResearchScreen(
                            uiState = uiState,
                            onSelectStock = { sym -> viewModel.selectStock(sym) }
                        )
                    }
                }

                // Search Dialog
                if (uiState.isSearchOpen) {
                    SearchDialog(
                        query = uiState.searchQuery,
                        onQueryChange = { q -> viewModel.setSearchQuery(q) },
                        onSelectStock = { s ->
                            viewModel.selectStock(s.symbol)
                            viewModel.navigateTo(Screen.Markets)
                        },
                        onDismiss = { viewModel.toggleSearch(false) }
                    )
                }

                // Notifications Dialog
                if (uiState.isNotificationsOpen) {
                    NotificationsDialog(
                        onDismiss = { viewModel.toggleNotifications(false) }
                    )
                }

                // Buy / Sell Order Execution Dialog
                if (uiState.orderDialog.isOpen) {
                    OrderExecutionDialog(
                        state = uiState.orderDialog,
                        onQuantityChange = { qty -> viewModel.updateOrderQuantity(qty) },
                        onPriceChange = { p -> viewModel.updateOrderPrice(p) },
                        onConfirm = { viewModel.executeOrder() },
                        onDismiss = { viewModel.closeOrderDialog() }
                    )
                }
            }
        }
    }
}
