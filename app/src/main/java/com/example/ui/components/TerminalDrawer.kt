package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.*
import com.example.viewmodel.Screen

private const val LOGO_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuDtorrrV9sa5Lbm87qQDUnLCt9gbZ0XmCOVWiuV1n6hRK35h7jjtWFYsQjQ5p_ia8v7ASCUGTlaW4QrtuHziRMFsJ5Tw2eFvjojOMo3hTZtYD3GV0UOzIjPewep--de6PiROHFPRH6fzL6uRjQv0XKhj_QvR-lCF9WxrezzTA1xcD2lvEwy2IrzOt3Li_JT8wWCTG0PciRqzsJ9ru5emHG_k7tDOIK_ZiXqF91WkWNxcorAz_-QbGN7jg"

@Composable
fun TerminalDrawerContent(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    onClose: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(310.dp)
            .background(SurfaceContainerLow)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Drawer Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainer)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = LOGO_URL,
                    contentDescription = "EconPulse Logo",
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Text(
                    text = "TERMINAL INDEX",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.01).sp
                    ),
                    color = OnSurface
                )
            }
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(36.dp)
                    .testTag("drawer_close_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Drawer",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Scrollable List of Navigation Items
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Group: Core Engines
            DrawerSectionHeader("CORE ENGINES")
            DrawerItem(
                label = "Overview",
                icon = Icons.Default.Dashboard,
                isSelected = currentScreen == Screen.Terminal,
                onClick = { onNavigate(Screen.Terminal) }
            )
            DrawerItem(
                label = "Indian Markets",
                icon = Icons.Default.QueryStats,
                isSelected = currentScreen == Screen.Markets,
                onClick = { onNavigate(Screen.Markets) }
            )
            DrawerItem(
                label = "US Markets",
                icon = Icons.Default.ShowChart,
                isSelected = false,
                onClick = {
                    onNavigate(Screen.Markets)
                    onItemClick("US Markets Selected")
                }
            )
            DrawerItem(
                label = "Commodities",
                icon = Icons.Default.Toll,
                isSelected = currentScreen == Screen.Commodities,
                onClick = { onNavigate(Screen.Commodities) }
            )
            DrawerItem(
                label = "Oil Intelligence",
                icon = Icons.Default.LocalGasStation,
                isSelected = currentScreen == Screen.Commodities,
                onClick = { onNavigate(Screen.Commodities) }
            )

            // Group: Analytics & Macro
            DrawerSectionHeader("ANALYTICS & MACRO")
            DrawerItem(
                label = "News & Geopolitics",
                icon = Icons.Default.Public,
                isSelected = false,
                onClick = { onItemClick("News & Geopolitics active in Terminal Wire") }
            )
            DrawerItem(
                label = "Economic Calendar",
                icon = Icons.Default.CalendarMonth,
                isSelected = false,
                onClick = { onItemClick("Economic Calendar synced with RBI/Fed data") }
            )
            DrawerItem(
                label = "AI Analyst Room",
                icon = Icons.Default.Psychology,
                isSelected = currentScreen == Screen.AiAnalyst,
                onClick = { onNavigate(Screen.AiAnalyst) }
            )
            DrawerItem(
                label = "Correlation Lab",
                icon = Icons.Default.Hub,
                isSelected = false,
                onClick = { onItemClick("Correlation Lab active: 10-Yr Beta matrix updated") }
            )
            DrawerItem(
                label = "Stock Research",
                icon = Icons.Default.Science,
                isSelected = currentScreen == Screen.Research,
                onClick = { onNavigate(Screen.Research) }
            )

            // Group: System
            DrawerSectionHeader("SYSTEM")
            DrawerItem(
                label = "Watchlists",
                icon = Icons.Default.FormatListBulleted,
                isSelected = false,
                onClick = {
                    onNavigate(Screen.Markets)
                    onItemClick("Switched to active Watchlists")
                }
            )
            DrawerItem(
                label = "Terminal Settings",
                icon = Icons.Default.Tune,
                isSelected = false,
                onClick = { onItemClick("Terminal Settings: FIX 4.4, Dark High-Density, Low Latency active") }
            )
        }

        // Drawer Footer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainer)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(BullishEmerald)
                )
                Text(
                    text = "DESK LATENCY: 12ms",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = OnSurfaceVariant
                )
            }
            Text(
                text = "FIX 4.4",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                ),
                color = TelemetryCyan
            )
        }
    }
}

@Composable
private fun DrawerSectionHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceContainerLowest)
            .padding(horizontal = 16.dp, vertical = 7.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.08.sp
            ),
            color = Outline
        )
    }
}

@Composable
private fun DrawerItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (isSelected) SurfaceContainerHigh else Color.Transparent
    val tint = if (isSelected) TelemetryCyan else OnSurfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(19.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 12.5.sp
            ),
            color = tint
        )
    }
}
