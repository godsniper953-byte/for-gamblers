package com.example.data

import com.example.model.*

object MarketRepository {

    val tickerTapeItems = listOf(
        TickerTapeItem("NIFTY 50", "22,480.30", "+0.64%", true),
        TickerTapeItem("SENSEX", "73,920.15", "+0.58%", true),
        TickerTapeItem("NASDAQ", "18,245.80", "-0.22%", false),
        TickerTapeItem("S&P 500", "5,430.10", "+0.12%", true),
        TickerTapeItem("BRENT", "$85.42", "+1.34%", true),
        TickerTapeItem("USD/INR", "83.48", "-0.05%", false),
        TickerTapeItem("US 10Y", "4.28%", "+2bps", true)
    )

    val globalIndices = listOf(
        GlobalIndex("NIFTY 50", "NIFTY 50", "22,514.80", "+142.35", "+0.64%", "22.38k-22.54k", listOf(14f, 12f, 13f, 8f, 9f, 4f, 2f), true),
        GlobalIndex("SENSEX", "BSE SENSEX", "74,119.30", "+452.10", "+0.61%", "73.6k-74.2k", listOf(15f, 11f, 12f, 7f, 5f, 2f), true),
        GlobalIndex("BANK NIFTY", "BANK NIFTY", "48,210.50", "+310.20", "+0.65%", "47.8k-48.3k", listOf(13f, 14f, 9f, 10f, 4f, 3f), true),
        GlobalIndex("NASDAQ COMP", "NASDAQ", "18,288.40", "-41.20", "-0.22%", "18.25k-18.34k", listOf(3f, 4f, 10f, 8f, 14f, 15f), false),
        GlobalIndex("S&P 500", "S&P 500", "5,464.20", "+6.80", "+0.12%", "5.45k-5.47k", listOf(11f, 10f, 12f, 7f, 5f), true),
        GlobalIndex("DOW JONES", "DOW JONES", "39,127.14", "-35.10", "-0.09%", "39.09k-39.2k", listOf(5f, 6f, 10f, 9f, 13f), false)
    )

    val breadthMetrics = BreadthMetrics(
        advancing = 1420,
        declining = 780,
        unchanged = 82,
        adrRatio = "1.82 (Bull Dominant)",
        indiaVix = "13.42",
        indiaVixChange = "-4.10%",
        cboeVix = "12.85",
        cboeVixChange = "-1.80%",
        riskScore = 68,
        riskLabel = "Risk-On",
        marketBias = "Equities Bias"
    )

    val commoditiesList = listOf(
        CommodityItem("BRENT", "Brent Crude", "$85.42", "/bbl", "+1.34%", "ICE Front", "+$1.13", true),
        CommodityItem("WTI", "WTI Crude", "$81.16", "/bbl", "+1.28%", "NYMEX", "+$1.03", true),
        CommodityItem("GOLD", "Spot Gold", "$2,348.50", "/oz", "+0.42%", "COMEX", "+$9.80", true),
        CommodityItem("SILVER", "Silver", "$30.12", "/oz", "+1.15%", "COMEX", "+$0.34", true),
        CommodityItem("NATGAS", "Nat Gas", "$2.84", "/MMBtu", "-2.10%", "Henry Hub", "-$0.06", false),
        CommodityItem("USDINR", "USD/INR", "₹83.48", "", "-0.05%", "INR Stiff", "-4p", true),
        CommodityItem("DXY", "DXY Index", "105.42", "", "+0.18%", "USD Basket", "+0.19", true),
        CommodityItem("US10Y", "US 10Y Yield", "4.282%", "", "+2.4 bps", "UST Curve", "+0.024", true)
    )

    val sectorsList = listOf(
        SectorItem("NIFTY AUTO", "M&M, Tata Motors", "+1.85%", 0.82f, true),
        SectorItem("NIFTY IT", "TCS, Infy, HCL", "+1.20%", 0.62f, true),
        SectorItem("NIFTY ENERGY", "RIL, ONGC, NTPC", "+1.10%", 0.58f, true),
        SectorItem("NIFTY METAL", "Tata Steel, Hindalco", "+0.95%", 0.48f, true),
        SectorItem("NIFTY PHARMA", "Sun, Dr Reddy", "+0.30%", 0.25f, true),
        SectorItem("NIFTY FMCG", "ITC, HUL, Nestlé", "-0.45%", 0.32f, false)
    )

    val flashWire = FlashWireItem(
        headline = "US PPI rises 0.2% MoM vs 0.3% exp • Red Sea crude premium expands • RBI repo fixed 6.50%",
        timestamp = "14:31:50 UTC",
        engineName = "ECONPULSE SYNTHESIS ENGINE",
        bulletPoints = listOf(
            "US PPI" to "Cooling wholesale inflation reinforces September rate trim scenario. Yields dipping on front end.",
            "Crude Supply" to "Houthi corridor risk adds +$1.20 risk premium across Brent front-month contracts.",
            "RBI Stance" to "5-1 majority reinforces persistent vigilance against food price shocks."
        )
    )

    val stockQuotes = listOf(
        StockQuote(
            symbol = "RELIANCE",
            name = "Reliance Industries Ltd",
            exchange = "NSE EQUITIES",
            category = "NSE",
            price = "₹2,942.50",
            change = "+₹38.20",
            percentChange = "+1.31%",
            volume = "8.42M",
            dayRange = "₹2,905.00 - ₹2,958.00",
            dayProgress = 0.71f,
            year52Range = "₹2,220 - ₹3,024",
            year52Progress = 0.89f,
            mktCap = "₹19.91T ($238B)",
            peRatio = "28.4",
            vwap = "2,934.10",
            consensus = "STRONG BUY",
            oscillatorsBullish = 8,
            masBullish = 12,
            sparklinePoints = listOf(12f, 10f, 13f, 7f, 8f, 2f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: ₹0.40 (0.01%)",
            bidsPercentage = 52,
            asksPercentage = 48,
            bidsVolume = "412.5K",
            asksVolume = "380.2K",
            bids = listOf(
                OrderBookLevel("14,250", "2,942.10", 0.85f),
                OrderBookLevel("9,800", "2,941.80", 0.65f),
                OrderBookLevel("6,350", "2,941.00", 0.50f)
            ),
            asks = listOf(
                OrderBookLevel("11,900", "2,942.50", 0.75f),
                OrderBookLevel("8,420", "2,943.00", 0.58f),
                OrderBookLevel("16,100", "2,943.50", 0.90f)
            ),
            isPositive = true
        ),
        StockQuote(
            symbol = "HDFCBANK",
            name = "HDFC Bank Ltd",
            exchange = "NSE EQUITIES",
            category = "NSE",
            price = "₹1,524.80",
            change = "+₹12.70",
            percentChange = "+0.84%",
            volume = "14.20M",
            dayRange = "₹1,510.00 - ₹1,532.00",
            dayProgress = 0.68f,
            year52Range = "₹1,363 - ₹1,757",
            year52Progress = 0.45f,
            mktCap = "₹11.60T ($139B)",
            peRatio = "19.2",
            vwap = "1,521.40",
            consensus = "BUY",
            oscillatorsBullish = 7,
            masBullish = 10,
            sparklinePoints = listOf(11f, 9f, 10f, 5f, 3f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: ₹0.20 (0.01%)",
            bidsPercentage = 54,
            asksPercentage = 46,
            bidsVolume = "310.4K",
            asksVolume = "264.1K",
            bids = listOf(
                OrderBookLevel("18,500", "1,524.60", 0.80f),
                OrderBookLevel("12,100", "1,524.20", 0.60f),
                OrderBookLevel("7,400", "1,523.80", 0.45f)
            ),
            asks = listOf(
                OrderBookLevel("15,200", "1,524.80", 0.70f),
                OrderBookLevel("9,600", "1,525.20", 0.55f),
                OrderBookLevel("14,800", "1,525.60", 0.85f)
            ),
            isPositive = true
        ),
        StockQuote(
            symbol = "ICICIBANK",
            name = "ICICI Bank Ltd",
            exchange = "NSE EQUITIES",
            category = "NSE",
            price = "₹1,120.40",
            change = "+₹12.20",
            percentChange = "+1.10%",
            volume = "9.15M",
            dayRange = "₹1,108.00 - ₹1,126.00",
            dayProgress = 0.72f,
            year52Range = "₹910 - ₹1,135",
            year52Progress = 0.94f,
            mktCap = "₹7.88T ($94B)",
            peRatio = "17.8",
            vwap = "1,118.50",
            consensus = "STRONG BUY",
            oscillatorsBullish = 9,
            masBullish = 13,
            sparklinePoints = listOf(13f, 11f, 12f, 6f, 4f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: ₹0.15 (0.01%)",
            bidsPercentage = 56,
            asksPercentage = 44,
            bidsVolume = "280.9K",
            asksVolume = "220.1K",
            bids = listOf(
                OrderBookLevel("11,400", "1,120.25", 0.78f),
                OrderBookLevel("8,300", "1,119.80", 0.58f),
                OrderBookLevel("5,900", "1,119.20", 0.42f)
            ),
            asks = listOf(
                OrderBookLevel("9,800", "1,120.40", 0.65f),
                OrderBookLevel("7,200", "1,120.80", 0.50f),
                OrderBookLevel("12,300", "1,121.20", 0.82f)
            ),
            isPositive = true
        ),
        StockQuote(
            symbol = "TCS",
            name = "Tata Consultancy Services",
            exchange = "NSE EQUITIES",
            category = "NSE",
            price = "₹3,890.00",
            change = "-₹16.40",
            percentChange = "-0.42%",
            volume = "2.10M",
            dayRange = "₹3,875.00 - ₹3,920.00",
            dayProgress = 0.33f,
            year52Range = "₹3,313 - ₹4,254",
            year52Progress = 0.61f,
            mktCap = "₹14.07T ($168B)",
            peRatio = "30.1",
            vwap = "3,902.30",
            consensus = "HOLD",
            oscillatorsBullish = 4,
            masBullish = 6,
            sparklinePoints = listOf(4f, 6f, 5f, 10f, 12f),
            candles = sampleCandles(isBullish = false),
            spreadText = "SPREAD: ₹0.50 (0.01%)",
            bidsPercentage = 45,
            asksPercentage = 55,
            bidsVolume = "120.5K",
            asksVolume = "148.2K",
            bids = listOf(
                OrderBookLevel("3,200", "3,889.50", 0.52f),
                OrderBookLevel("4,100", "3,888.00", 0.65f),
                OrderBookLevel("2,800", "3,886.50", 0.44f)
            ),
            asks = listOf(
                OrderBookLevel("5,600", "3,890.00", 0.85f),
                OrderBookLevel("4,900", "3,892.00", 0.72f),
                OrderBookLevel("6,800", "3,894.00", 0.95f)
            ),
            isPositive = false
        ),
        StockQuote(
            symbol = "INFY",
            name = "Infosys Ltd",
            exchange = "NSE EQUITIES",
            category = "NSE",
            price = "₹1,510.25",
            change = "+₹21.60",
            percentChange = "+1.45%",
            volume = "7.80M",
            dayRange = "₹1,488.00 - ₹1,518.00",
            dayProgress = 0.76f,
            year52Range = "₹1,358 - ₹1,620",
            year52Progress = 0.58f,
            mktCap = "₹6.28T ($75B)",
            peRatio = "24.5",
            vwap = "1,504.10",
            consensus = "BUY",
            oscillatorsBullish = 8,
            masBullish = 11,
            sparklinePoints = listOf(14f, 10f, 11f, 5f, 2f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: ₹0.25 (0.02%)",
            bidsPercentage = 58,
            asksPercentage = 42,
            bidsVolume = "215.3K",
            asksVolume = "156.4K",
            bids = listOf(
                OrderBookLevel("8,900", "1,510.00", 0.82f),
                OrderBookLevel("6,400", "1,509.50", 0.60f),
                OrderBookLevel("4,100", "1,508.80", 0.45f)
            ),
            asks = listOf(
                OrderBookLevel("7,100", "1,510.25", 0.68f),
                OrderBookLevel("5,800", "1,511.00", 0.52f),
                OrderBookLevel("8,200", "1,511.50", 0.76f)
            ),
            isPositive = true
        ),
        StockQuote(
            symbol = "BHARTIARTL",
            name = "Bharti Airtel Ltd",
            exchange = "NSE EQUITIES",
            category = "NSE",
            price = "₹1,412.00",
            change = "+₹29.70",
            percentChange = "+2.15%",
            volume = "6.40M",
            dayRange = "₹1,382.00 - ₹1,419.00",
            dayProgress = 0.81f,
            year52Range = "₹850 - ₹1,430",
            year52Progress = 0.97f,
            mktCap = "₹8.05T ($96B)",
            peRatio = "45.2",
            vwap = "1,403.50",
            consensus = "STRONG BUY",
            oscillatorsBullish = 9,
            masBullish = 14,
            sparklinePoints = listOf(13f, 12f, 8f, 4f, 1f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: ₹0.30 (0.02%)",
            bidsPercentage = 61,
            asksPercentage = 39,
            bidsVolume = "240.8K",
            asksVolume = "154.2K",
            bids = listOf(
                OrderBookLevel("9,200", "1,411.70", 0.88f),
                OrderBookLevel("7,100", "1,411.00", 0.68f),
                OrderBookLevel("5,500", "1,410.20", 0.52f)
            ),
            asks = listOf(
                OrderBookLevel("6,400", "1,412.00", 0.62f),
                OrderBookLevel("4,800", "1,412.80", 0.46f),
                OrderBookLevel("8,900", "1,413.50", 0.82f)
            ),
            isPositive = true
        ),
        // US Stocks
        StockQuote(
            symbol = "NVDA",
            name = "NVIDIA Corporation",
            exchange = "NASDAQ GLOBAL",
            category = "US",
            price = "$128.40",
            change = "+$3.98",
            percentChange = "+3.20%",
            volume = "48.2M",
            dayRange = "$124.10 - $129.50",
            dayProgress = 0.79f,
            year52Range = "$40.80 - $140.76",
            year52Progress = 0.88f,
            mktCap = "$3.16T",
            peRatio = "68.2",
            vwap = "127.15",
            consensus = "STRONG BUY",
            oscillatorsBullish = 10,
            masBullish = 14,
            sparklinePoints = listOf(14f, 10f, 7f, 4f, 1f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: $0.02 (0.02%)",
            bidsPercentage = 55,
            asksPercentage = 45,
            bidsVolume = "520.1K",
            asksVolume = "425.8K",
            bids = listOf(
                OrderBookLevel("24,500", "128.38", 0.88f),
                OrderBookLevel("18,200", "128.30", 0.65f),
                OrderBookLevel("12,400", "128.20", 0.45f)
            ),
            asks = listOf(
                OrderBookLevel("19,800", "128.40", 0.72f),
                OrderBookLevel("14,600", "128.48", 0.52f),
                OrderBookLevel("22,100", "128.55", 0.80f)
            ),
            isPositive = true
        ),
        StockQuote(
            symbol = "AAPL",
            name = "Apple Inc.",
            exchange = "NASDAQ GLOBAL",
            category = "US",
            price = "$214.50",
            change = "+$1.60",
            percentChange = "+0.75%",
            volume = "32.4M",
            dayRange = "$212.80 - $215.30",
            dayProgress = 0.68f,
            year52Range = "$164.08 - $220.20",
            year52Progress = 0.90f,
            mktCap = "$3.28T",
            peRatio = "32.8",
            vwap = "214.10",
            consensus = "BUY",
            oscillatorsBullish = 7,
            masBullish = 11,
            sparklinePoints = listOf(10f, 12f, 9f, 6f, 4f),
            candles = sampleCandles(isBullish = true),
            spreadText = "SPREAD: $0.01 (0.00%)",
            bidsPercentage = 51,
            asksPercentage = 49,
            bidsVolume = "310.5K",
            asksVolume = "298.2K",
            bids = listOf(
                OrderBookLevel("16,400", "214.49", 0.82f),
                OrderBookLevel("11,200", "214.40", 0.56f),
                OrderBookLevel("8,900", "214.30", 0.44f)
            ),
            asks = listOf(
                OrderBookLevel("14,800", "214.50", 0.74f),
                OrderBookLevel("10,500", "214.60", 0.52f),
                OrderBookLevel("16,200", "214.70", 0.81f)
            ),
            isPositive = true
        ),
        StockQuote(
            symbol = "MSFT",
            name = "Microsoft Corp",
            exchange = "NASDAQ GLOBAL",
            category = "US",
            price = "$448.20",
            change = "-$0.67",
            percentChange = "-0.15%",
            volume = "18.6M",
            dayRange = "$446.50 - $451.20",
            dayProgress = 0.36f,
            year52Range = "$309.45 - $468.35",
            year52Progress = 0.87f,
            mktCap = "$3.33T",
            peRatio = "36.2",
            vwap = "449.10",
            consensus = "BUY",
            oscillatorsBullish = 6,
            masBullish = 9,
            sparklinePoints = listOf(5f, 7f, 6f, 9f, 11f),
            candles = sampleCandles(isBullish = false),
            spreadText = "SPREAD: $0.05 (0.01%)",
            bidsPercentage = 48,
            asksPercentage = 52,
            bidsVolume = "190.2K",
            asksVolume = "206.5K",
            bids = listOf(
                OrderBookLevel("7,400", "448.15", 0.68f),
                OrderBookLevel("5,900", "448.00", 0.54f),
                OrderBookLevel("3,800", "447.80", 0.35f)
            ),
            asks = listOf(
                OrderBookLevel("8,100", "448.20", 0.74f),
                OrderBookLevel("6,200", "448.40", 0.57f),
                OrderBookLevel("9,500", "448.60", 0.87f)
            ),
            isPositive = false
        )
    )

    // Oil Intelligence specific data
    val oilAlphaDrivers = listOf(
        OilMacroDriver(
            title = "OPEC+ Voluntary Cut Extension",
            description = "OPEC+ voluntary supply reduction prolonged by 1.65 mb/d through Q4.",
            highlightedText = "1.65 mb/d",
            tagColorType = "warning"
        ),
        OilMacroDriver(
            title = "Maritime Transit Surcharge",
            description = "Red Sea transit surcharge adding +$1.80/bbl on global shipping reroutes.",
            highlightedText = "+$1.80/bbl",
            tagColorType = "error"
        ),
        OilMacroDriver(
            title = "US Strategic Replenishment",
            description = "US Department of Energy replenishment purchases active up to $79/bbl baseline.",
            highlightedText = "$79/bbl",
            tagColorType = "primary"
        )
    )

    val indiaTransmissionVectors = listOf(
        IndiaTransmissionVector(
            channelName = "Current Account Deficit (CAD)",
            headline = "Every +$10/bbl crude spike expands deficit",
            impactStatement = "-$12.5 Billion (~0.3% of national GDP)",
            iconName = "receipt_long",
            type = "cad"
        ),
        IndiaTransmissionVector(
            channelName = "USD / INR Transmission",
            headline = "Import demand pressures domestic currency",
            impactStatement = "+35 to +50 bps depreciation risk",
            iconName = "currency_exchange",
            type = "fx"
        ),
        IndiaTransmissionVector(
            channelName = "Headline CPI Impact",
            headline = "Freight, logistics & petrochemical input costs",
            impactStatement = "+28 to +35 bps headline retail inflation",
            iconName = "trending_up",
            type = "cpi"
        )
    )

    val corporateMarginSensitivities = listOf(
        CorporateMarginSector("Oil Marketing (IOCL, BPCL, HPCL)", "Auto-fuel retail margin suppression", "NEGATIVE", "Under-recoveries", false),
        CorporateMarginSector("Airlines (InterGlobe / IndiGo)", "ATF costs represent ~40% of airline OpEx", "NEGATIVE", "Fuel surcharge lag", false),
        CorporateMarginSector("Paints & Coatings (Asian Paints, Berger)", "Titanium Dioxide & monomer input inflation", "NEGATIVE", "COGS expansion", false),
        CorporateMarginSector("Upstream Exploration (ONGC, Oil India)", "Net realization expands above $75 floor", "POSITIVE", "+EPS leverage", true),
        CorporateMarginSector("Complex Refiners (Reliance Industries)", "High-Nelson complexity diesel/gasoline cracks", "NEUTRAL/POS", "Expanding GRMs", null)
    )

    val maritimeChokepoints = listOf(
        MaritimeChokepoint("Strait of Hormuz", "20.8 mb/d", "0% DISRUPTION • NORMAL", "check_circle", false),
        MaritimeChokepoint("Red Sea / Mandeb", "-45% TANKERS", "Rerouted Cape Route", "warning", true),
        MaritimeChokepoint("OPEC+ Spare Buffer", "4.8 mb/d", "Held by Saudi & UAE", "security", false),
        MaritimeChokepoint("US SPR Level", "370.2M bbl", "+0.4M WoW Refill", "database", false)
    )

    val storageInventories = listOf(
        StorageTelemetry("EIA Crude Stockpiles", "BULLISH", "Consensus: -1.20M bbl forecast", "-2.54M bbl", "Draw Surplus", true),
        StorageTelemetry("Cushing Delivery Hub Storage", "TIGHT", "Operational capacity utilization: 68%", "31.4M bbl", "-0.8M WoW Draw", false)
    )

    // AI Macro Analyst Query presets
    val aiQueryPresets = listOf(
        AiMacroQueryPreset(
            id = "crude_spike",
            buttonChipTitle = "Crude +10% → India Impact",
            queryText = "\"How would a sustained 10% rise in Brent crude affect India's inflation, rupee, NIFTY 50, and major Indian sectors?\"",
            brentPrice = "$85.42 +1.3%",
            usdinrPrice = "83.48 -0.05%",
            niftyPrice = "22,514 +0.6%",
            cpiPrice = "4.75% Stable",
            rollingBetaText = "Empirical 10-year rolling beta between Brent Crude and NIFTY 50 is -0.22 during global supply bottlenecks, but inverts to +0.15 during concurrent global demand expansion cycles.",
            importDependencyText = "India currently imports ~87.3% of its crude oil requirements.",
            importBillStat = "$132.4B annualized",
            tradeDeficitImpact = "-$12.5B / yr",
            fxDepreciationRange = "-0.4% to -0.6%",
            cpiPassThrough = "+30 bps",
            scenarios = listOf(
                AiProbabilisticScenario(
                    title = "Base Case: Managed Absorption",
                    probabilityPercent = 55,
                    peakTarget = "$92/bbl",
                    durationHorizon = "45-60 Days",
                    analysisText = "Brent peaks near $92 before settling. RBI absorbs FX shock via targeted FX intervention. NIFTY 50 undergoes brief 1.5% consolidation. Paints (Berger, Asian Paints) and Aviation (IndiGo) face margin compression; Upstream E&P (ONGC, Oil India) rallies +4-7%.",
                    niftyImpact = "-1.5%",
                    usdinrTarget = "83.85",
                    sectorDelta = "ONGC ▲ / Paints ▼",
                    scenarioType = "base"
                ),
                AiProbabilisticScenario(
                    title = "Bullish De-escalation",
                    probabilityPercent = 25,
                    peakTarget = "$76-$78/bbl",
                    durationHorizon = "< 20 Days",
                    analysisText = "Mideast geopolitical premium rapidly evaporates. Supply routes normalize. Brent drops back below $80. Indian equities rally aggressively to new highs driven by sustained foreign institutional (FII) net purchasing and margin relief across consumer discretionary.",
                    niftyImpact = "+2.8% ATH",
                    usdinrTarget = "83.20",
                    sectorDelta = "Autos & Paints ▲",
                    scenarioType = "bull"
                ),
                AiProbabilisticScenario(
                    title = "Bearish Sustained Spike",
                    probabilityPercent = 20,
                    peakTarget = "> $100/bbl",
                    durationHorizon = "Multi-Quarter",
                    analysisText = "Hormuz shipping disruptions choke crude throughput. Crude sustains $100+. USD/INR tests 84.40. RBI forced into hawkish pause, shelving planned repo rate easing. Broad equity index retreats 4-6% with intense foreign portfolio outflows.",
                    niftyImpact = "-4.5% Correction",
                    usdinrTarget = "84.40",
                    sectorDelta = "RBI Rate Cut Delayed",
                    scenarioType = "bear"
                )
            ),
            sectorSensitivities = listOf(
                SectorBetaSensitivity("Upstream Oil & Gas", "+4.8%", 0.82f, true),
                SectorBetaSensitivity("OMCs (Retail)", "-3.2%", 0.58f, false),
                SectorBetaSensitivity("Aviation (ATF Cost)", "-4.9%", 0.74f, false),
                SectorBetaSensitivity("Paints & Adhesives", "-2.6%", 0.48f, false),
                SectorBetaSensitivity("Bank NIFTY", "-1.1%", 0.25f, false)
            ),
            confirmingSignals = listOf(
                "WTI prompt-month spread backwardation widens above +$1.85/bbl.",
                "Weekly US EIA crude commercial inventory draws exceed 4.0M barrels.",
                "RBI weekly statistical bulletin shows foreign reserves draw > $2.0B."
            ),
            invalidatingSignals = listOf(
                "OPEC+ emergency communiqué restores voluntary output cuts prematurely.",
                "US domestic crude production prints weekly record exceeding 13.55 mb/d.",
                "Chinese industrial PMI retreats below 49.0 signaling aggregate demand erosion."
            ),
            citations = listOf("RBI Monthly Bulletin (May 2024)", "EIA Petroleum Status Rpt", "MoPNG Petroleum Statistics", "Bloomberg FX Consensus"),
            confidencePercent = "88.4%"
        ),
        AiMacroQueryPreset(
            id = "fed_easing",
            buttonChipTitle = "Fed -25bps → RBI Policy",
            queryText = "\"If the US Federal Reserve cuts rates by 25bps, how quickly does the RBI follow and what is the sensitivity for Bank NIFTY?\"",
            brentPrice = "$85.42 +1.3%",
            usdinrPrice = "83.48 -0.05%",
            niftyPrice = "22,514 +0.6%",
            cpiPrice = "4.75% Stable",
            rollingBetaText = "Historically, RBI policy lag behind Fed easing cycles averages 4 to 6 months depending on monsoon food basket trends and USD/INR real effective exchange rate (REER).",
            importDependencyText = "Foreign Institutional Investors (FII) equity inflow elasticity increases 1.8x per 25bps US spread compression.",
            importBillStat = "+$4.2B FII flow delta",
            tradeDeficitImpact = "+$6.5B Capital Inflow",
            fxDepreciationRange = "+0.5% (INR strengthening)",
            cpiPassThrough = "-15 bps (borrowing relief)",
            scenarios = listOf(
                AiProbabilisticScenario(
                    title = "Base Case: Staggered RBI Follow-Through",
                    probabilityPercent = 60,
                    peakTarget = "6.25% Repo",
                    durationHorizon = "Q3-Q4 Horizon",
                    analysisText = "RBI initiates 25bps rate cut at subsequent MPC meeting following Fed move, citing stabilized core inflation. Bank NIFTY expands 2.5-3.8% on NIM stabilization and lower wholesale funding yields.",
                    niftyImpact = "+2.2%",
                    usdinrTarget = "83.15",
                    sectorDelta = "Private Banks & NBFCs ▲",
                    scenarioType = "bull"
                ),
                AiProbabilisticScenario(
                    title = "Cautious Hawkish Pause",
                    probabilityPercent = 30,
                    peakTarget = "6.50% Unchanged",
                    durationHorizon = "2 MPC Cycles",
                    analysisText = "Vegetable and food price volatility keeps MPC 4-2 divided; RBI waits for second consecutive sub-4.5% CPI print before joining global easing cycle.",
                    niftyImpact = "+0.4%",
                    usdinrTarget = "83.40",
                    sectorDelta = "Rate Sensitives Neutral",
                    scenarioType = "base"
                ),
                AiProbabilisticScenario(
                    title = "Global Reflation Resurgence",
                    probabilityPercent = 10,
                    peakTarget = "Neutral Stance",
                    durationHorizon = "6+ Months",
                    analysisText = "Stronger US jobs data curtails subsequent Fed rate path; capital flight volatility forces defensive liquidity absorption.",
                    niftyImpact = "-1.8%",
                    usdinrTarget = "83.95",
                    sectorDelta = "IT Exporters ▲ / Banks ▼",
                    scenarioType = "bear"
                )
            ),
            sectorSensitivities = listOf(
                SectorBetaSensitivity("NBFCs & Housing Fin", "+3.8%", 0.75f, true),
                SectorBetaSensitivity("Private Banks", "+2.9%", 0.62f, true),
                SectorBetaSensitivity("Real Estate (Nifty Realty)", "+4.2%", 0.85f, true),
                SectorBetaSensitivity("IT Services", "-0.8%", 0.20f, false),
                SectorBetaSensitivity("FMCG & Staples", "+1.1%", 0.30f, true)
            ),
            confirmingSignals = listOf(
                "US 2-Year Treasury yield falls below 4.40%.",
                "India core inflation (excluding food and fuel) remains below 3.50%.",
                "Systemic banking liquidity in India persists in surplus > ₹1.0 Lakh Cr."
            ),
            invalidatingSignals = listOf(
                "Monsoon spatial rainfall distribution deficit exceeds 10% in agrarian belts.",
                "US CPI prints hotter than consensus, reversing Fed rate expectations."
            ),
            citations = listOf("RBI MPC Minutes", "Federal Reserve FOMC Projections", "Bloomberg Fixed Income Consensus"),
            confidencePercent = "91.2%"
        ),
        AiMacroQueryPreset(
            id = "tariffs_tech",
            buttonChipTitle = "Tariffs → Nasdaq vs Nifty",
            queryText = "\"Assess cross-market dispersion between Nasdaq 100 tech giants and Nifty IT under elevated semiconductor trade restrictions.\"",
            brentPrice = "$85.42 +1.3%",
            usdinrPrice = "83.48 -0.05%",
            niftyPrice = "22,514 +0.6%",
            cpiPrice = "4.75% Stable",
            rollingBetaText = "Cross-border tech beta between Nasdaq Tech and Nifty IT stands at 0.58. Nifty IT operates as a pure software services OpEx play rather than hardware CapEx, serving as a relative defensive hedge.",
            importDependencyText = "Indian IT firms derive ~52% of total consolidated revenues directly from North American enterprise clients.",
            importBillStat = "$194B software exports",
            tradeDeficitImpact = "+$14.2B Service Export",
            fxDepreciationRange = "USD strength benefits margins",
            cpiPassThrough = "Neutral domestic impact",
            scenarios = listOf(
                AiProbabilisticScenario(
                    title = "Base Case: Defensive Resilience",
                    probabilityPercent = 50,
                    peakTarget = "Nifty IT +3.5%",
                    durationHorizon = "1-2 Quarters",
                    analysisText = "While hardware restrictions elevate hardware costs for hyperscalers, demand for enterprise AI transformation and automation drives continued deal flow for Indian IT system integrators.",
                    niftyImpact = "+1.1%",
                    usdinrTarget = "83.60",
                    sectorDelta = "TCS & Infosys Outperform",
                    scenarioType = "base"
                ),
                AiProbabilisticScenario(
                    title = "CapEx Freeze Contagion",
                    probabilityPercent = 35,
                    peakTarget = "Nasdaq -5%",
                    durationHorizon = "90 Days",
                    analysisText = "Global semiconductor supply re-routing delays enterprise cloud deployments, slowing pipeline conversions across Fortune 500 discretionary tech budgets.",
                    niftyImpact = "-2.1%",
                    usdinrTarget = "83.75",
                    sectorDelta = "Tier-2 Midcap IT ▼",
                    scenarioType = "bear"
                ),
                AiProbabilisticScenario(
                    title = "Enterprise AI Integration Boom",
                    probabilityPercent = 15,
                    peakTarget = "Nasdaq +8%",
                    durationHorizon = "Multi-Quarter",
                    analysisText = "Accelerated generative enterprise adoption yields margin expansion; Indian tier-1 IT firms capture significant automation modernization deals.",
                    niftyImpact = "+3.4%",
                    usdinrTarget = "83.25",
                    sectorDelta = "NIFTY IT ATH ▲",
                    scenarioType = "bull"
                )
            ),
            sectorSensitivities = listOf(
                SectorBetaSensitivity("Tier-1 IT (TCS, Infy)", "+2.4%", 0.55f, true),
                SectorBetaSensitivity("Midcap IT & ER&D", "+3.6%", 0.70f, true),
                SectorBetaSensitivity("Telecom & Networks", "-1.4%", 0.32f, false),
                SectorBetaSensitivity("Industrial Electronics", "-2.8%", 0.50f, false)
            ),
            confirmingSignals = listOf(
                "US enterprise IT budget survey prints net positive expansion > 6%.",
                "USD/INR stability provides gross margin cushion > 24% for tier-1 IT."
            ),
            invalidatingSignals = listOf(
                "US enterprise discretionary software cancellations rise in Q2 reports."
            ),
            citations = listOf("NASSCOM Annual Strategic Review", "Gartner Global IT Spending Forecast"),
            confidencePercent = "84.7%"
        ),
        AiMacroQueryPreset(
            id = "inr_breakeven",
            buttonChipTitle = "USD/INR 84.00 Breakeven",
            queryText = "\"What are the macro and equity market implications if USD/INR permanently breaks above 84.00?\"",
            brentPrice = "$85.42 +1.3%",
            usdinrPrice = "83.48 -0.05%",
            niftyPrice = "22,514 +0.6%",
            cpiPrice = "4.75% Stable",
            rollingBetaText = "RBI's foreign exchange intervention band has strongly anchored USD/INR around 83.30-83.60 using record $650B+ FX reserve war chest. A breach of 84.00 signals non-linear dollar strength or severe terms-of-trade deterioration.",
            importDependencyText = "Import cover remains robust at over 11 months of merchandise imports.",
            importBillStat = "$652B FX reserves",
            tradeDeficitImpact = "Import cost inflation",
            fxDepreciationRange = "Tests 84.25-84.50 band",
            cpiPassThrough = "+22 bps imported inflation",
            scenarios = listOf(
                AiProbabilisticScenario(
                    title = "Orderly Managed Float",
                    probabilityPercent = 65,
                    peakTarget = "83.85-84.10",
                    durationHorizon = "Gradual",
                    analysisText = "RBI conducts smooth spot and NDF interventions, allowing calculated depreciation to preserve export competitiveness without triggering capital flight.",
                    niftyImpact = "-0.8%",
                    usdinrTarget = "83.95",
                    sectorDelta = "Exporters (Pharma/IT) ▲",
                    scenarioType = "base"
                ),
                AiProbabilisticScenario(
                    title = "Aggressive FX Defense",
                    probabilityPercent = 25,
                    peakTarget = "Sub-83.60 Reversal",
                    durationHorizon = "Immediate",
                    analysisText = "RBI deploys heavy dollar selling across spot and forward desks, burning reserves to aggressively reject the 84.00 level.",
                    niftyImpact = "+1.2%",
                    usdinrTarget = "83.45",
                    sectorDelta = "Importers & Rupee Assets ▲",
                    scenarioType = "bull"
                ),
                AiProbabilisticScenario(
                    title = "Disorderly Slide",
                    probabilityPercent = 10,
                    peakTarget = "> 84.60",
                    durationHorizon = "Acute",
                    analysisText = "Broad dollar spike overwhelms Asian central banks; FII outflows accelerate from domestic debt and equities.",
                    niftyImpact = "-3.6%",
                    usdinrTarget = "84.60",
                    sectorDelta = "Broad Market Retreat ▼",
                    scenarioType = "bear"
                )
            ),
            sectorSensitivities = listOf(
                SectorBetaSensitivity("IT Services Exporters", "+3.1%", 0.65f, true),
                SectorBetaSensitivity("Pharma Formulations", "+2.5%", 0.52f, true),
                SectorBetaSensitivity("OMCs & Refiners", "-3.5%", 0.60f, false),
                SectorBetaSensitivity("Metals & Mining", "+1.2%", 0.28f, true),
                SectorBetaSensitivity("Consumer Discretionary", "-2.2%", 0.45f, false)
            ),
            confirmingSignals = listOf(
                "Dollar Index (DXY) sustains above 106.50.",
                "Crude oil prices sustain above $90/bbl."
            ),
            invalidatingSignals = listOf(
                "RBI governor re-asserts committed defense of currency stability.",
                "Substantial foreign direct investment (FDI) inflows in private markets."
            ),
            citations = listOf("RBI Weekly Statistical Supplement", "BIS Real Effective Exchange Rates"),
            confidencePercent = "86.1%"
        )
    )

    private fun sampleCandles(isBullish: Boolean): List<CandleStickData> {
        val list = mutableListOf<CandleStickData>()
        val base = if (isBullish) 100f else 115f
        for (i in 0 until 17) {
            val isGreen = if (isBullish) {
                i !in listOf(2, 4, 7, 10, 11, 15)
            } else {
                i in listOf(2, 5, 8, 12)
            }
            val open = base + (if (isBullish) i * 1.5f else -i * 1.2f)
            val close = open + (if (isGreen) 3.5f else -3.0f)
            val high = maxOf(open, close) + 2.5f
            val low = minOf(open, close) - 2.0f
            val volume = 0.4f + (i % 5) * 0.12f
            list.add(CandleStickData(open, high, low, close, volume))
        }
        return list
    }
}
