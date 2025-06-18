package com.ziko.presentation.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.core.util.UpdateSystemBarsColors
import com.ziko.navigation.Screen
import com.ziko.presentation.components.CustomBiggerTopAppBar

/**
 * First screen of the sign-up process. Collects user's first name, last name, and email.
 *
 * This screen validates the email and enables progression only when all input fields are filled.
 * It relies on [SignUpViewModel] for email validation logic.
 *
 * ## State:
 * - [firstName], [lastName], [email] are hoisted inside the screen using `remember`.
 * - [emailError] is observed from the ViewModel and shown below the email input field.
 *
 * ## Navigation:
 * - Navigates to [Screen.SignTwo] when inputs are valid and the button is pressed.
 *
 * @param navController Used to handle navigation between screens.
 * @param viewModel ViewModel used for validating inputs and storing temporary form data.
 */
@Composable
fun SignUpScreenOne(
    navController: NavController,
    viewModel: SignUpViewModel
) {
    // Local state for user input
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Error message for email validation (from ViewModel)
    val emailError = viewModel.emailError.value

    // Scroll state for screen scrollability
    val scrollState = rememberScrollState()

    // System bar colors (purple top, white bottom)
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    Scaffold(
        topBar = {
            CustomBiggerTopAppBar(
                title = "Sign Up",
                onNavigationClick = { navController.popBackStack() } // Navigate back
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Header text
            Text(
                text = "Create Account",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 22.sp,
                color = Color(0xFF080E1E)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // FIRST NAME INPUT
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "First Name",
                        fontSize = 15.sp,
                        color = Color(0xFF363B44)
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = { Text("John") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // LAST NAME INPUT
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Last Name",
                        fontSize = 15.sp,
                        color = Color(0xFF363B44)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = { Text("Doe") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // EMAIL INPUT
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Email",
                        fontSize = 15.sp,
                        color = Color(0xFF363B44)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            viewModel.updateEmail(it) // Triggers validation
                        },
                        placeholder = { Text("username@domain.com") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.DarkGray),
                        isError = emailError != null,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                    if (emailError != null) {
                        Text(
                            text = emailError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // NEXT BUTTON
                Button(
                    onClick = {
                        // Save form data to ViewModel
                        viewModel.updateFirstName(firstName)
                        viewModel.updateLastName(lastName)

                        // Proceed only if email is valid
                        if (viewModel.isEmailValid()) {
                            navController.navigate(Screen.SignTwo.route)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5B7BFE)
                    ),
                    enabled = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
                ) {
                    Text(text = "Next", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }
}
