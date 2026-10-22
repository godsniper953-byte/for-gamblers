package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BullishEmerald
import com.example.ui.theme.BearishCrimson

@Composable
fun SparklineView(
    points: List<Float>,
    isPositive: Boolean,
    modifier: Modifier = Modifier,
    width: Dp = 56.dp,
    height: Dp = 16.dp,
    strokeWidth: Float = 3.5f
) {
    val strokeColor = if (isPositive) BullishEmerald else BearishCrimson
    
    Canvas(modifier = modifier.width(width).height(height)) {
        if (points.size < 2) return@Canvas
        
        val minVal = points.minOrNull() ?: 0f
        val maxVal = points.maxOrNull() ?: 1f
        val range = if (maxVal - minVal == 0f) 1f else maxVal - minVal
        
        val path = Path()
        val stepX = size.width / (points.size - 1)
        
        points.forEachIndexed { index, value ->
            val normalizedY = size.height - ((value - minVal) / range) * (size.height - 4f) - 2f
            val x = index * stepX
            if (index == 0) {
                path.moveTo(x, normalizedY)
            } else {
                path.lineTo(x, normalizedY)
            }
        }
        
        drawPath(
            path = path,
            color = strokeColor,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
