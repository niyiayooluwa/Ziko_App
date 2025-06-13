package com.ziko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ziko.navigation.NavGraph
import com.ziko.ui.theme.ZikoTheme
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Let Compose draw behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Make bars transparent so we can color them from Compose
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            ZikoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrentScreen()
                }
            }
        }
    }
}

@Composable
fun CurrentScreen() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavGraph(
            navController = navController,
            innerPadding = innerPadding
        )
    }
}