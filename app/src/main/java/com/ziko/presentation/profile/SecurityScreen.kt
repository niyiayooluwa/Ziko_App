package com.ziko.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.core.util.UpdateSystemBarsColors
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pressed

/**
 * Composable screen that allows a user to securely change their password.
 *
 * This screen includes:
 * - Fields for entering the old password, new password, and confirmation.
 * - Password visibility toggles for all fields.
 * - Validation before submitting:
 *   - Old and new passwords must differ.
 *   - New and confirm passwords must match.
 *   - Minimum password length of 6 characters.
 * - Toasts to show the result of the password change.
 * - System bar colors are set to white for visual consistency.
 *
 * @param navController Used for navigating back to the previous screen.
 * @param userViewModel ViewModel providing access to user data and password change logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(navController: NavController, userViewModel: UserViewModel) {
    // Collect password change result from ViewModel state
    val changeResult by userViewModel.passwordChangeResult.collectAsState()

    val context = LocalContext.current

    // Local state for password fields
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Toggles for showing/hiding password values
    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Updates system UI bar colors for visual consistency
    UpdateSystemBarsColors(
        topColor = Color.White,
        bottomColor = Color.White
    )

    // Show result toast when password change is attempted
    LaunchedEffect(changeResult) {
        changeResult?.let {
            if (it.isSuccess) {
                Toast.makeText(context, "Password changed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error: ${it.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
            // Optional: clear the result if needed
            // userViewModel.passwordChangeResult.value = null
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 48.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFe5e5e5), RoundedCornerShape(12.dp))
                    .padding(24.dp)
            ) {
                // --- Old Password ---
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Old Password", fontSize = 15.sp, color = Color(0xFF363B44))
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        placeholder = { Text("••••••••") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        visualTransformation = if (oldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                                Icon(
                                    imageVector = if (oldPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (oldPasswordVisible) "Hide password" else "Show password",
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
                }

                // --- New Password ---
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("New Password", fontSize = 15.sp, color = Color(0xFF363B44))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        placeholder = { Text("••••••••") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                                Icon(
                                    imageVector = if (newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (newPasswordVisible) "Hide password" else "Show password",
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
                }

                // --- Confirm Password ---
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirm Password", fontSize = 15.sp, color = Color(0xFF363B44))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("••••••••") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.DarkGray),
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
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
                }

                // --- Submit Button ---
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFFF0eDff))
                        .neumorphic(
                            neuShape = Pressed.Rounded(radius = 4.dp),
                            lightShadowColor = Color.White,
                            darkShadowColor = Color(0xFFCACCD1).copy(alpha = 0.5f),
                            strokeWidth = 4.dp,
                            elevation = 4.dp
                        )
                        .clickable {
                            when {
                                oldPassword == newPassword -> {
                                    Toast.makeText(context, "New password must be different from the old one.", Toast.LENGTH_SHORT).show()
                                }
                                newPassword != confirmPassword -> {
                                    Toast.makeText(context, "New password and confirm password do not match.", Toast.LENGTH_SHORT).show()
                                }
                                newPassword.length < 6 -> {
                                    Toast.makeText(context, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    userViewModel.changeUserPassword(oldPassword, newPassword)
                                }
                            }
                        }
                ) {
                    Text(
                        text = "Change Password",
                        color = Color(0xFF5b7bfe),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}
