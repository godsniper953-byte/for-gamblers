package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketRepository
import com.example.model.CorporateMarginSector
import com.example.model.IndiaTransmissionVector
import com.example.model.MaritimeChokepoint
import com.example.ui.theme.*
import com.example.viewmodel.TerminalUiState

@Composable
fun OilIntelligenceScreen(
    uiState: TerminalUiState,
    onCurveViewChange: (String) -> Unit,
    onSectorClick: (CorporateMarginSector) -> Unit,
    onShareTelemetry: () -> Unit,
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
        // 1. Commodity Ticker Ribbon
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainerLowest)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val ribbon = listOf(
                    Triple("BRENT", "$85.42", "+1.34%"),
                    Triple("WTI", "$81.16", "+1.28%"),
                    Triple("NG", "$2.84", "-2.10%"),
                    Triple("GOLD", "$2,348.50", "+0.42%"),
                    Triple("SILVER", "$30.12", "+1.15%"),
                    Triple("COPPER", "$4.48", "+0.80%")
                )
                ribbon.forEach { (name, price, delta) ->
                    val isPos = delta.startsWith("+")
                    Row(
                        modifier = Modifier
                            .background(SurfaceContainer, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(name, color = OnSurfaceVariant, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                        Text(price, color = OnSurface, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPos) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = if (isPos) BullishEmerald else BearishCrimson,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                delta,
                                color = if (isPos) BullishEmerald else BearishCrimson,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 2. HERO ENERGY INTELLIGENCE CARD
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
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(TelemetryCyan)
                            )
                            Text(
                                text = "INTELLIGENCE TERMINAL",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                ),
                                color = TelemetryCyan
                            )
                            Text("|", color = OutlineVariant, fontSize = 9.sp)
                            Text(
                                text = "BRENT SPOT (ICE)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp
                                ),
                                color = OnSurfaceVariant
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(SurfaceContainer, RoundedCornerShape(3.dp))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "LIVE FEED",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 8.5.sp
                                ),
                                color = TelemetryCyan
                            )
                        }
                    }

                    // Price & Range
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "$85.42",
                                    style = TickerLgStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                                    color = OnSurface
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropUp,
                                        contentDescription = null,
                                        tint = BullishEmerald,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "+1.34%",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        ),
                                        color = BullishEmerald
                                    )
                                }
                            }
                            Text(
                                text = "USD / Barrel • Global Benchmark",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp),
                                color = OnSurfaceVariant
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "1M RANGE",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                                color = OnSurfaceVariant
                            )
                            Text(
                                text = "$80.20 - $87.90",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp),
                                color = OnSurface
                            )
                        }
                    }

                    // 1Y Forward Deficit Curve Graph
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .padding(8.dp),
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
                                Icon(Icons.Default.ShowChart, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "1Y Forward Deficit Curve",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.5.sp
                                    ),
                                    color = OnSurface
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SurfaceContainerHigh)
                                    .padding(2.dp),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(if (uiState.oilCurveView == "curve") TelemetryCyan else Color.Transparent)
                                        .clickable { onCurveViewChange("curve") }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Curve",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        ),
                                        color = if (uiState.oilCurveView == "curve") OnPrimary else OnSurfaceVariant
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(if (uiState.oilCurveView == "deficit") TelemetryCyan else Color.Transparent)
                                        .clickable { onCurveViewChange("deficit") }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Deficit",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        ),
                                        color = if (uiState.oilCurveView == "deficit") OnPrimary else OnSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Curve Canvas
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(95.dp)
                        ) {
                            ForwardDeficitCanvas(
                                viewMode = uiState.oilCurveView,
                                modifier = Modifier.fillMaxSize()
                            )

                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(SurfaceContainerHigh.copy(alpha = 0.85f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 3.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "+1.2M bpd Deficit",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 9.5.sp),
                                    color = BullishEmerald
                                )
                                Text(
                                    text = "Q3-Q4 Horizon",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 8.5.sp),
                                    color = OnSurfaceVariant
                                )
                            }
                        }
                    }

                    // Key Alpha Drivers
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "KEY ALPHA DRIVERS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp,
                                letterSpacing = 0.05.sp
                            ),
                            color = OnSurfaceVariant
                        )

                        AlphaDriverRow(
                            icon = Icons.Default.VerifiedUser,
                            iconTint = AmberAdvisory,
                            text = "OPEC+ voluntary supply reduction prolonged by 1.65 mb/d through Q4."
                        )
                        AlphaDriverRow(
                            icon = Icons.Default.Navigation,
                            iconTint = BearishCrimson,
                            text = "Red Sea transit surcharge adding +$1.80/bbl on global shipping reroutes."
                        )
                        AlphaDriverRow(
                            icon = Icons.Default.Storefront,
                            iconTint = TelemetryCyan,
                            text = "US Department of Energy replenishment purchases active up to $79/bbl baseline."
                        )
                    }
                }
            }
        }

        // 3. INDIA MACRO TRANSMISSION MATRIX
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
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(18.dp))
                            Column {
                                Text(
                                    text = "INDIA TRANSMISSION MATRIX",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    ),
                                    color = OnSurface
                                )
                                Text(
                                    text = "Sensitivity model: Crude spike → Macro vector pass-through",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                    color = OnSurfaceVariant
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .background(PrimaryContainer, RoundedCornerShape(3.dp))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ECONOMETRIC",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 8.5.sp
                                ),
                                color = OnPrimary
                            )
                        }
                    }

                    // 3-Up Macro Shock Channel Cards
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        MarketRepository.indiaTransmissionVectors.forEach { vector ->
                            TransmissionChannelCard(vector = vector)
                        }
                    }

                    // Sector Corporate Margin Sensitivity
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "CORPORATE MARGIN SENSITIVITY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                ),
                                color = OnSurfaceVariant
                            )
                            Text(
                                text = "SECTOR DISSECTION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp
                                ),
                                color = Outline
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerLowest),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            MarketRepository.corporateMarginSensitivities.forEach { sector ->
                                CorporateSectorRow(sector = sector, onClick = { onSectorClick(sector) })
                            }
                        }
                    }
                }
            }
        }

        // 4. MARITIME CHOKEPOINT RADAR
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
                            Icon(Icons.Default.Public, contentDescription = null, tint = AmberAdvisory, modifier = Modifier.size(18.dp))
                            Text(
                                text = "MARITIME CHOKEPOINT RADAR",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                ),
                                color = OnSurface
                            )
                        }
                        Text(
                            text = "AUTOMATED AIS",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                            color = OnSurfaceVariant
                        )
                    }

                    // 2x2 Chokepoint Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ChokepointGridCard(
                            item = MarketRepository.maritimeChokepoints[0],
                            modifier = Modifier.weight(1f)
                        )
                        ChokepointGridCard(
                            item = MarketRepository.maritimeChokepoints[1],
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ChokepointGridCard(
                            item = MarketRepository.maritimeChokepoints[2],
                            modifier = Modifier.weight(1f)
                        )
                        ChokepointGridCard(
                            item = MarketRepository.maritimeChokepoints[3],
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // 5. STORAGE & INVENTORY TELEMETRY
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
                            Icon(Icons.Default.Inventory2, contentDescription = null, tint = TelemetryCyan, modifier = Modifier.size(18.dp))
                            Text(
                                text = "STORAGE & INVENTORY TELEMETRY",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                ),
                                color = OnSurface
                            )
                        }
                        Text(
                            text = "WEEKLY EIA SYNC",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                            color = OnSurfaceVariant
                        )
                    }

                    MarketRepository.storageInventories.forEach { inv ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = inv.title,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp),
                                        color = OnSurface
                                    )
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (inv.isBullish) BullishEmerald.copy(alpha = 0.2f) else AmberAdvisory.copy(alpha = 0.2f),
                                                RoundedCornerShape(3.dp)
                                            )
                                            .padding(horizontal = 4.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = inv.tag,
                                            style = TextStyle(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 8.5.sp
                                            ),
                                            color = if (inv.isBullish) BullishEmerald else AmberAdvisory
                                        )
                                    }
                                }
                                Text(
                                    text = inv.forecastConsensus,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                                    color = OnSurfaceVariant
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = inv.actualDelta,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    ),
                                    color = if (inv.isBullish) BullishEmerald else OnSurface
                                )
                                Text(
                                    text = inv.detailSubtitle,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp
                                    ),
                                    color = if (inv.isBullish) OnSurfaceVariant else BearishCrimson
                                )
                            }
                        }
                    }
                }
            }
        }

        // 6. Disclaimer
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainerLowest)
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Outline,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Fact vs Forecast Disclaimer: All macroeconomic transmission ratios are statistical estimates derived from historical econometric models (2014-2024). Source: Ministry of Petroleum & Natural Gas (MoPNG), Reserve Bank of India (RBI), International Energy Agency (IEA). DEMO DATA.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    ),
                    color = OnSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ForwardDeficitCanvas(
    viewMode: String,
    modifier: Modifier = Modifier
) {
    val emeraldColor = BullishEmerald
    val cyanColor = TelemetryCyan

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Historical Baseline Reference line
        drawLine(
            color = Color(0xFF3D494C),
            start = Offset(0f, h * 0.65f),
            end = Offset(w, h * 0.65f),
            strokeWidth = 1f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
        )

        val points = listOf(
            Offset(0f, h * 0.75f),
            Offset(w * 0.10f, h * 0.68f),
            Offset(w * 0.25f, h * 0.72f),
            Offset(w * 0.40f, h * 0.55f),
            Offset(w * 0.55f, h * 0.60f),
            Offset(w * 0.70f, h * 0.38f),
            Offset(w * 0.85f, h * 0.46f),
            Offset(w, h * 0.15f)
        )

        // Area Fill Path
        val fillPath = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        val alpha = if (viewMode == "deficit") 0.25f else 0.08f
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(emeraldColor.copy(alpha = alpha), Color.Transparent),
                startY = 0f,
                endY = h
            )
        )

        // Main curve line
        val curvePath = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.forEach { lineTo(it.x, it.y) }
        }
        drawPath(
            path = curvePath,
            color = emeraldColor,
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )

        // Active node circle
        drawCircle(
            color = cyanColor,
            radius = 5f,
            center = points.last()
        )
    }
}

@Composable
private fun AlphaDriverRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainer)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(15.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp, lineHeight = 15.sp),
            color = OnSurface
        )
    }
}

@Composable
private fun TransmissionChannelCard(vector: IndiaTransmissionVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val (bgBox, iconTint, icon) = when (vector.type) {
            "cad" -> Triple(ErrorContainer.copy(alpha = 0.3f), BearishCrimson, Icons.Default.ReceiptLong)
            "fx" -> Triple(TertiaryContainer.copy(alpha = 0.3f), AmberAdvisory, Icons.Default.CurrencyExchange)
            else -> Triple(PrimaryContainer.copy(alpha = 0.25f), TelemetryCyan, Icons.Default.TrendingUp)
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(bgBox),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(17.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vector.channelName.uppercase(),
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, fontWeight = FontWeight.Bold),
                color = OnSurfaceVariant
            )
            Text(
                text = vector.headline,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp),
                color = OnSurface
            )
            Text(
                text = vector.impactStatement,
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.SemiBold),
                color = iconTint
            )
        }
    }
}

@Composable
private fun CorporateSectorRow(
    sector: CorporateMarginSector,
    onClick: () -> Unit
) {
    val (badgeBg, badgeColor) = when (sector.isPositive) {
        true -> BullishEmerald.copy(alpha = 0.2f) to BullishEmerald
        false -> BearishCrimson.copy(alpha = 0.2f) to BearishCrimson
        null -> TelemetryCyan.copy(alpha = 0.2f) to TelemetryCyan
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = sector.sectorName,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp),
                color = OnSurface
            )
            Text(
                text = sector.detail,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                color = OnSurfaceVariant
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .background(badgeBg, RoundedCornerShape(3.dp))
                    .padding(horizontal = 4.dp, vertical = 1.dp)
            ) {
                Text(
                    text = sector.verdict,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 8.5.sp),
                    color = badgeColor
                )
            }
            Text(
                text = sector.verdictSub,
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                color = OnSurfaceVariant
            )
        }
    }
}

@Composable
private fun ChokepointGridCard(
    item: MaritimeChokepoint,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.Bold),
                    color = OnSurfaceVariant,
                    maxLines = 1
                )
                if (item.isWarning) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(BearishCrimson))
                } else {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(BullishEmerald))
                }
            }

            Text(
                text = item.metric,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                ),
                color = if (item.isWarning) BearishCrimson else OnSurface
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = if (item.isWarning) Icons.Default.Warning else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (item.isWarning) BearishCrimson else BullishEmerald,
                    modifier = Modifier.size(11.dp)
                )
                Text(
                    text = item.statusText,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 8.5.sp),
                    color = if (item.isWarning) BearishCrimson else BullishEmerald,
                    maxLines = 1
                )
            }
        }
    }
}
