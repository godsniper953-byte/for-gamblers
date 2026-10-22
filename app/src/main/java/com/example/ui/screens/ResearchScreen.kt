package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.StockQuote
import com.example.ui.components.SparklineView
import com.example.ui.theme.*
import com.example.viewmodel.TerminalUiState

@Composable
fun ResearchScreen(
    uiState: TerminalUiState,
    onSelectStock: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val stock = uiState.selectedStock

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBase)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
    ) {
        // Research Lab Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Science, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(18.dp))
                            Text(
                                text = "QUANTITATIVE RESEARCH LAB",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp
                                ),
                                color = OnSurface
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHigh, RoundedCornerShape(3.dp))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "MONTE CARLO v3.8",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 8.5.sp, fontWeight = FontWeight.Bold),
                                color = TelemetryCyan
                            )
                        }
                    }

                    Text(
                        text = "Active Target: ${stock.name} (${stock.symbol})",
                        style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
                        color = BullishEmerald
                    )
                }
            }
        }

        // Correlation Matrix Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cross-Asset Correlation Coefficients",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                            color = OnSurface
                        )
                        Text(
                            text = "3-YR ROLLING",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                            color = Outline
                        )
                    }

                    val correlations = listOf(
                        Pair("NIFTY 50 Index", "+0.78"),
                        Pair("Brent Crude Spot", "-0.34"),
                        Pair("USD/INR Spot", "-0.18"),
                        Pair("US 10Y Yield", "+0.22"),
                        Pair("India VIX Index", "-0.62")
                    )

                    correlations.forEach { (asset, coef) ->
                        val isPos = coef.startsWith("+")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(horizontal = 10.dp, vertical = 7.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(asset, color = OnSurface, fontSize = 11.5.sp, style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = coef,
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.5.sp
                                ),
                                color = if (isPos) BullishEmerald else BearishCrimson
                            )
                        }
                    }
                }
            }
        }

        // Valuation & Multiples Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Valuation & Factor Telemetry",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                        color = OnSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ValuationBox("P/E MULTIPLE", stock.peRatio, "Industry: 24.1", Modifier.weight(1f))
                        ValuationBox("EV / EBITDA", "16.8x", "Percentile: 72%", Modifier.weight(1f))
                        ValuationBox("PRICE / BOOK", "2.84x", "ROE: 14.8%", Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ValuationBox(
    title: String,
    value: String,
    sub: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainer)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(title, color = Outline, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
            Text(value, color = OnSurface, fontSize = 13.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            Text(sub, color = TelemetryCyan, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
        }
    }
}
