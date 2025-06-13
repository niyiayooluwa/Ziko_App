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
import com.ziko.util.UpdateSystemBarsColors

@Composable
fun OnboardingScreen (
    navController: NavController
) {
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Box {
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
                contentScale = ContentScale.Crop
            )

            Image (
                painter = painterResource(R.drawable.onboarding_image_2),
                contentDescription = "fore image",
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column (
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (
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
                    color = Color(0x99080E1E)
                )
            }

            Button(
                onClick = {navController.navigate(Screen.Login.route)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Add some horizontal padding around the button
                shape = RoundedCornerShape(percent = 50), // Make the ends fully rounded
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