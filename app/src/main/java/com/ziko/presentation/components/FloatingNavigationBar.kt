package com.ziko.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

/**
 * A floating bottom navigation bar for the main screen with two tabs: Lessons and Assessment.
 *
 * This component is visible on primary screens and adapts icon states based on the current route.
 *
 * @param navController Navigation controller for performing navigation actions.
 * @param currentRoute The current screen route to highlight the active tab.
 */
@Composable
fun FloatingNavBar(navController: NavController, currentRoute: String) {
    Surface(
        modifier = Modifier
            .animateContentSize()
            .height(80.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(40.dp), spotColor = Color(0xFFE5E5E5)),
        shape = RoundedCornerShape(40.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.height(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isLessonsActive = currentRoute == Screen.Home.route
            NavItem(
                activeIcon = R.drawable.book,
                inactiveIcon = R.drawable.book_1,
                label = "Lessons",
                isActive = isLessonsActive,
                onClick = {
                    if (!isLessonsActive) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )

            val isAssessmentActive = currentRoute == Screen.Assessment.route
            NavItem(
                activeIcon = R.drawable.task_1,
                inactiveIcon = R.drawable.task,
                label = "Assessment",
                isActive = isAssessmentActive,
                onClick = {
                    if (!isAssessmentActive) {
                        navController.navigate(Screen.Assessment.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

/**
 * A single item in the floating navigation bar.
 *
 * Shows an icon and label when active; only the icon when inactive.
 * Entire row is clickable and uses transparent background for seamless layout.
 *
 * @param activeIcon Drawable resource ID for the active icon.
 * @param inactiveIcon Drawable resource ID for the inactive icon.
 * @param label Text label to show when active.
 * @param isActive Whether this tab is currently selected.
 * @param activeColor Optional color used when the tab is active.
 * @param onClick Callback for when this nav item is tapped.
 */
@Composable
fun NavItem(
    activeIcon: Int,
    inactiveIcon: Int,
    label: String,
    isActive: Boolean,
    activeColor: Color = Color(0xFF5E5AEC),
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = if (isActive) activeIcon else inactiveIcon),
                contentDescription = label,
                tint = if (isActive) activeColor else Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(4.dp))

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
