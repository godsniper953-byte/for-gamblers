package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketRepository
import com.example.model.CandleStickData
import com.example.model.OrderBookLevel
import com.example.model.StockQuote
import com.example.ui.components.SparklineView
import com.example.ui.theme.*
import com.example.viewmodel.TerminalUiState

@Composable
fun MarketsScreen(
    uiState: TerminalUiState,
    onSelectStock: (String) -> Unit,
    onTimeframeChange: (String) -> Unit,
    onChartTypeChange: (String) -> Unit,
    onToggleIndicator: (String) -> Unit,
    onWatchlistCategoryChange: (String) -> Unit,
    onScreenerFilterChange: (String) -> Unit,
    onOpenSearch: () -> Unit,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onShareQuote: (String) -> Unit,
    onToggleWatchlistStar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val stock = uiState.selectedStock
    val isBullish = stock.isPositive
    val deltaColor = if (isBullish) BullishEmerald else BearishCrimson

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBase)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
    ) {
        // Section 1: Search & Ticker Quick Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Search trigger input
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .clickable(onClick = onOpenSearch)
                            .padding(horizontal = 10.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = null,
                            tint = TelemetryCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = ">",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = TelemetryCyan,
                                fontSize = 12.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Search 50,000+ global tickers, ISIN, ADRs...",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Outline,
                                fontSize = 11.5.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "⌘K",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                color = OnSurfaceVariant
                            ),
                            modifier = Modifier
                                .background(SurfaceContainer, RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }

                    // Quick Switcher horizontal pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val quickTickers = listOf("RELIANCE", "NIFTY 50", "AAPL", "NVDA", "HDFCBANK", "TCS", "BRENT")
                        quickTickers.forEach { item ->
                            val isSelected = stock.symbol.equals(item, ignoreCase = true)
                            val bg = if (isSelected) SurfaceContainerHigh else SurfaceContainer
                            val textColor = if (isSelected) TelemetryCyan else OnSurfaceVariant

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(bg)
                                    .clickable { onSelectStock(item) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (isSelected) {
                                        Box(
                                            modifier = Modifier
                                                .size(5.dp)
                                                .clip(CircleShape)
                                                .background(BullishEmerald)
                                        )
                                    }
                                    Text(
                                        text = if (item == "RELIANCE" || item == "HDFCBANK" || item == "TCS") "$item.NS" else if (item == "AAPL" || item == "NVDA") "$item.US" else item,
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 9.5.sp
                                        ),
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Active Quote Header Card
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
                    // Header row: Symbol, Name, Exchange, Watchlist & Share
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = stock.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        letterSpacing = (-0.01).sp
                                    ),
                                    color = OnSurface,
                                    maxLines = 1
                                )
                                Box(
                                    modifier = Modifier
                                        .background(SurfaceContainerHighest, RoundedCornerShape(3.dp))
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "DEMO DATA",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 8.5.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = AmberAdvisory
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "${stock.symbol}.NS",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                                    color = OnSurfaceVariant
                                )
                                Text("•", color = Outline, fontSize = 9.5.sp)
                                Text(
                                    text = stock.exchange,
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                                    color = OnSurfaceVariant
                                )
                                Text("•", color = Outline, fontSize = 9.5.sp)
                                Text(
                                    text = "L1 REALTIME",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold),
                                    color = BullishEmerald
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(
                                onClick = { onToggleWatchlistStar(stock.symbol) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(SurfaceContainer, RoundedCornerShape(4.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Watchlist",
                                    tint = AmberAdvisory,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            IconButton(
                                onClick = { onShareQuote(stock.symbol) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(SurfaceContainer, RoundedCornerShape(4.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // Price & Volume row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stock.price,
                                style = TickerLgStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isBullish) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                    contentDescription = null,
                                    tint = deltaColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "${stock.change} (${stock.percentChange})",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.5.sp
                                    ),
                                    color = deltaColor
                                )
                            }
                        }

                        Text(
                            text = "Vol: ${stock.volume}",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp
                            ),
                            color = OnSurfaceVariant
                        )
                    }

                    // Analytical Micro Matrix (Day range, 52W range, MktCap, PE, VWAP)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest.copy(alpha = 0.6f))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Ranges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Day Range",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                        color = Outline
                                    )
                                    Text(
                                        text = stock.dayRange,
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                                        color = OnSurface
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                LinearProgressIndicator(
                                    progress = { stock.dayProgress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = TelemetryCyan,
                                    trackColor = SurfaceContainerHigh
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "52W Range",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                        color = Outline
                                    )
                                    Text(
                                        text = stock.year52Range,
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                                        color = OnSurface
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                LinearProgressIndicator(
                                    progress = { stock.year52Progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = BullishEmerald,
                                    trackColor = SurfaceContainerHigh
                                )
                            }
                        }

                        // MktCap, PE, VWAP stats
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("Mkt Cap:", color = Outline, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                                Text(stock.mktCap, color = OnSurface, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("P/E:", color = Outline, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                                Text(stock.peRatio, color = OnSurface, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("VWAP:", color = Outline, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace)
                                Text(stock.vwap, color = TelemetryCyan, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Interactive Chart Console
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
                    // Toolbar: Timeframes & Chart Type
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Timeframe Switcher
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLowest)
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            listOf("1D", "5D", "1M", "6M", "1Y", "5Y", "MAX").forEach { tf ->
                                val isSelected = uiState.selectedTimeframe == tf
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (isSelected) SurfaceContainerHigh else Color.Transparent)
                                        .clickable { onTimeframeChange(tf) }
                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = tf,
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 9.5.sp
                                        ),
                                        color = if (isSelected) TelemetryCyan else OnSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Chart Type Switcher
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLowest)
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            ChartTypeButton(
                                icon = Icons.Default.CandlestickChart,
                                isSelected = uiState.selectedChartType == "Candlestick",
                                onClick = { onChartTypeChange("Candlestick") }
                            )
                            ChartTypeButton(
                                icon = Icons.Default.ShowChart,
                                isSelected = uiState.selectedChartType == "Line",
                                onClick = { onChartTypeChange("Line") }
                            )
                            ChartTypeButton(
                                icon = Icons.Default.Equalizer,
                                isSelected = uiState.selectedChartType == "Heikin-Ashi",
                                onClick = { onChartTypeChange("Heikin-Ashi") }
                            )
                        }
                    }

                    // Active Indicators Ribbon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        IndicatorPill("EMA 20/50", TelemetryCyan, uiState.activeIndicators.contains("EMA 20/50")) {
                            onToggleIndicator("EMA 20/50")
                        }
                        IndicatorPill("RSI (14): 62.4", BullishEmerald, uiState.activeIndicators.contains("RSI (14)")) {
                            onToggleIndicator("RSI (14)")
                        }
                        IndicatorPill("MACD: Divergence", AmberAdvisory, uiState.activeIndicators.contains("MACD")) {
                            onToggleIndicator("MACD")
                        }
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerLowest, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "VWAP: ${stock.vwap}",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    // Candlestick Graphic Surface
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .padding(8.dp)
                    ) {
                        // Price Scale Overlays on the right
                        Text(
                            text = "2,958.00",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                            color = Outline,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .background(SurfaceContainerHigh.copy(alpha = 0.9f), RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = stock.price,
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.Bold),
                                color = TelemetryCyan
                            )
                        }
                        Text(
                            text = "2,905.00",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
                            color = Outline,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 24.dp)
                        )

                        // Main Canvas (Grid lines, EMA curves, Candlesticks, and Volume histogram)
                        CandlestickCanvas(
                            candles = stock.candles,
                            chartType = uiState.selectedChartType,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Consensus Pill
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(BullishEmerald.copy(alpha = 0.2f), RoundedCornerShape(3.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = stock.consensus,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.5.sp
                                    ),
                                    color = BullishEmerald
                                )
                            }
                            Text(
                                text = "${stock.oscillatorsBullish} Oscillators Bullish",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                                color = OnSurface
                            )
                        }
                        Text(
                            text = "${stock.masBullish} MAs Bullish",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
        }

        // Section 4: Market Depth & Level 2 Order Book
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
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapVert,
                                contentDescription = null,
                                tint = TelemetryCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Depth & Flow (L2)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                ),
                                color = OnSurface
                            )
                        }
                        Text(
                            text = stock.spreadText,
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                            color = OnSurfaceVariant
                        )
                    }

                    // Ratio bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Bids: ${stock.bidsPercentage}% (${stock.bidsVolume})",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold),
                            color = BullishEmerald
                        )
                        Text(
                            text = "Asks: ${stock.asksPercentage}% (${stock.asksVolume})",
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold),
                            color = BearishCrimson
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(SurfaceContainerHigh)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(stock.bidsPercentage / 100f)
                                .background(BullishEmerald)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(BearishCrimson)
                        )
                    }

                    // Dual Tabular Ladder
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Bids Table
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("QTY", color = Outline, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text("BID", color = Outline, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                            }
                            stock.bids.forEach { bid ->
                                OrderBookRow(
                                    qty = bid.qty,
                                    price = bid.price,
                                    depth = bid.depthRatio,
                                    isBid = true
                                )
                            }
                        }

                        // Asks Table
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("ASK", color = Outline, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text("QTY", color = Outline, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                            }
                            stock.asks.forEach { ask ->
                                OrderBookRow(
                                    qty = ask.qty,
                                    price = ask.price,
                                    depth = ask.depthRatio,
                                    isBid = false
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 5: Screener Quick Filters
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val filters = listOf(
                    "Top Gainers" to Icons.Default.TrendingUp,
                    "Top Losers" to Icons.Default.TrendingDown,
                    "Most Active" to Icons.Default.Bolt,
                    "52W Breakouts" to Icons.Default.WorkspacePremium
                )
                filters.forEach { (name, icon) ->
                    val isSelected = uiState.selectedScreenerFilter == name
                    val bg = if (isSelected) SurfaceContainerHigh else SurfaceContainer
                    val tint = if (isSelected) TelemetryCyan else OnSurfaceVariant

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(bg)
                            .clickable { onScreenerFilterChange(name) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(13.dp))
                            Text(
                                text = name,
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = tint
                            )
                        }
                    }
                }
            }
        }

        // Section 6: High-Density Watchlist Matrix
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
                    // Watchlist Tabs Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLowest)
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            WatchlistTab("Indian (NSE)", uiState.selectedWatchlistCategory == "in") {
                                onWatchlistCategoryChange("in")
                            }
                            WatchlistTab("US MegaCap", uiState.selectedWatchlistCategory == "us") {
                                onWatchlistCategoryChange("us")
                            }
                            WatchlistTab("Global Macro", uiState.selectedWatchlistCategory == "macro") {
                                onWatchlistCategoryChange("macro")
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(BullishEmerald)
                            )
                            Text(
                                text = "9 ASSETS",
                                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                color = BullishEmerald
                            )
                        }
                    }

                    // Watchlist Items
                    val displayedQuotes = when (uiState.selectedWatchlistCategory) {
                        "us" -> MarketRepository.stockQuotes.filter { it.category == "US" }
                        "macro" -> MarketRepository.stockQuotes.take(4) // Cross macro representations
                        else -> MarketRepository.stockQuotes.filter { it.category == "NSE" }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        displayedQuotes.forEach { q ->
                            WatchlistRowItem(
                                quote = q,
                                isSelected = q.symbol == stock.symbol,
                                onClick = { onSelectStock(q.symbol) }
                            )
                        }
                    }
                }
            }
        }

        // Section 7: Fast Action Desk Dock (Buy / Sell Buttons)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Buy Button
                Button(
                    onClick = onBuyClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("buy_bid_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryContainer,
                        contentColor = OnSecondary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "BUY (BID) ${stock.bids.firstOrNull()?.price ?: stock.price}",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                    }
                }

                // Sell Button
                Button(
                    onClick = onSellClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("sell_ask_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ErrorContainer,
                        contentColor = OnErrorContainer
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RemoveCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "SELL (ASK) ${stock.asks.firstOrNull()?.price ?: stock.price}",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChartTypeButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(26.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) SurfaceContainerHigh else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) TelemetryCyan else OnSurfaceVariant,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
private fun IndicatorPill(
    label: String,
    dotColor: Color,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(if (isActive) SurfaceContainerHigh else SurfaceContainerLowest)
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                ),
                color = if (isActive) dotColor else OnSurfaceVariant
            )
        }
    }
}

@Composable
private fun CandlestickCanvas(
    candles: List<CandleStickData>,
    chartType: String,
    modifier: Modifier = Modifier
) {
    val bullishColor = BullishEmerald
    val bearishColor = BearishCrimson
    val cyanColor = TelemetryCyan
    val amberColor = AmberAdvisory

    Canvas(modifier = modifier) {
        val width = size.width - 60f // Leave space for price overlays
        val height = size.height - 30f // Leave space for volume strip

        // Grid lines
        val gridY1 = height * 0.25f
        val gridY2 = height * 0.50f
        val gridY3 = height * 0.75f
        drawLine(Color(0xFF1C2028), Offset(0f, gridY1), Offset(width, gridY1), strokeWidth = 1f)
        drawLine(Color(0xFF1C2028), Offset(0f, gridY2), Offset(width, gridY2), strokeWidth = 1f)
        drawLine(Color(0xFF1C2028), Offset(0f, gridY3), Offset(width, gridY3), strokeWidth = 1f)

        // Center dashed reference line
        drawLine(
            color = cyanColor.copy(alpha = 0.35f),
            start = Offset(0f, gridY2),
            end = Offset(width, gridY2),
            strokeWidth = 1f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
        )

        if (candles.isEmpty()) return@Canvas

        val minPrice = candles.minOf { it.low }
        val maxPrice = candles.maxOf { it.high }
        val priceRange = if (maxPrice - minPrice == 0f) 1f else maxPrice - minPrice

        val candleStep = width / candles.size
        val candleWidth = (candleStep * 0.6f).coerceIn(4f, 10f)

        // Draw smooth EMA lines
        val emaPath20 = Path()
        val emaPath50 = Path()
        candles.forEachIndexed { idx, c ->
            val x = idx * candleStep + candleStep / 2f
            val y20 = height - ((c.close - minPrice) / priceRange) * height * 0.85f - 8f
            val y50 = height - ((c.open - minPrice) / priceRange) * height * 0.75f - 14f
            if (idx == 0) {
                emaPath20.moveTo(x, y20)
                emaPath50.moveTo(x, y50)
            } else {
                emaPath20.lineTo(x, y20)
                emaPath50.lineTo(x, y50)
            }
        }
        drawPath(emaPath20, cyanColor.copy(alpha = 0.8f), style = Stroke(width = 1.5f, cap = StrokeCap.Round))
        drawPath(emaPath50, amberColor.copy(alpha = 0.7f), style = Stroke(width = 1.5f, cap = StrokeCap.Round))

        // Draw Candlesticks or Line
        if (chartType == "Line") {
            val linePath = Path()
            candles.forEachIndexed { idx, c ->
                val x = idx * candleStep + candleStep / 2f
                val y = height - ((c.close - minPrice) / priceRange) * height
                if (idx == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
            }
            drawPath(linePath, bullishColor, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
        } else {
            candles.forEachIndexed { idx, c ->
                val x = idx * candleStep + candleStep / 2f
                val isGreen = c.close >= c.open
                val color = if (isGreen) bullishColor else bearishColor

                val highY = height - ((c.high - minPrice) / priceRange) * height
                val lowY = height - ((c.low - minPrice) / priceRange) * height
                val openY = height - ((c.open - minPrice) / priceRange) * height
                val closeY = height - ((c.close - minPrice) / priceRange) * height

                val topBody = minOf(openY, closeY)
                val bodyHeight = maxOf(3f, kotlin.math.abs(closeY - openY))

                // Wick
                drawLine(
                    color = color,
                    start = Offset(x, highY),
                    end = Offset(x, lowY),
                    strokeWidth = 1.2f
                )

                // Body
                drawRect(
                    color = color,
                    topLeft = Offset(x - candleWidth / 2f, topBody),
                    size = Size(candleWidth, bodyHeight)
                )

                // Volume bar at bottom
                val volHeight = (c.volumeRatio * 20f).coerceIn(4f, 22f)
                val volTop = size.height - volHeight
                drawRect(
                    color = color.copy(alpha = 0.65f),
                    topLeft = Offset(x - candleWidth / 2f, volTop),
                    size = Size(candleWidth, volHeight)
                )
            }
        }
    }
}

@Composable
private fun OrderBookRow(
    qty: String,
    price: String,
    depth: Float,
    isBid: Boolean
) {
    val barColor = if (isBid) BidDepthGreen else AskDepthRed
    val textColor = if (isBid) BullishEmerald else BearishCrimson

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(SurfaceContainerLowest)
    ) {
        // Volume Depth Background Fill
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(depth)
                .then(
                    if (isBid) Modifier.align(Alignment.CenterEnd)
                    else Modifier.align(Alignment.CenterStart)
                )
                .background(barColor)
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isBid) {
                Text(
                    text = qty,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                    color = OnSurfaceVariant
                )
                Text(
                    text = price,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 10.sp),
                    color = textColor
                )
            } else {
                Text(
                    text = price,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 10.sp),
                    color = textColor
                )
                Text(
                    text = qty,
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.5.sp),
                    color = OnSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun WatchlistTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) SurfaceContainerHigh else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 9.5.sp
            ),
            color = if (isSelected) TelemetryCyan else OnSurfaceVariant
        )
    }
}

@Composable
private fun WatchlistRowItem(
    quote: StockQuote,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val deltaColor = if (quote.isPositive) BullishEmerald else BearishCrimson
    val bg = if (isSelected) SurfaceContainerHigh else SurfaceContainerLowest

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = quote.symbol,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    ),
                    color = OnSurface
                )
                Box(
                    modifier = Modifier
                        .background(TelemetryCyan.copy(alpha = 0.15f), RoundedCornerShape(3.dp))
                        .padding(horizontal = 3.5.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = if (quote.category == "US") "NASDAQ" else "NSE",
                        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 8.5.sp),
                        color = TelemetryCyan
                    )
                }
            }
            Text(
                text = quote.name,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = OnSurfaceVariant,
                maxLines = 1
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SparklineView(
                points = quote.sparklinePoints,
                isPositive = quote.isPositive,
                width = 44.dp,
                height = 14.dp
            )
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = quote.price,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.5.sp
                    ),
                    color = OnSurface
                )
                Text(
                    text = quote.percentChange,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    ),
                    color = deltaColor
                )
            }
        }
    }
}
