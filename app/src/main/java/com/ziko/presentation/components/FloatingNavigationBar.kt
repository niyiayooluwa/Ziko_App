package com.ziko.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
            .animateContentSize()
            .height(80.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(40.dp), spotColor = Color(0xFFE5E5E5)), // Shadow color from Figma
        shape = RoundedCornerShape(40.dp),  // Fully rounded corners
        color = Color.White
    ) {
        Row(
            modifier = Modifier
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
    isActive: Boolean,
    activeColor: Color = Color(0xFF5E5AEC),
    onClick: () -> Unit
) {
    // Make the entire surface clickable for better touch area
    Surface(
        modifier = Modifier
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
                tint = when (isActive) {
                    true -> activeColor
                    false -> Color.Gray
                },
                modifier = Modifier
                    .size(24.dp) // Larger icon for better visibility
            )

            Spacer(Modifier.width(4.dp))

            // Label (only shown when active)
            if (isActive) {
                Text(
                    text = label,
                    color = activeColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}