package com.ziko.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pressed

@Composable
fun SuccessIndicator (
    condition: Boolean,
    onClick: () -> Unit,
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                when (condition) {
                    true -> Color(0xFF12D18E)
                    false -> Color(0xFFf75555)
                }
            )
            .padding(
                top = 24.dp,
                bottom = 40.dp,
                start = 24.dp,
                end = 24.dp,
            ),
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (condition) {
                    true -> Icons.Filled.CheckCircle
                    false -> Icons.Filled.Cancel
                },
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            Text(
                text = when (condition) {
                    true -> "You nailed it."
                    false -> "Whoops! Not quite yet."
                },
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color.White
            )
        }

        Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFFF0eDff))
                        .neumorphic(
                            neuShape = Pressed.Rounded(radius = 4.dp),
                            lightShadowColor = Color.White,
                            darkShadowColor = Color(0xFFd3d3d3),
                            strokeWidth = 4.dp,
                            elevation = 4.dp
                        )
                        .clickable{
                            onClick()
                        }
            ) {
                Text(
                        text = when (condition) {
                            true -> "Next"
                            false -> "Try again"
                        },
                        color = when (condition) {
                            true -> Color(0xFF12D18E)
                            false -> Color(0xFFf75555)
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }
    }
}