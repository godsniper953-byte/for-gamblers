package com.example.model

data class TickerTapeItem(
    val symbol: String,
    val price: String,
    val changeText: String,
    val isPositive: Boolean
)

data class GlobalIndex(
    val symbol: String,
    val name: String,
    val price: String,
    val change: String,
    val percentChange: String,
    val rangeText: String,
    val sparklinePoints: List<Float>,
    val isPositive: Boolean
)

data class FlashWireItem(
    val headline: String,
    val timestamp: String,
    val engineName: String,
    val bulletPoints: List<Pair<String, String>>
)

data class BreadthMetrics(
    val advancing: Int,
    val declining: Int,
    val unchanged: Int,
    val adrRatio: String,
    val indiaVix: String,
    val indiaVixChange: String,
    val cboeVix: String,
    val cboeVixChange: String,
    val riskScore: Int,
    val riskLabel: String,
    val marketBias: String
)

data class CommodityItem(
    val symbol: String,
    val name: String,
    val price: String,
    val unit: String,
    val percentChange: String,
    val exchange: String,
    val netDelta: String,
    val isPositive: Boolean
)

data class SectorItem(
    val name: String,
    val topStocks: String,
    val percentChange: String,
    val barFillPercent: Float,
    val isPositive: Boolean
)

data class OrderBookLevel(
    val qty: String,
    val price: String,
    val depthRatio: Float
)

data class CandleStickData(
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volumeRatio: Float
)

data class StockQuote(
    val symbol: String,
    val name: String,
    val exchange: String,
    val category: String, // "NSE", "US", "MACRO"
    val price: String,
    val change: String,
    val percentChange: String,
    val volume: String,
    val dayRange: String,
    val dayProgress: Float,
    val year52Range: String,
    val year52Progress: Float,
    val mktCap: String,
    val peRatio: String,
    val vwap: String,
    val consensus: String,
    val oscillatorsBullish: Int,
    val masBullish: Int,
    val sparklinePoints: List<Float>,
    val candles: List<CandleStickData>,
    val spreadText: String,
    val bidsPercentage: Int,
    val asksPercentage: Int,
    val bidsVolume: String,
    val asksVolume: String,
    val bids: List<OrderBookLevel>,
    val asks: List<OrderBookLevel>,
    val isPositive: Boolean
)

data class OilMacroDriver(
    val title: String,
    val description: String,
    val highlightedText: String,
    val tagColorType: String // "warning", "error", "primary"
)

data class IndiaTransmissionVector(
    val channelName: String,
    val headline: String,
    val impactStatement: String,
    val iconName: String,
    val type: String // "cad", "fx", "cpi"
)

data class CorporateMarginSector(
    val sectorName: String,
    val detail: String,
    val verdict: String,
    val verdictSub: String,
    val isPositive: Boolean? // true = positive, false = negative, null = neutral
)

data class MaritimeChokepoint(
    val name: String,
    val metric: String,
    val statusText: String,
    val icon: String,
    val isWarning: Boolean
)

data class StorageTelemetry(
    val title: String,
    val tag: String,
    val forecastConsensus: String,
    val actualDelta: String,
    val detailSubtitle: String,
    val isBullish: Boolean
)

data class AiProbabilisticScenario(
    val title: String,
    val probabilityPercent: Int,
    val peakTarget: String,
    val durationHorizon: String,
    val analysisText: String,
    val niftyImpact: String,
    val usdinrTarget: String,
    val sectorDelta: String,
    val scenarioType: String // "base", "bull", "bear"
)

data class SectorBetaSensitivity(
    val sectorName: String,
    val betaDeltaText: String,
    val barPercent: Float,
    val isPositive: Boolean
)

data class AiMacroQueryPreset(
    val id: String,
    val buttonChipTitle: String,
    val queryText: String,
    val brentPrice: String,
    val usdinrPrice: String,
    val niftyPrice: String,
    val cpiPrice: String,
    val rollingBetaText: String,
    val importDependencyText: String,
    val importBillStat: String,
    val tradeDeficitImpact: String,
    val fxDepreciationRange: String,
    val cpiPassThrough: String,
    val scenarios: List<AiProbabilisticScenario>,
    val sectorSensitivities: List<SectorBetaSensitivity>,
    val confirmingSignals: List<String>,
    val invalidatingSignals: List<String>,
    val citations: List<String>,
    val confidencePercent: String
)
