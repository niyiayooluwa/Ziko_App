package com.ziko.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.navigation.Screen

@Composable
fun FloatingNavBar(navController: NavController, currentRoute: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lessons tab
                val isLessonsActive = currentRoute == Screen.Home.route
                NavItem(
                    icon = Icons.Filled.MenuBook,
                    label = "Lessons",
                    isActive = isLessonsActive,
                    activeColor = Color(0xFF5E5AEC),
                    inactiveColor = Color.Gray,
                    onClick = {
                        if (!isLessonsActive) {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )

                // Assessment tab
                val isAssessmentActive = currentRoute == Screen.Assessment.route
                NavItem(
                    icon = Icons.Filled.CheckCircle,
                    label = "Assessment",
                    isActive = isAssessmentActive,
                    activeColor = Color(0xFF5E5AEC),
                    inactiveColor = Color.Gray,
                    onClick = {
                        if (!isAssessmentActive) {
                            navController.navigate(Screen.Assessment.route) {
                                popUpTo(Screen.Home.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavItem(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    onClick: () -> Unit
) {
    val itemWidth by animateDpAsState(
        targetValue = if (isActive) 130.dp else 60.dp,
        animationSpec = tween(300),
        label = "width"
    )

    Row(
        modifier = Modifier
            .width(itemWidth)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isActive) activeColor else inactiveColor,
            modifier = Modifier.padding(end = if (isActive) 8.dp else 0.dp)
        )

        // Label (only shown when active)
        AnimatedVisibility(
            visible = isActive,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Text(
                text = label,
                color = activeColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}