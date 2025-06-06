package com.ziko.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.navigation.Screen


@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String
) {
    val items = listOf(
        BottomNavItem.Lesson,
        BottomNavItem.Assessment
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Floating above bottom
            .wrapContentHeight()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .wrapContentWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(40.dp) // Half of height for full roundness
                )
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(40.dp),
                    clip = false
                )
                .padding(horizontal = 24.dp), // Space inside container
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color(0xFF5B7BFE) else Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )
                    if (isSelected) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = item.label,
                            color = Color(0xFF5B7BFE),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}


sealed class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    data object Lesson : BottomNavItem("Lesson", Icons.AutoMirrored.Filled.MenuBook, Screen.Home.route)
    data object Assessment : BottomNavItem("Assessment", Icons.Filled.Assignment, Screen.Assessment.route)
}
