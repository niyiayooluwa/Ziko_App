package com.ziko.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.navigation.Screen

@Composable
fun FloatingNavBar(navController: NavController, currentRoute: String) {
    // Simplified structure with exact specifications
    Surface(
        modifier = Modifier
            .width(263.dp)
            .height(80.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(40.dp), spotColor = Color(0xFFE5E5E5)), // Shadow color from Figma
        shape = RoundedCornerShape(40.dp),  // Fully rounded corners
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .width(263.dp)
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lessons tab
            val isLessonsActive = currentRoute == Screen.Home.route
            NavItem(
                activeIcon = R.drawable.book,
                inactiveIcon = R.drawable.book_1,
                label = "Lessons",
                isActive = isLessonsActive,
                activeColor = Color(0xFF5E5AEC),
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
                activeIcon = R.drawable.task_1,
                inactiveIcon = R.drawable.task,
                label = "Assessment",
                isActive = isAssessmentActive,
                activeColor = Color(0xFF5E5AEC),
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

@Composable
fun NavItem(
    activeIcon: Int,
    inactiveIcon: Int,
    label: String,
    activeColor: Color,
    isActive: Boolean,
    onClick: () -> Unit
) {
    // Using a fixed width to avoid layout jumps during navigation
    val itemWidth = if (isActive) 130.dp else 60.dp

    // Make the entire surface clickable for better touch area
    Surface(
        modifier = Modifier
            .width(itemWidth)
            .clickable(onClick = onClick),
        color = Color.Transparent // Transparent to not show background
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with larger touch target
            Icon(
                painter = painterResource(if (isActive) activeIcon else inactiveIcon),
                contentDescription = label,
                modifier = Modifier
                    .size(28.dp) // Larger icon for better visibility
                    .padding(end = if (isActive) 8.dp else 0.dp)
            )

            // Label (only shown when active)
            AnimatedVisibility(
                visible = isActive,
                enter = fadeIn(animationSpec = tween(100)) + expandHorizontally(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100)) + shrinkHorizontally(animationSpec = tween(100))
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
}