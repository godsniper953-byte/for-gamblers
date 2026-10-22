package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.Screen

@Composable
fun TerminalBottomNav(
    currentScreen: Screen,
    onScreenSelect: (Screen) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SurfaceBase.copy(alpha = 0.98f),
        tonalElevation = 8.dp
    ) {
        Column {
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f), thickness = 0.5.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    label = "Markets",
                    icon = Icons.Default.ShowChart,
                    isSelected = currentScreen == Screen.Markets,
                    onClick = { onScreenSelect(Screen.Markets) },
                    testTag = "nav_markets"
                )
                BottomNavItem(
                    label = "Terminal",
                    icon = Icons.Default.Terminal,
                    isSelected = currentScreen == Screen.Terminal,
                    onClick = { onScreenSelect(Screen.Terminal) },
                    testTag = "nav_terminal"
                )
                BottomNavItem(
                    label = "Commodities",
                    icon = Icons.Default.BarChart,
                    isSelected = currentScreen == Screen.Commodities,
                    onClick = { onScreenSelect(Screen.Commodities) },
                    testTag = "nav_commodities"
                )
                BottomNavItem(
                    label = "AI Analyst",
                    icon = Icons.Default.Psychology,
                    isSelected = currentScreen == Screen.AiAnalyst,
                    onClick = { onScreenSelect(Screen.AiAnalyst) },
                    testTag = "nav_ai_analyst"
                )
                BottomNavItem(
                    label = "Research",
                    icon = Icons.Default.Science,
                    isSelected = currentScreen == Screen.Research,
                    onClick = { onScreenSelect(Screen.Research) },
                    testTag = "nav_research"
                )
                BottomNavItem(
                    label = "More",
                    icon = Icons.Default.MoreHoriz,
                    isSelected = false,
                    onClick = onMoreClick,
                    testTag = "nav_more"
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val contentColor = if (isSelected) TelemetryCyan else OnSurfaceVariant
    val primaryCyan = TelemetryCyan
    
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(56.dp)
            .clickable(onClick = onClick)
            .testTag(testTag)
            .then(
                if (isSelected) {
                    Modifier
                        .background(SurfaceContainerHigh.copy(alpha = 0.4f))
                        .drawBehind {
                            drawLine(
                                color = primaryCyan,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 3.dp.toPx()
                            )
                        }
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                ),
                color = contentColor,
                maxLines = 1
            )
        }
    }
}
