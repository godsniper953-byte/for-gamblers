package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MarketRepository
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: String) {
    object Markets : Screen("markets", "Markets", "stacked_line_chart")
    object Terminal : Screen("terminal", "Terminal", "terminal")
    object Commodities : Screen("commodities", "Commodities", "bar_chart")
    object AiAnalyst : Screen("ai_analyst", "AI Analyst", "smart_toy")
    object Research : Screen("research", "Research", "science")
}

data class OrderDialogState(
    val isOpen: Boolean = false,
    val isBuy: Boolean = true,
    val symbol: String = "",
    val price: String = "",
    val quantity: Int = 50,
    val orderType: String = "LIMIT"
)

data class TerminalUiState(
    val currentScreen: Screen = Screen.Terminal,
    val isDrawerOpen: Boolean = false,
    val isSearchOpen: Boolean = false,
    val isNotificationsOpen: Boolean = false,
    val searchQuery: String = "",
    val isFlashWireExpanded: Boolean = false,
    val flashWireMessage: String = MarketRepository.flashWire.headline,
    val statusPingText: String = "12ms LATENCY",
    
    // Markets Screen
    val selectedStock: StockQuote = MarketRepository.stockQuotes[0],
    val selectedTimeframe: String = "1D",
    val selectedChartType: String = "Candlestick",
    val activeIndicators: Set<String> = setOf("EMA 20/50", "RSI (14)", "MACD"),
    val selectedWatchlistCategory: String = "in", // "in", "us", "macro"
    val selectedScreenerFilter: String = "Top Gainers",
    val orderDialog: OrderDialogState = OrderDialogState(),
    val snackbarMessage: String? = null,
    
    // Oil Screen
    val oilCurveView: String = "curve", // "curve", "deficit"
    val selectedSectorDetail: CorporateMarginSector? = null,
    
    // AI Analyst Screen
    val selectedAiPreset: AiMacroQueryPreset = MarketRepository.aiQueryPresets[0],
    val aiCustomPrompt: String = "",
    val isSynthesizing: Boolean = false,
    val activeFeedbackPositive: Boolean? = null
)

class TerminalViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TerminalUiState())
    val uiState: StateFlow<TerminalUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen, isDrawerOpen = false) }
    }

    fun toggleDrawer(open: Boolean? = null) {
        _uiState.update { it.copy(isDrawerOpen = open ?: !it.isDrawerOpen) }
    }

    fun toggleSearch(open: Boolean) {
        _uiState.update { it.copy(isSearchOpen = open, searchQuery = if (!open) "" else it.searchQuery) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleNotifications(open: Boolean) {
        _uiState.update { it.copy(isNotificationsOpen = open) }
    }

    fun toggleFlashWire() {
        _uiState.update { it.copy(isFlashWireExpanded = !it.isFlashWireExpanded) }
    }

    fun triggerQuickAction(actionName: String) {
        _uiState.update {
            it.copy(
                flashWireMessage = "[EXECUTING] Querying institutional matrices for ${actionName.uppercase()}... Latency: 11ms"
            )
        }
        viewModelScope.launch {
            delay(3000)
            _uiState.update {
                it.copy(flashWireMessage = MarketRepository.flashWire.headline)
            }
        }
    }

    // Markets actions
    fun selectStock(symbol: String) {
        val found = MarketRepository.stockQuotes.firstOrNull { it.symbol.equals(symbol, ignoreCase = true) }
        if (found != null) {
            _uiState.update { it.copy(selectedStock = found, isSearchOpen = false) }
        }
    }

    fun setTimeframe(tf: String) {
        _uiState.update { it.copy(selectedTimeframe = tf) }
    }

    fun setChartType(type: String) {
        _uiState.update { it.copy(selectedChartType = type) }
    }

    fun toggleIndicator(indicator: String) {
        _uiState.update {
            val set = it.activeIndicators.toMutableSet()
            if (set.contains(indicator)) set.remove(indicator) else set.add(indicator)
            it.copy(activeIndicators = set)
        }
    }

    fun setWatchlistCategory(cat: String) {
        _uiState.update { it.copy(selectedWatchlistCategory = cat) }
    }

    fun setScreenerFilter(filter: String) {
        _uiState.update { it.copy(selectedScreenerFilter = filter) }
    }

    fun openOrderDialog(isBuy: Boolean) {
        val stock = _uiState.value.selectedStock
        val price = if (isBuy) {
            stock.bids.firstOrNull()?.price ?: stock.price
        } else {
            stock.asks.firstOrNull()?.price ?: stock.price
        }
        _uiState.update {
            it.copy(
                orderDialog = OrderDialogState(
                    isOpen = true,
                    isBuy = isBuy,
                    symbol = stock.symbol,
                    price = price,
                    quantity = 50
                )
            )
        }
    }

    fun closeOrderDialog() {
        _uiState.update { it.copy(orderDialog = it.orderDialog.copy(isOpen = false)) }
    }

    fun updateOrderQuantity(qty: Int) {
        if (qty > 0) {
            _uiState.update { it.copy(orderDialog = it.orderDialog.copy(quantity = qty)) }
        }
    }

    fun updateOrderPrice(price: String) {
        _uiState.update { it.copy(orderDialog = it.orderDialog.copy(price = price)) }
    }

    fun executeOrder() {
        val dialog = _uiState.value.orderDialog
        val side = if (dialog.isBuy) "BUY" else "SELL"
        val msg = "[EXECUTION FILL] $side ${dialog.quantity}x ${dialog.symbol} @ ${dialog.price} filled instantly on NSE."
        _uiState.update {
            it.copy(
                orderDialog = it.orderDialog.copy(isOpen = false),
                snackbarMessage = msg
            )
        }
    }

    fun dismissSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    // Oil actions
    fun setOilCurveView(view: String) {
        _uiState.update { it.copy(oilCurveView = view) }
    }

    fun selectSectorDetail(sector: CorporateMarginSector?) {
        _uiState.update { it.copy(selectedSectorDetail = sector) }
    }

    // AI Analyst actions
    fun selectAiPreset(presetId: String) {
        val found = MarketRepository.aiQueryPresets.firstOrNull { it.id == presetId }
        if (found != null) {
            _uiState.update {
                it.copy(
                    selectedAiPreset = found,
                    activeFeedbackPositive = null
                )
            }
        }
    }

    fun updateCustomPrompt(text: String) {
        _uiState.update { it.copy(aiCustomPrompt = text) }
    }

    fun submitCustomPrompt(prompt: String = _uiState.value.aiCustomPrompt) {
        if (prompt.isBlank()) return
        _uiState.update { it.copy(isSynthesizing = true) }
        viewModelScope.launch {
            delay(800)
            // Create dynamic synthesis or map to best preset
            val active = _uiState.value.selectedAiPreset
            val updatedPreset = active.copy(
                queryText = "\"$prompt\"",
                confidencePercent = "89.5%"
            )
            _uiState.update {
                it.copy(
                    selectedAiPreset = updatedPreset,
                    isSynthesizing = false,
                    aiCustomPrompt = "",
                    snackbarMessage = "AI Macro Synthesis calibrated for: $prompt"
                )
            }
        }
    }

    fun setFeedback(positive: Boolean) {
        _uiState.update { it.copy(activeFeedbackPositive = positive) }
        showToast(if (positive) "Telemetry feedback recorded: +1 Quality" else "Feedback noted for model weight recalibration")
    }

    fun showToast(msg: String) {
        _uiState.update { it.copy(snackbarMessage = msg) }
    }
}
