package com.ziko.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom top app bar with a larger centered title and optional back navigation button.
 *
 * This variant is designed to have a more prominent visual weight than the default app bar,
 * and it's intended for top-level pages or screens with minimal UI clutter.
 *
 * @param title The title displayed in the center of the app bar.
 * @param onNavigationClick Optional lambda invoked when the back button is clicked.
 * If null, no button is shown and spacing is preserved to avoid layout shift.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBiggerTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp, // Bigger than default (16.sp)
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                // Maintain spacing if no back icon to prevent text shifting
                Box(modifier = Modifier.size(24.dp))
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF410FA3) // Custom background
        )
    )
}
