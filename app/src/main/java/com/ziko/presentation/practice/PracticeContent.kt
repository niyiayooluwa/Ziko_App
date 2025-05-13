package com.ziko.presentation.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.R
import com.ziko.presentation.ProgressTopAppBar
import com.ziko.util.AudioButtonWithLabel
import com.ziko.util.Size
import com.ziko.util.SpeechButton

@Preview
@Composable
fun PracticeContent() {
    Scaffold (
        topBar = {
            ProgressTopAppBar(
                progress = TODO(),
                currentScreen = TODO(),
                totalScreens = TODO(),
                onCancel = TODO(),
                onNavigateBack = TODO(),
                isFirstScreen = TODO()
            )
        }
    ){ paddingValues ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ){
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Repeat",
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF080E1E)
            )

            Spacer(modifier = Modifier.height(24.dp))

            AudioButtonWithLabel(
                text = "bit - beat",
                audioResId = R.raw.cut_cart,
                size = Size.BIG
            )
            
            SpeechButton(
                modifier = TODO(),
                onSpeechResult = TODO()
            )
        }
    }
}