package com.ziko.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A customizable horizontal line for UI separation.
 *
 * Can be rendered as a solid or dashed line, with optional explicit width.
 * Useful as a visual divider between content blocks.
 *
 * @param isDashed If true, renders a dashed line. Otherwise, renders a solid line.
 * @param width Optional width for the line. Defaults to intrinsic width (wrap content).
 */
@Composable
fun LineUI(
    isDashed: Boolean = true,
    width: Dp = Dp.Unspecified
) {
    // Base modifier sets height and optionally sets width if specified
    val modifier = Modifier
        .height(1.dp)
        .then(if (width != Dp.Unspecified) Modifier.width(width) else Modifier)

    Canvas(modifier = modifier) {
        val strokeWidth = 1.dp.toPx()

        if (isDashed) {
            // Dash pattern: 10f gap, 10f line
            val dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                color = Color(0xFFDDE4EE),
                strokeWidth = strokeWidth,
                pathEffect = dashPathEffect
            )
        } else {
            // Solid line
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                color = Color(0xFFDDE4EE),
                strokeWidth = strokeWidth
            )
        }
    }
}
