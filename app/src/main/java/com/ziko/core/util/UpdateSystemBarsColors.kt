package com.ziko.core.util

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * File: UpdateSystemBarsColors.kt
 * Layer: core (platform-specific UI utility)
 *
 * Description:
 * Updates the system status bar and navigation bar colors in a Compose app, including icon visibility
 * adjustment based on background brightness (light vs dark themes).
 *
 * This utility ensures that the system bars match the app's current theme or screen design and
 * automatically determines whether icons should be light or dark for contrast.
 *
 * Typically used in screens with dynamic color schemes or theming.
 *
 * @param topColor The desired color for the status bar (top system bar).
 * @param bottomColor The desired color for the navigation bar (bottom system bar).
 *
 * @sample UpdateSystemBarsColors(Color.Black, Color.White)
 */
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
