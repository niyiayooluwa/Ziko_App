package com.ziko.presentation.auth.login

import com.ziko.presentation.components.LineUI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.navigation.Screen
import com.ziko.presentation.auth.UserViewModel
import com.ziko.presentation.components.CustomBiggerTopAppBar

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()

    val user by userViewModel.user.collectAsState()
    val loginState = loginViewModel.loginState.value

    val context = LocalContext.current

    // Centralize navigation & fetch logic here
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            userViewModel.fetchUser()
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomBiggerTopAppBar(
                title = "Login",
                onNavigationClick = { navController.navigate(Screen.Onboarding.route) }
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
                text = "Welcome Back",
                fontWeight = FontWeight.Medium,
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
                // Email
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
                        value = loginViewModel.email,
                        onValueChange = { loginViewModel.onEmailChange(it) },
                        placeholder = { Text("username@domain.com") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        isError = loginState is LoginState.Error,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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
                        value = loginViewModel.password,
                        onValueChange = { loginViewModel.onPasswordChange(it) },
                        placeholder = { Text("••••••••") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        visualTransformation = if (loginViewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        isError = loginState is LoginState.Error,
                        trailingIcon = {
                            val image = if (loginViewModel.passwordVisible)
                                Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            val description = if (loginViewModel.passwordVisible)
                                "Hide password" else "Show password"

                            IconButton(onClick = { loginViewModel.onPasswordVisibilityToggle() }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = description,
                                    tint = Color(0xFF080E1E),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F3F3),
                            unfocusedContainerColor = Color(0xFFF2F3F3),
                            focusedBorderColor = Color(0x33080E1E),
                            unfocusedBorderColor = Color.Transparent
                        )
                    )

                    Text(
                        text = "Forgot Password",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFD6185D),
                        modifier = Modifier.clickable {
                            // TODO: Add forgot password navigation/logic
                        }
                    )
                }

                if (loginState is LoginState.Error) {
                    Text(
                        text = loginState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { loginViewModel.login() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B7BFE)),
                    enabled = loginViewModel.email.isNotBlank() && loginViewModel.password.isNotBlank()
                ) {
                    if (loginState is LoginState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(text = "Log In", fontSize = 20.sp, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    LineUI(true, 150.dp)
                    Text("Or", fontSize = 15.sp)
                    LineUI(true, 150.dp)
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF2f3f3))
                            .size(width = 164.dp, height = 50.dp)
                            .padding(vertical = 12.dp, horizontal = 63.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.google),
                            contentDescription = "Sign in with google"
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF2f3f3))
                            .size(width = 164.dp, height = 50.dp)
                            .padding(vertical = 12.dp, horizontal = 63.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = "Sign in with facebook"
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Not a member?", fontSize = 17.sp, color = Color(0xFF656872))
                    Text(
                        text = " Signup",
                        fontSize = 17.sp,
                        color = Color(0xFF5B7BFE),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.SignOne.route)
                        }
                    )
                }
            }
        }
    }
}
