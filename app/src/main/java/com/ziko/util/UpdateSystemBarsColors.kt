package com.ziko.util

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun UpdateSystemBarsColors(
    topColor: Color,
    bottomColor: Color
) {
    val activity = LocalActivity.current
    val window = activity?.window
    val view = LocalView.current

    // Set actual colors
    if (window != null) {
        window.statusBarColor = topColor.toArgb()
        window.navigationBarColor = bottomColor.toArgb()
    }

    // Icon color contrast (light vs dark icons)
    if (window != null) {
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = topColor.luminance() > 0.5f
            isAppearanceLightNavigationBars = bottomColor.luminance() > 0.5f
        }
    }
}
