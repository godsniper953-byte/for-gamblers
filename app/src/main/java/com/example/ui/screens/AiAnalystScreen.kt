package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AiMacroQueryPreset
import com.example.model.AiProbabilisticScenario
import com.example.model.SectorBetaSensitivity
import com.example.ui.components.SparklineView
import com.example.ui.theme.*
import com.example.viewmodel.TerminalUiState

@Composable
fun AiAnalystScreen(
    uiState: TerminalUiState,
    onSelectPreset: (String) -> Unit,
    onCustomPromptChange: (String) -> Unit,
    onSubmitPrompt: (String) -> Unit,
    onFeedback: (Boolean) -> Unit,
    onCopyBrief: () -> Unit,
    onExportCsv: () -> Unit,
    onSendToDesk: () -> Unit,
    modifier: Modifier = Modifier
) {
    val preset = uiState.selectedAiPreset

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBase)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
        ) {
            // 1. Top Command Telemetry Header Card
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
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(BullishEmerald)
                                )
                                Text(
                                    text = "EconPulse Macro Analyst",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    ),
                                    color = OnSurface
                                )
                                Box(
                                    modifier = Modifier
                                        .background(SurfaceContainerHigh, RoundedCornerShape(3.dp))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "v4.2",
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                        color = TelemetryCyan
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .background(SurfaceContainerHighest, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(Icons.Default.Bolt, contentDescription = null, tint = BullishEmerald, modifier = Modifier.size(13.dp))
                                Text(
                                    text = "LATENCY: 42ms",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.Bold),
                                    color = BullishEmerald
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ENGINE: ACTIVE • GRAPH: SYNCED (5m ago)",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                                color = Outline
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(Icons.Default.Verified, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(13.dp))
                                Text(
                                    text = "Epistemic Rigor: High",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.SemiBold),
                                    color = TelemetryCyan
                                )
                            }
                            Text(
                                text = "Grounded Econometric Baselines",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // 2. Micro Signal Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainer)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("COMPUTE:", color = Outline, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                        Text("FP8 QUANTIZED", color = OnSurface, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("CORRELATION MATRIX:", color = Outline, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                        Text("UPDATED", color = BullishEmerald, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("MONTE CARLO:", color = Outline, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                        Text("10,000 RUNS", color = TelemetryCyan, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // 3. Preset Analytical Triggers (Horizontal Chips)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val presets = com.example.data.MarketRepository.aiQueryPresets
                    presets.forEach { p ->
                        val isSelected = p.id == preset.id
                        val bg = if (isSelected) TelemetryCyan.copy(alpha = 0.2f) else SurfaceContainer
                        val textColor = if (isSelected) TelemetryCyan else OnSurfaceVariant

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(bg)
                                .clickable { onSelectPreset(p.id) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (p.id == "crude_spike") Icons.Default.LocalGasStation else if (p.id == "fed_easing") Icons.Default.AccountBalance else Icons.Default.SwapHoriz,
                                    contentDescription = null,
                                    tint = textColor,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = p.buttonChipTitle,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.5.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = textColor
                                )
                                if (isSelected) {
                                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(TelemetryCyan))
                                }
                            }
                        }
                    }
                }
            }

            // 4. Active Institutional Query Console Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
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
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Terminal, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "ACTIVE SYNTHESIS QUERY",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.5.sp,
                                        letterSpacing = 0.05.sp
                                    ),
                                    color = TelemetryCyan
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(SurfaceContainerHighest, RoundedCornerShape(3.dp))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "DEMO MODEL",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 8.5.sp),
                                    color = AmberAdvisory
                                )
                            }
                        }

                        // Query Quote
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLowest)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = preset.queryText,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.5.sp,
                                    lineHeight = 18.sp
                                ),
                                color = OnSurface
                            )
                        }

                        // 4-grid Input Matrix Vectors
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TelemetryInputPill("Brent Crude", preset.brentPrice, BullishEmerald, Modifier.weight(1f))
                            TelemetryInputPill("USD/INR", preset.usdinrPrice, BearishCrimson, Modifier.weight(1f))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TelemetryInputPill("NIFTY 50", preset.niftyPrice, BullishEmerald, Modifier.weight(1f))
                            TelemetryInputPill("India CPI (MoM)", preset.cpiPrice, AmberAdvisory, Modifier.weight(1f))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "GENERATED: 14:35:12 UTC",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                                color = Outline
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(11.dp))
                                Text(
                                    text = "Validated Model Output",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = OnSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // 5. SECTION A: Factual Historical Evidence
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(SurfaceBright, RoundedCornerShape(3.dp))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "SECTION A",
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 8.5.sp),
                                        color = OnSurface
                                    )
                                }
                                Text(
                                    text = "Factual Historical Evidence",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                    color = OnSurface
                                )
                            }
                            Icon(Icons.Default.HistoryEdu, contentDescription = null, tint = Outline, modifier = Modifier.size(16.dp))
                        }

                        // Evidence block 1: Beta
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(8.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.StackedLineChart, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(14.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("ROLLING BETA REGRESSIONS", color = TelemetryCyan, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                    Text("10-YR HORIZON", color = Outline, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = preset.rollingBetaText,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                                    color = OnSurface
                                )
                            }
                        }

                        // Evidence block 2: Import Dependency
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(8.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LocalGasStation, contentDescription = null, tint = AmberAdvisory, modifier = Modifier.size(14.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("STRUCTURAL IMPORT DEPENDENCY", color = AmberAdvisory, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                    Text("FY24 CAD METRICS", color = Outline, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${preset.importDependencyText} The annualized total petroleum import bill recorded ${preset.importBillStat} across the previous fiscal year.",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                                    color = OnSurface
                                )
                            }
                        }

                        // Sparkline intensity
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLowest)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CRUDE IMPORT INTENSITY INDEX",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 8.5.sp),
                                    color = Outline
                                )
                                Text(
                                    text = "87.3% (YoY +1.2%)",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.5.sp),
                                    color = BullishEmerald
                                )
                            }
                            SparklineView(
                                points = listOf(18f, 17f, 19f, 13f, 14f, 8f, 10f, 4f),
                                isPositive = true,
                                width = 70.dp,
                                height = 18.dp
                            )
                        }
                    }
                }
            }

            // 6. SECTION B: Transmission Vectors
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(PrimaryContainer, RoundedCornerShape(3.dp))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "SECTION B",
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 8.5.sp),
                                        color = OnPrimary
                                    )
                                }
                                Text(
                                    text = "Transmission Vectors",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                    color = OnSurface
                                )
                            }
                            Icon(Icons.Default.Hub, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(16.dp))
                        }

                        // Vector 1: Trade Deficit
                        TransmissionVectorDetail(
                            icon = Icons.Default.AccountBalanceWallet,
                            title = "Trade Deficit Elasticity",
                            metric = preset.tradeDeficitImpact,
                            metricColor = BearishCrimson,
                            description = "Every sustained $10/bbl escalation broadens India's current account deficit (CAD) by roughly 40-50 bps of GDP, adding immediate pressure on foreign currency demand.",
                            fillRatio = 0.78f,
                            barColor = BearishCrimson
                        )

                        // Vector 2: FX Transmission
                        TransmissionVectorDetail(
                            icon = Icons.Default.CurrencyExchange,
                            title = "Rupee (FX) Depreciation Vector",
                            metric = preset.fxDepreciationRange,
                            metricColor = AmberAdvisory,
                            description = "Exerts sharp near-term downward drift on USD/INR. Spot market defense from RBI anticipated to burn ~$2B-$3.5B in weekly reserves to smooth volatility.",
                            fillRatio = 0.52f,
                            barColor = AmberAdvisory
                        )

                        // Vector 3: Inflation
                        TransmissionVectorDetail(
                            icon = Icons.Default.TrendingUp,
                            title = "Headline CPI Pass-Through",
                            metric = preset.cpiPassThrough,
                            metricColor = TelemetryCyan,
                            description = "Direct pass-through materializes within 60 to 90 days if OMCs pass pump prices through; secondary diesel logistics inflation adds an additional ~15 bps over 2 quarters.",
                            fillRatio = 0.65f,
                            barColor = TelemetryCyan
                        )
                    }
                }
            }

            // 7. SECTION C: Probabilistic Scenarios
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(SurfaceContainerHighest, RoundedCornerShape(3.dp))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "SECTION C",
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 8.5.sp),
                                        color = BullishEmerald
                                    )
                                }
                                Text(
                                    text = "Probabilistic Scenarios",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                    color = OnSurface
                                )
                            }
                            Icon(Icons.Default.QueryStats, contentDescription = null, tint = BullishEmerald, modifier = Modifier.size(16.dp))
                        }

                        preset.scenarios.forEach { sc ->
                            ScenarioCard(scenario = sc)
                        }
                    }
                }
            }

            // 8. Sector Sensitivity Vector (Equity Beta)
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
                                text = "Sector Sensitivity Vector (10% Oil Rise)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                color = OnSurface
                            )
                            Text(
                                text = "EQUITY BETA",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                                color = Outline
                            )
                        }

                        preset.sectorSensitivities.forEach { sens ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceContainer)
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = sens.sectorName,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 11.sp),
                                    color = OnSurface,
                                    modifier = Modifier.width(115.dp),
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                LinearProgressIndicator(
                                    progress = { sens.barPercent },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = if (sens.isPositive) BullishEmerald else BearishCrimson,
                                    trackColor = SurfaceContainerHighest
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = sens.betaDeltaText,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp
                                    ),
                                    color = if (sens.isPositive) BullishEmerald else BearishCrimson,
                                    modifier = Modifier.width(42.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 9. Thesis Boundary Conditions (Confirming vs Invalidating)
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Balance, contentDescription = null, tint = AmberAdvisory, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Thesis Boundary Conditions",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                                    color = OnSurface
                                )
                            }
                            Text(
                                text = "TELEMETRY RIGOR",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                                color = Outline
                            )
                        }

                        // Confirming
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BullishEmerald, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "Confirming Signals (Base/Bear Thesis)",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    color = BullishEmerald
                                )
                            }
                            preset.confirmingSignals.forEach { sig ->
                                Row(modifier = Modifier.padding(start = 4.dp, top = 1.dp)) {
                                    Text("• ", color = BullishEmerald, fontSize = 11.sp)
                                    Text(sig, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = OnSurfaceVariant)
                                }
                            }
                        }

                        // Invalidating
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Cancel, contentDescription = null, tint = BearishCrimson, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "Invalidating Signals (Reversion to Bull)",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    color = BearishCrimson
                                )
                            }
                            preset.invalidatingSignals.forEach { sig ->
                                Row(modifier = Modifier.padding(start = 4.dp, top = 1.dp)) {
                                    Text("• ", color = BearishCrimson, fontSize = 11.sp)
                                    Text(sig, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = OnSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }

            // 10. Institutional Citation Corpus
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
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
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(15.dp))
                                Text(
                                    text = "INSTITUTIONAL CITATION CORPUS",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                    color = OnSurfaceVariant
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("CONFIDENCE:", color = Outline, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text(preset.confidencePercent, color = BullishEmerald, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            preset.citations.forEach { cit ->
                                Box(
                                    modifier = Modifier
                                        .background(SurfaceContainerHigh, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Text(cit, color = OnSurface, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("MODEL: ECON-TRANS-LLM-4.2B", color = Outline, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
                            Text("WEIGHTS: CALIBRATED 08:00 UTC", color = Outline, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }

            // 11. Quick Feedback & Export Controls
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        ActionButton(
                            icon = Icons.Default.ContentCopy,
                            label = "Copy Brief",
                            onClick = onCopyBrief
                        )
                        ActionButton(
                            icon = Icons.Default.Download,
                            label = "Export CSV",
                            onClick = onExportCsv
                        )
                        ActionButton(
                            icon = Icons.Default.Share,
                            label = "Send to Desk",
                            onClick = onSendToDesk
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = { onFeedback(true) },
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (uiState.activeFeedbackPositive == true) BullishEmerald.copy(alpha = 0.2f) else SurfaceContainer,
                                    RoundedCornerShape(4.dp)
                                )
                        ) {
                            Icon(Icons.Default.ThumbUp, contentDescription = "Thumbs Up", tint = if (uiState.activeFeedbackPositive == true) BullishEmerald else OnSurfaceVariant, modifier = Modifier.size(15.dp))
                        }
                        IconButton(
                            onClick = { onFeedback(false) },
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (uiState.activeFeedbackPositive == false) BearishCrimson.copy(alpha = 0.2f) else SurfaceContainer,
                                    RoundedCornerShape(4.dp)
                                )
                        ) {
                            Icon(Icons.Default.ThumbDown, contentDescription = "Thumbs Down", tint = if (uiState.activeFeedbackPositive == false) BearishCrimson else OnSurfaceVariant, modifier = Modifier.size(15.dp))
                        }
                    }
                }
            }
        }

        // Sticky Bottom Follow-up Query Console Anchor
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurfaceContainerLow.copy(alpha = 0.98f),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Input Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainerLowest)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onSendToDesk() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = OnSurfaceVariant, modifier = Modifier.size(17.dp))
                    }

                    Text(
                        text = ">",
                        style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                        color = TelemetryCyan
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    BasicTextField(
                        value = uiState.aiCustomPrompt,
                        onValueChange = onCustomPromptChange,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("ai_prompt_input"),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            color = OnSurface,
                            fontSize = 12.sp
                        ),
                        cursorBrush = SolidColor(TelemetryCyan),
                        decorationBox = { innerTextField ->
                            if (uiState.aiCustomPrompt.isEmpty()) {
                                Text(
                                    text = "Ask a macro follow-up (e.g. 'OMC margins at $95 Brent?')...",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Outline,
                                        fontSize = 11.5.sp
                                    ),
                                    maxLines = 1
                                )
                            }
                            innerTextField()
                        }
                    )

                    IconButton(
                        onClick = {
                            onSubmitPrompt("Simulate voice macro query: Stress test PSU vs Private OMCs")
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Query", tint = OnSurfaceVariant, modifier = Modifier.size(17.dp))
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(TelemetryCyan)
                            .clickable {
                                if (uiState.aiCustomPrompt.isNotBlank()) {
                                    onSubmitPrompt(uiState.aiCustomPrompt)
                                }
                            }
                            .testTag("ai_submit_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.isSynthesizing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = OnPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "Send",
                                tint = OnPrimary,
                                modifier = Modifier.size(17.dp)
                            )
                        }
                    }
                }

                // Suggested Follow-up Probes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("PROBE:", color = Outline, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
                    listOf(
                        "Stress test PSU vs Private OMCs",
                        "Show USD/INR hedging band",
                        "Simulate Russian crude discount delta"
                    ).forEach { probe ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceContainer)
                                .clickable { onSubmitPrompt(probe) }
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "\"$probe\"",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TelemetryInputPill(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SurfaceContainerLow)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = OnSurfaceVariant, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
        Text(value, color = color, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun TransmissionVectorDetail(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    metric: String,
    metricColor: Color,
    description: String,
    fillRatio: Float,
    barColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainer)
            .padding(8.dp),
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
                Icon(imageVector = icon, contentDescription = null, tint = metricColor, modifier = Modifier.size(14.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp),
                    color = OnSurface
                )
            }
            Text(
                text = metric,
                style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.sp),
                color = metricColor
            )
        }

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
            color = OnSurfaceVariant
        )

        Spacer(modifier = Modifier.height(2.dp))
        LinearProgressIndicator(
            progress = { fillRatio },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = barColor,
            trackColor = SurfaceContainerHighest
        )
    }
}

@Composable
private fun ScenarioCard(scenario: AiProbabilisticScenario) {
    val (dotColor, badgeBg, badgeColor) = when (scenario.scenarioType) {
        "bull" -> Triple(BullishEmerald, BullishEmerald.copy(alpha = 0.2f), BullishEmerald)
        "bear" -> Triple(BearishCrimson, BearishCrimson.copy(alpha = 0.2f), BearishCrimson)
        else -> Triple(TelemetryCyan, TelemetryCyan.copy(alpha = 0.2f), TelemetryCyan)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainer)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(dotColor))
                Text(
                    text = scenario.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                    color = OnSurface
                )
            }
            Box(
                modifier = Modifier
                    .background(badgeBg, RoundedCornerShape(3.dp))
                    .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
                Text(
                    text = "${scenario.probabilityPercent}% PROB",
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 9.sp),
                    color = badgeColor
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Peak Target: ${scenario.peakTarget}", color = OnSurfaceVariant, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
            Text("•", color = Outline, fontSize = 9.sp)
            Text("Duration: ${scenario.durationHorizon}", color = OnSurfaceVariant, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
        }

        Text(
            text = scenario.analysisText,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp, lineHeight = 15.sp),
            color = OnSurface
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SurfaceContainerLow)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NIFTY: ${scenario.niftyImpact}",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = if (scenario.niftyImpact.startsWith("-")) BearishCrimson else BullishEmerald
            )
            Text(
                text = "USD/INR: ${scenario.usdinrTarget}",
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold),
                color = OnSurface
            )
            Text(
                text = scenario.sectorDelta,
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold),
                color = if (scenario.scenarioType == "bull") BullishEmerald else if (scenario.scenarioType == "bear") BearishCrimson else AmberAdvisory
            )
        }
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SurfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 7.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(13.dp))
        Text(
            text = label,
            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.Medium),
            color = OnSurfaceVariant
        )
    }
}
