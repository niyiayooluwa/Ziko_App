package com.ziko.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.navigation.Screen
import com.ziko.core.util.UpdateSystemBarsColors

/**
 * OnboardingScreen is the first visual interaction screen for new users.
 * It features:
 * - A prominent onboarding image background
 * - A foreground logo or visual element
 * - A concise title and description
 * - A "Start Learning" CTA button that navigates to the Login screen
 *
 * @param navController The NavController used for navigation to the Login screen
 */
@Composable
fun OnboardingScreen(navController: NavController) {

    // Set system bar colors to visually align with the onboarding screen's theme
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // White background ensures visual separation below the image
    ) {

        // SECTION 1: Image container (top 70% of the screen)
        Box {
            // Background image fills width and 70% of height, with rounded bottom corners
            Image(
                painter = painterResource(R.drawable.onboarding_image_1),
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.7f)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    ),
                contentScale = ContentScale.Crop // Ensures the image fills space proportionally
            )

            // Foreground image (e.g., mascot or app icon), centered within the box
            Image(
                painter = painterResource(R.drawable.onboarding_image_2),
                contentDescription = "Foreground image",
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.Center)
            )
        }

        // Spacer creates vertical breathing room between image and text section
        Spacer(modifier = Modifier.height(32.dp))

        // SECTION 2: Text + Button
        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            // Text block (title + subtitle)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Confidence in your English",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF080E1E)
                )

                Text(
                    text = "Your guide to mastering English Phonetics\n& Phonology",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color(0x99080E1E) // Slightly transparent dark text for subtext
                )
            }

            // CTA Button: "Start Learning" → navigates to Login screen
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Adds padding around button edges
                shape = RoundedCornerShape(percent = 50), // Makes the button fully pill-shaped
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5B7BFE),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Start Learning",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
