package com.ziko.presentation.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ziko.core.util.UpdateSystemBarsColors
import com.ziko.navigation.Screen
import com.ziko.presentation.auth.login.LoginState
import com.ziko.presentation.auth.login.LoginViewModel
import com.ziko.presentation.components.CustomBiggerTopAppBar
import com.ziko.presentation.profile.UserViewModel

/**
 * Composable screen for the second step of user sign-up,
 * where the user creates and confirms their password.
 *
 * @param navController Used to navigate between screens.
 * @param viewModel ViewModel holding sign-up state and logic.
 * @param userViewModel ViewModel used to fetch user data after auto-login.
 */
@Composable
fun SignUpScreenTwo(
    navController: NavController,
    viewModel: SignUpViewModel,
    userViewModel: UserViewModel
) {
    // LoginViewModel is used to auto-login after successful sign-up
    val loginViewModel: LoginViewModel = hiltViewModel()

    // Local password state
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Visibility toggles
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Collect sign-up state from ViewModel
    val signUpState = viewModel.signUpState.value
    val scrollState = rememberScrollState()

    // Local error state to show relevant messages
    var localErrorMessage by remember { mutableStateOf<String?>(null) }

    // Auto-login after successful sign-up
    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                loginViewModel.apply {
                    onEmailChange(viewModel.signUpData.value.email)
                    onPasswordChange(viewModel.signUpData.value.password)
                    login()
                }
            }
            is SignUpState.Error -> {
                localErrorMessage = signUpState.message
            }
            else -> Unit
        }
    }

    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    // Collect login state from ViewModel
    val loginState by loginViewModel.loginState

    // Navigate to home if login succeeds, otherwise show error
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                userViewModel.fetchUser()
                navController.navigate(Screen.Home.route)
            }
            is LoginState.Error -> {
                localErrorMessage = (loginState as LoginState.Error).message
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            CustomBiggerTopAppBar(
                title = "Sign Up",
                onNavigationClick = { navController.popBackStack() }
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
                // ---------------- Password Field ----------------
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
                        textStyle = TextStyle(color = Color.DarkGray),
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

                // ---------------- Confirm Password Field ----------------
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
                        textStyle = TextStyle(color = Color.DarkGray),
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

                // ---------------- Submit Button ----------------
                Button(
                    onClick = {
                        viewModel.signup(
                            onSuccess = {},
                            onError = {}
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5B7BFE)
                    ),
                    enabled = password.isNotBlank() && confirmPassword.isNotBlank()
                ) {
                    if (signUpState is SignUpState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp))
                    } else {
                        Text(text = "Sign Up", fontSize = 20.sp, color = Color.White)
                    }
                }

                // ---------------- Error Message Display ----------------
                if (signUpState is SignUpState.Error) {
                    Text(
                        text = signUpState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

