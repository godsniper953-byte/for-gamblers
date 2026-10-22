package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketRepository
import com.example.model.CommodityItem
import com.example.model.GlobalIndex
import com.example.model.SectorItem
import com.example.ui.components.SparklineView
import com.example.ui.theme.*
import com.example.viewmodel.Screen
import com.example.viewmodel.TerminalUiState

@Composable
fun TerminalOverviewScreen(
    uiState: TerminalUiState,
    onToggleFlashWire: () -> Unit,
    onQuickAction: (String) -> Unit,
    onIndexClick: (String) -> Unit,
    onCommodityClick: (String) -> Unit,
    onDeepDiveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBase)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
    ) {
        // 1. Breaking Macro Live Wire Pill
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onToggleFlashWire)
                    .testTag("breaking_wire_card"),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(TertiaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = OnTertiary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                            Text(
                                text = "FLASH",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = AmberAdvisory
                            )
                            Text(
                                text = uiState.flashWireMessage,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                                color = OnSurface,
                                maxLines = 1,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Icon(
                            imageVector = if (uiState.isFlashWireExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand Flash Details",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Expanded Details
                    AnimatedVisibility(visible = uiState.isFlashWireExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerLowest)
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "ECONPULSE SYNTHESIS ENGINE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.5.sp,
                                        letterSpacing = 0.06.sp
                                    ),
                                    color = TelemetryCyan
                                )
                                Text(
                                    text = "14:31:50 UTC",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp
                                    ),
                                    color = OnSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            MarketRepository.flashWire.bulletPoints.forEach { item ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "• ",
                                        style = TextStyle(fontFamily = FontFamily.Monospace, color = BullishEmerald)
                                    )
                                    Text(
                                        text = "${item.first}: ${item.second}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 11.5.sp,
                                            lineHeight = 15.sp
                                        ),
                                        color = OnSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Terminal Session Telemetry & Markets Status Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        ) {
                            MarketStatusChip("NSE: OPEN", BullishEmerald)
                            MarketStatusChip("BSE: OPEN", BullishEmerald)
                            MarketStatusChip("NYSE: PRE", AmberAdvisory)
                            MarketStatusChip("CME: ACT", TelemetryCyan)
                            MarketStatusChip("LSE: OPEN", BullishEmerald)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(TertiaryContainer, RoundedCornerShape(3.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "DEMO",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = OnTertiary
                                )
                            }
                            Text(
                                text = "12ms LATENCY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.5.sp
                                ),
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "SYNC: 14:32:05 UTC • FEED: SIM-ENGINE-X4",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.5.sp
                            ),
                            color = OnSurfaceVariant
                        )
                        Text(
                            text = "Institutional Matrix",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.5.sp
                            ),
                            color = Outline
                        )
                    }
                }
            }
        }

        // 3. Global Indices Matrix (2-column compact grid)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "GLOBAL INDICES",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                letterSpacing = (-0.01).sp
                            ),
                            color = OnSurface
                        )
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHigh, RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "REALTIME",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TelemetryCyan
                            )
                        }
                    }
                    Text(
                        text = "6 ACTIVE CORRIDORS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.5.sp
                        ),
                        color = OnSurfaceVariant
                    )
                }

                // 2-column grid pairs
                val indices = MarketRepository.globalIndices
                for (i in indices.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IndexCompactCard(
                            index = indices[i],
                            onClick = { onIndexClick(indices[i].symbol) },
                            modifier = Modifier.weight(1f)
                        )
                        if (i + 1 < indices.size) {
                            IndexCompactCard(
                                index = indices[i + 1],
                                onClick = { onIndexClick(indices[i + 1].symbol) },
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // 4. Market Breadth & Volatility Dial Card
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MARKET BREADTH & VOLATILITY",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                letterSpacing = (-0.01).sp
                            ),
                            color = OnSurface
                        )
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHigh, RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "NSE CASH",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp
                                ),
                                color = TelemetryCyan
                            )
                        }
                    }

                    // Advancing vs Declining
                    val breadth = MarketRepository.breadthMetrics
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.ArrowUpward,
                                    contentDescription = null,
                                    tint = BullishEmerald,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = " 1,420 ADVANCING",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    ),
                                    color = BullishEmerald
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "780 DECLINING ",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    ),
                                    color = BearishCrimson
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDownward,
                                    contentDescription = null,
                                    tint = BearishCrimson,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        // Progress split bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SurfaceContainerHighest)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(0.645f)
                                    .background(BullishEmerald)
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(2.dp)
                                    .background(SurfaceContainerHighest)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(0.335f)
                                    .background(BearishCrimson)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ADR: 1.82 (Bull Dominant)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp
                                ),
                                color = OnSurfaceVariant
                            )
                            Text(
                                text = "Unchanged: 82",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp
                                ),
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    // 3 Volatility & Macro metrics cluster
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MacroMetricBox(
                            title = "INDIA VIX",
                            value = breadth.indiaVix,
                            delta = breadth.indiaVixChange,
                            subLabel = "Calm Regime",
                            isDeltaGood = true,
                            modifier = Modifier.weight(1f)
                        )
                        MacroMetricBox(
                            title = "CBOE VIX",
                            value = breadth.cboeVix,
                            delta = breadth.cboeVixChange,
                            subLabel = "Sub-Neutral",
                            isDeltaGood = true,
                            modifier = Modifier.weight(1f)
                        )
                        MacroMetricBox(
                            title = "RISK SENTIMENT",
                            value = "${breadth.riskScore}/100",
                            delta = breadth.riskLabel,
                            subLabel = breadth.marketBias,
                            isDeltaGood = true,
                            isSentiment = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // 5. Commodities & Cross-Assets (horizontal chips row)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMMODITIES & CROSS-ASSETS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            letterSpacing = (-0.01).sp
                        ),
                        color = OnSurface
                    )
                    Text(
                        text = "CROSS-CORRELATED",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp
                        ),
                        color = OnSurfaceVariant
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MarketRepository.commoditiesList.forEach { item ->
                        CommodityChipCard(
                            item = item,
                            onClick = { onCommodityClick(item.symbol) }
                        )
                    }
                }
            }
        }

        // 6. Sector Dispersion Heatmap
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
                            text = "SECTOR DISPERSION",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                letterSpacing = (-0.01).sp
                            ),
                            color = OnSurface
                        )
                        Text(
                            text = "SORT: % CHANGE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp
                            ),
                            color = OnSurfaceVariant
                        )
                    }

                    MarketRepository.sectorsList.forEach { sector ->
                        SectorRowItem(sector = sector)
                    }
                }
            }
        }

        // 7. AI Macro Outlook Synthesis Teaser Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh)
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PrimaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = null,
                                    tint = OnPrimary,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                            Text(
                                text = "AI MACRO OUTLOOK",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp,
                                    letterSpacing = (-0.01).sp
                                ),
                                color = OnSurface
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainer, RoundedCornerShape(3.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "PRO-AGENT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TelemetryCyan
                            )
                        }
                    }

                    Text(
                        text = "\"Domestic liquidity remains insulated with DII inflows offsetting mild FII derivatives hedging. Global cross-asset posture signals equity resilience into US pre-open.\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.5.sp,
                            lineHeight = 17.sp
                        ),
                        color = OnSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Engine: EconPulse LLM-FinTech v4.8",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.5.sp
                            ),
                            color = OnSurfaceVariant
                        )
                        Button(
                            onClick = onDeepDiveClick,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TelemetryCyan,
                                contentColor = OnPrimary
                            ),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(
                                text = "DEEP DIVE",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }

        // 8. Quick Actions Strip
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                QuickActionButton(
                    label = "Heatmap",
                    icon = Icons.Default.GridView,
                    iconTint = TelemetryCyan,
                    onClick = { onQuickAction("Heatmap") },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    label = "Yields",
                    icon = Icons.Default.SsidChart,
                    iconTint = BullishEmerald,
                    onClick = { onQuickAction("Yield Curve") },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    label = "OI Ladder",
                    icon = Icons.Default.Layers,
                    iconTint = AmberAdvisory,
                    onClick = { onQuickAction("Option Chain") },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    label = "Radar",
                    icon = Icons.Default.Radar,
                    iconTint = Outline,
                    onClick = { onQuickAction("Macro Radar") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MarketStatusChip(label: String, color: Color) {
    Box(
        modifier = Modifier
            .background(SurfaceContainerHigh, RoundedCornerShape(3.dp))
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = color
            )
        }
    }
}

@Composable
private fun IndexCompactCard(
    index: GlobalIndex,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val deltaColor = if (index.isPositive) BullishEmerald else BearishCrimson
    val badgeBg = if (index.isPositive) SecondaryContainer else ErrorContainer
    val badgeText = if (index.isPositive) OnSecondaryContainer else OnErrorContainer

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = index.symbol,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    ),
                    color = OnSurface,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .background(badgeBg, RoundedCornerShape(3.dp))
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = index.percentChange,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.5.sp
                        ),
                        color = badgeText
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = index.price,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.5.sp
                    ),
                    color = OnSurface
                )
                Text(
                    text = index.change,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 9.5.sp
                    ),
                    color = deltaColor
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = index.rangeText,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.5.sp
                    ),
                    color = OnSurfaceVariant
                )
                SparklineView(
                    points = index.sparklinePoints,
                    isPositive = index.isPositive,
                    width = 48.dp,
                    height = 14.dp
                )
            }
        }
    }
}

@Composable
private fun MacroMetricBox(
    title: String,
    value: String,
    delta: String,
    subLabel: String,
    isDeltaGood: Boolean,
    isSentiment: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainer)
            .padding(vertical = 6.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 8.5.sp
                ),
                color = OnSurfaceVariant
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp
                    ),
                    color = if (isSentiment) TelemetryCyan else OnSurface
                )
            }
            Text(
                text = delta,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 9.5.sp
                ),
                color = if (isDeltaGood) BullishEmerald else BearishCrimson
            )
            Text(
                text = subLabel,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp),
                color = Outline
            )
        }
    }
}

@Composable
private fun CommodityChipCard(
    item: CommodityItem,
    onClick: () -> Unit
) {
    val deltaColor = if (item.isPositive) BullishEmerald else BearishCrimson
    val badgeBg = if (item.isPositive) SecondaryContainer else ErrorContainer
    val badgeText = if (item.isPositive) OnSecondaryContainer else OnErrorContainer

    Card(
        modifier = Modifier
            .width(136.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    ),
                    color = OnSurfaceVariant,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .background(badgeBg, RoundedCornerShape(3.dp))
                        .padding(horizontal = 3.5.dp, vertical = 0.5.dp)
                ) {
                    Text(
                        text = item.percentChange,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.5.sp
                        ),
                        color = badgeText
                    )
                }
            }

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = item.price,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp
                    ),
                    color = OnSurface
                )
                if (item.unit.isNotEmpty()) {
                    Text(
                        text = item.unit,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp
                        ),
                        color = OnSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.exchange,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.5.sp
                    ),
                    color = OnSurfaceVariant
                )
                Text(
                    text = item.netDelta,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 9.sp
                    ),
                    color = deltaColor
                )
            }
        }
    }
}

@Composable
private fun SectorRowItem(sector: SectorItem) {
    val deltaColor = if (sector.isPositive) BullishEmerald else BearishCrimson
    val barColor = if (sector.isPositive) BullishEmerald else BearishCrimson

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(3.dp)
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
                Text(
                    text = sector.name,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.5.sp
                    ),
                    color = OnSurface
                )
                Text(
                    text = sector.topStocks,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = OnSurfaceVariant
                )
            }
            Text(
                text = sector.percentChange,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                ),
                color = deltaColor
            )
        }

        // Bar visual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(SurfaceContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(sector.barFillPercent)
                    .then(
                        if (!sector.isPositive) Modifier.align(Alignment.CenterEnd)
                        else Modifier.align(Alignment.CenterStart)
                    )
                    .background(barColor)
            )
        }
    }
}

@Composable
private fun QuickActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(19.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = OnSurface
            )
        }
    }
}
