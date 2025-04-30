package com.ziko.presentation.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.navigation.Screen
import com.ziko.presentation.CustomBiggerTopAppBar

@Composable
fun SignUpScreenTwo(
    navController: NavController,
    viewModel: SignUpViewModel
) { // Receive ViewModel
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val signUpState by viewModel.signUpState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomBiggerTopAppBar(
                title = "Sign Up",
                onNavigationClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Create Password",
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
                // Password
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Password",
                        fontSize = 15.sp,
                        color = Color(0xFF363B44)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            viewModel.updatePassword(it)
                        },
                        placeholder = { Text("••••••••") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            val description = if (passwordVisible) "Hide password" else "Show password"
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = description)
                            }
                        },
                        isError = viewModel.passwordMismatchError.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Confirm Password
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Confirm Password",
                        fontSize = 15.sp,
                        color = Color(0xFF363B44)
                    )
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            viewModel.updateConfirmPassword(it)
                        },
                        placeholder = { Text("••••••••") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            val description = if (confirmPasswordVisible) "Hide password" else "Show password"
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(imageVector = image, contentDescription = description)
                            }
                        },
                        isError = viewModel.passwordMismatchError.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                    if (viewModel.passwordMismatchError.value) {
                        Text(
                            text = "Passwords do not match",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.signup(
                            onSuccess = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onError = { /* Error handling via SignUpState */ }
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5B7BFE)
                    ),
                    enabled = password.isNotBlank() && confirmPassword.isNotBlank()
                ) {
                    if (signUpState is SignUpState.Loading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text(text = "Sign Up", fontSize = 20.sp)
                    }
                }

                if (signUpState is SignUpState.Error) {
                    Text(
                        text = (signUpState as SignUpState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}