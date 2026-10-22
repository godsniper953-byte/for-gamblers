---
name: Terminal Intelligence System
colors:
  surface: '#0f131c'
  surface-dim: '#0f131c'
  surface-bright: '#353942'
  surface-container-lowest: '#0a0e16'
  surface-container-low: '#181c24'
  surface-container: '#1c2028'
  surface-container-high: '#262a33'
  surface-container-highest: '#31353e'
  on-surface: '#dfe2ee'
  on-surface-variant: '#bcc9cd'
  inverse-surface: '#dfe2ee'
  inverse-on-surface: '#2c3039'
  outline: '#869397'
  outline-variant: '#3d494c'
  surface-tint: '#4cd7f6'
  primary: '#4cd7f6'
  on-primary: '#003640'
  primary-container: '#06b6d4'
  on-primary-container: '#00424f'
  inverse-primary: '#00687a'
  secondary: '#4edea3'
  on-secondary: '#003824'
  secondary-container: '#00a572'
  on-secondary-container: '#00311f'
  tertiary: '#ffb95f'
  on-tertiary: '#472a00'
  tertiary-container: '#e79400'
  on-tertiary-container: '#563400'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#acedff'
  primary-fixed-dim: '#4cd7f6'
  on-primary-fixed: '#001f26'
  on-primary-fixed-variant: '#004e5c'
  secondary-fixed: '#6ffbbe'
  secondary-fixed-dim: '#4edea3'
  on-secondary-fixed: '#002113'
  on-secondary-fixed-variant: '#005236'
  tertiary-fixed: '#ffddb8'
  tertiary-fixed-dim: '#ffb95f'
  on-tertiary-fixed: '#2a1700'
  on-tertiary-fixed-variant: '#653e00'
  background: '#0f131c'
  on-background: '#dfe2ee'
  surface-variant: '#31353e'
typography:
  headline-lg:
    fontFamily: Space Grotesk
    fontSize: 1.75rem
    fontWeight: '700'
    lineHeight: 2.25rem
    letterSpacing: -0.02em
  headline-lg-mobile:
    fontFamily: Space Grotesk
    fontSize: 1.375rem
    fontWeight: '700'
    lineHeight: 1.75rem
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Space Grotesk
    fontSize: 1.25rem
    fontWeight: '600'
    lineHeight: 1.625rem
    letterSpacing: -0.015em
  headline-sm:
    fontFamily: Space Grotesk
    fontSize: 1rem
    fontWeight: '600'
    lineHeight: 1.375rem
    letterSpacing: 0em
  body-lg:
    fontFamily: Hanken Grotesk
    fontSize: 0.9375rem
    fontWeight: '400'
    lineHeight: 1.375rem
    letterSpacing: 0em
  body-md:
    fontFamily: Hanken Grotesk
    fontSize: 0.8125rem
    fontWeight: '400'
    lineHeight: 1.125rem
    letterSpacing: 0em
  body-sm:
    fontFamily: Hanken Grotesk
    fontSize: 0.75rem
    fontWeight: '400'
    lineHeight: 1rem
    letterSpacing: 0.01em
  ticker-lg:
    fontFamily: JetBrains Mono
    fontSize: 1.25rem
    fontWeight: '600'
    lineHeight: 1.5rem
    letterSpacing: -0.03em
  ticker-md:
    fontFamily: JetBrains Mono
    fontSize: 0.875rem
    fontWeight: '500'
    lineHeight: 1.125rem
    letterSpacing: -0.02em
  label-md:
    fontFamily: JetBrains Mono
    fontSize: 0.75rem
    fontWeight: '500'
    lineHeight: 0.875rem
    letterSpacing: 0.03em
  label-xs:
    fontFamily: JetBrains Mono
    fontSize: 0.625rem
    fontWeight: '600'
    lineHeight: 0.75rem
    letterSpacing: 0.06em
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  gutter: 0.5rem
  gutter-md: 0.75rem
  gutter-lg: 1rem
  margin: 0.75rem
  margin-md: 1rem
  margin-lg: 1.5rem
  space-xs: 0.25rem
  space-sm: 0.375rem
  space-md: 0.5rem
  space-lg: 0.75rem
  space-xl: 1rem
---

## Brand & Style

The design system embodies the rigor, velocity, and high-density precision required by quantitative analysts, portfolio managers, and institutional researchers. Synthesizing the high-throughput keyboard-first utility of classic financial command consoles with the visual lucidity of contemporary analytics platforms, the interface functions as a cockpit for mission-critical capital decisions.

The design movement combines **Data-Dense Modern Minimalist** with **Technical Industrialism**:
- **Density Over Decoration:** UI chrome is compressed; padding is tight and purposeful to maximize visible viewport metrics and multi-pane analytical depth.
- **Utilitarian Hierarchy:** Form strictly follows market telemetry. Structural grids, razor-sharp dividers, and monospace tabular alignments take precedence over decorative flourishes.
- **High Information Signal:** Color is employed strictly as data notation (direction, delta, volatility, alert state, intelligence tier) rather than aesthetic styling.

## Colors

The palette establishes an ultra-dark institutional canvas that prevents eye fatigue across 14-hour market desks while delivering instant optical divergence for delta variations and model forecasts.

### Surface Architecture
- **Canvas Base:** `#0B0F17` (Deep midnight charcoal base container)
- **Panel / Card Surface:** `#111827` (Primary component container)
- **Elevated Hover / Flyout Surface:** `#1E293B` (Inspectors, active panes, contextual popovers)
- **Structural Outlines:** `#1F293D` (Subtle slate gridlines and cell delimitations)

### Functional & Signal Colors
- **Telemetry Cyan (`#06B6D4`):** Designates macro indicators, predictive AI signals, focused metrics, and execution triggers.
- **Positive Delta (`#10B981`):** Pure emerald tick indicating positive price returns, yield upside, or risk mitigation.
- **Negative Delta (`#EF4444`):** Subdued crimson indicating negative price returns, drawdowns, and stop-loss anomalies.
- **Amber Advisory (`#F59E0B`):** Warning flags, macroeconomic event horizons, volatility regime shifts, and pending synthetic orders.
- **Data Neutral / Muted:** `#64748B` for tertiary metadata, inactive indices, and timestamp tags; `#E2E8F0` for primary legible figures.

## Typography

The typographic hierarchy prioritizes strict tabular alignment, character differentiation, and zero ambiguous glyphs (such as 0 vs O, 1 vs l).

- **Headlines (`Space Grotesk`):** Modern geometric tech sans-serif utilized for terminal quadrant headers, high-level intelligence syntheses, and executive summaries.
- **Interface & Reading (`Hanken Grotesk`):** Clean, neutral corporate sans-serif engineered for continuous scanning of analytical briefs, trade execution confirmations, and news feeds.
- **Data, Tickers, & Metrics (`JetBrains Mono`):** Tabular, monospaced numerical engine applied to order book matrices, live prices, timestamps, PnL ratios, and micro badges. Forces numerical values to align identically along horizontal columns.

## Layout & Spacing

The terminal is governed by a **high-density, multi-pane fluid workspace** optimized for multi-monitor desktop setups and dockable window configurations.

- **Workspace Layout:** Uses a continuous 24-column CSS grid for fine-grained analytical docking (e.g., Depth Chart [6 cols] | Execution Ladder [6 cols] | Model Signals [12 cols]).
- **Spacing Rhythm:** Standardized on an ultra-compact 4px grid. Standard padding between metrics is locked at `0.25rem` (`space-xs`) or `0.375rem` (`space-sm`), reserving `0.75rem` (`space-lg`) strictly for structural pane gutters.
- **Viewport Scaling:**
  - **Desktop (≥ 1440px):** Multi-pane split screen (3-4 panes wide), persistent system status bar, fixed 8px gutters, and uncollapsed order ladders.
  - **Tablet (768px – 1439px):** Dynamic dual-panel layout with sliding drawers for execution engines and macro tickers; 12-column grid.
  - **Mobile (< 768px):** Single-column stacked stream. Ticker feeds pin to top viewport; secondary technical matrices fold into swipeable tabbed navigation.

## Elevation & Depth

This system avoids floating cloud shadows, drop shadows, or skeuomorphic bevels, which add visual noise in information-saturated trading software. Depth is achieved via **Tonal Tiering and Precise 1px Slate Separators**:

1. **Base Layer (L0 - `#0B0F17`):** The master canvas, status strip, and persistent terminal backdrop.
2. **Structural Container (L1 - `#111827`):** Analytical tiles, charts, order book tables, and metrics clusters. Defined strictly by a `1px solid #1F293D` border.
3. **Interactive & Elevated State (L2 - `#1E293B`):** Hovered rows, selected cells, and tooltips. Tooltips use a hairline border (`1px solid #06B6D4` or `1px solid #334155`) with an ambient dark occlusion shadow: `0 4px 16px -2px rgba(0, 0, 0, 0.75)`.
4. **Focused Modals / Overlays (L3):** Background blur (`backdrop-filter: blur(4px)`) over an ultra-dark semi-transparent veil (`rgba(11, 15, 23, 0.85)`), enclosing critical order submission confirmation dialogs.

## Shapes

The interface leverages sharp, engineered corners to maximize pixel efficiency and preserve the authoritative look of financial machinery.

- **Standard Radius (`rounded` = 0.25rem / 4px):** Form inputs, buttons, quadrant panels, and table selection highlights.
- **Small Accent Radius (`rounded-sm` = 0.125rem / 2px):** Monospace badges, status markers, delta indicators, and timeframe chips.
- **Pill Radius (`rounded-full`):** Reserved exclusively for live network connection dots (e.g., WebSocket heartbeat, market open/closed beacons).

## Components

### Buttons & Action Triggers
- **Primary Execution (Cyan):** `#06B6D4` background, `#0B0F17` bold typography, flat hover with brightness shift (110%). Focus outline: `2px solid #06B6D4` with `2px` offset.
- **Buy / Bid Triggers:** Deep emerald tint (`#064E3B`) with `#10B981` border and crisp white numerical labels.
- **Sell / Ask Triggers:** Deep crimson tint (`#7F1D1D`) with `#EF4444` border and crisp white numerical labels.
- **Terminal Key Triggers:** Dark slate fill (`#1E293B`), `1px solid #1F293D`, monospace shortcut badge nested on right edge (e.g., `ALT+1`).

### Data Cards & Tiles
- Framed in `#111827` with a razor-thin `1px solid #1F293D` frame.
- Headers are compressed to `24px` height, featuring upper-case monospace taxonomy, real-time latency ping, and active tool controls.
- Interactive states avoid movement; focus is communicated by changing the border color from `#1F293D` to `#06B6D4`.

### Chips, Multi-Timeframe Pills & Badges
- **Timeframe Selector:** Inline segmented control containing chips (`1m`, `5m`, `15m`, `1H`, `4H`, `1D`, `YTD`). Unselected: transparent background with `#64748B` text. Selected: `#1E293B` background with `#06B6D4` text and `1px solid #06B6D4`.
- **System Badges (`DEMO`, `LIVE`, `SIMULATED`):** Micro monospace text (`0.625rem`), uppercase, `2px` padding, high-contrast borders matching environment state.

### Lists & Tabular Order Ladders
- Zero horizontal cell padding at edge intersections; row height pinned to a compact `20px` or `24px`.
- Alternating subtle striping is omitted in favor of hover highlighting via `#1E293B`.
- Numerical columns right-align; labels and tickers left-align.
- Depth heatmaps render as semi-transparent volume fills beneath the text: green (`rgba(16, 185, 129, 0.15)`) for bid volume; red (`rgba(239, 68, 68, 0.15)`) for ask volume.

### Micro-Sparklines & Vector Graphs
- Micro visual telemetry embedded directly inside table rows (width: `64px`, height: `16px`).
- Polyline width: `1.25px`.
- Trend-colored: `#10B981` (bullish horizon) or `#EF4444` (bearish horizon) with zero area fill to maintain minimal cognitive load.

### Form Inputs & Terminal Command Line
- Inset `#0B0F17` field background with `1px solid #1F293D` borders.
- Monospace typographic inputs with instantaneous parameter formatting.
- Dedicated prompt symbol (`>`) styled in `#06B6D4` for keyboard terminal command executions.