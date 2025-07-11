package com.ziko.presentation.profile

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ziko.R
import com.ziko.navigation.Screen
import com.ziko.core.util.UpdateSystemBarsColors
import kotlinx.coroutines.launch
import me.nikhilchaudhari.library.neumorphic

/**
 * Profile screen composable displaying the user's details such as name and profile picture,
 * and providing options to log out or edit profile information using bottom sheets.
 *
 * ## Features
 * - Displays user name and profile picture.
 * - Allows editing first and last name.
 * - Allows updating profile picture via gallery or camera.
 * - Supports logout confirmation.
 *
 * @param navController The [NavHostController] used to navigate between screens.
 * @param userViewModel The [UserViewModel] providing user state and update/logout functions.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val user by userViewModel.user.collectAsState()
    val updateResult by userViewModel.userUpdateResult.collectAsState()

    var showSheet by remember{ mutableStateOf(false) }
    var showEditProfileSheet by remember { mutableStateOf(false) }

    var firstName by remember { mutableStateOf("")}
    var lastName by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    UpdateSystemBarsColors(
        topColor = Color.White,
        bottomColor = Color.White
    )

    val profilePicUri by userViewModel.profilePicUri.collectAsState()
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Camera image result
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            userViewModel.updateProfilePic(tempCameraUri.toString())
        }
    }

    // Gallery image result
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            userViewModel.updateProfilePic(it.toString())
        }
    }

    // Request camera permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            tempCameraUri = createImageUri(context)
            tempCameraUri?.let { cameraLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(updateResult) {
        updateResult?.let {
            if (it.isSuccess) {
                Toast.makeText(context, "Name updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed: ${it.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
            // Reset after showing
            //userViewModel.userUpdateResult.value = null
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
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
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 48.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                //This is where the image is supposed to be
                Box (
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF5b7bfe).copy(alpha = 0.2f))
                        .border(1.dp, Color(0xFF5b7bfe), CircleShape)
                ) {
                    if (profilePicUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = profilePicUri),
                            contentDescription = null,
                            contentScale = (ContentScale.Crop),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                    else {
                        Text(
                            text = user?.first_name?.first().toString(),
                            fontSize = 13.sp,
                            color = Color(0xFF5b7bfe),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                
                Spacer (modifier = Modifier.height(16.dp))

                //Name
                Text(
                    text = "${user?.first_name} ${user?.last_name}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Spacer (modifier = Modifier.height(4.dp))

                //email
                Text(
                    text = "${user?.email}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer (modifier = Modifier.height(24.dp))

                //edit profile button
                Button(
                    onClick = { showEditProfileSheet = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF5b7bfe)
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .height(40.dp),
                    border = null
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Edit",
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Edit Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer (modifier = Modifier.height(44.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .clickable{navController.navigate(Screen.SecurityScreen.route)}
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFe5e5e5), RoundedCornerShape(12.dp))
                        .padding(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF5b7bfe).copy(alpha = 0.2f))
                        ) {
                            Icon(
                                painterResource(R.drawable.lock),
                                contentDescription = null,
                                tint = Color(0xFF5b7bfe),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Text(
                            text = "Security",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            contentDescription = "Logout",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Button(
                onClick = {showSheet = true},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF5b7bfe)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                border = BorderStroke(1.dp, Color(0xFF5b7bfe))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Logout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        // Show logout confirmation sheet
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                containerColor = Color.White,
            ) {
                // Layout for Logout Bottom Sheet
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Title
                    Text(
                        text = "Logout",
                        fontSize = 22.sp,
                        color = Color(0xFFD6185D),
                        fontWeight = FontWeight.Medium
                    )

                    HorizontalDivider(color = Color(0xFFe5e5e5))

                    // Confirmation message
                    Text(
                        text = "Are you sure you want to logout?",
                        fontSize = 17.sp,
                        color = Color(0xFF080e1e),
                        fontWeight = FontWeight.Medium
                    )

                    // Buttons Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Cancel Button
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .clickable { showSheet = false },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color(0xFF5b7bfe),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500
                            )
                        }

                        // Confirm Logout Button
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFFD6185D))
                                .clickable {
                                    // Triggers logout and navigates to Login screen
                                    userViewModel.logout {
                                        navController.navigate(Screen.Login.route)
                                    }
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Yes, Logout",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }
                }
            }
        }

        // Show edit profile sheet
        if (showEditProfileSheet) {
            ModalBottomSheet(
                onDismissRequest = { showEditProfileSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
            ) {
                // Layout for Edit Profile Bottom Sheet
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Sheet title
                    Text(
                        text = "Edit Profile",
                        fontSize = 22.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )

                    HorizontalDivider(color = Color(0xFFe5e5e5))

                    // Profile Picture Picker
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF5b7bfe).copy(alpha = 0.2f))
                            .border(1.dp, Color(0xFF5b7bfe), CircleShape)
                            .clickable { galleryLauncher.launch("image/*") }
                    ) {
                        if (profilePicUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = profilePicUri),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            // Fallback to first letter of first name
                            Text(
                                text = user?.first_name?.first().toString(),
                                fontSize = 13.sp,
                                color = Color(0xFF5b7bfe),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    // First Name Field
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
                            placeholder = { Text(user?.first_name ?: "") },
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

                    // Last Name Field
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
                            placeholder = { Text(user?.last_name ?: "") },
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

                    // Action Buttons: Cancel & Save
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Cancel Button
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFFF0eDff))
                                .neumorphic( /* Pressed Neumorphic style */ )
                                .clickable { showEditProfileSheet = false },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color(0xFF5b7bfe),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500
                            )
                        }

                        // Save Button
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFF5b7bfe))
                                .clickable {
                                    // Launch update in coroutine scope
                                    scope.launch {
                                        userViewModel.updateUserName(firstName, lastName)
                                        showEditProfileSheet = false
                                    }
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Save",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }
                }
            }
        }

    }
}

/**
 * Creates a new [Uri] to store an image in external media storage using the current timestamp.
 *
 * @param context The context used to access [ContentResolver].
 * @return A [Uri] pointing to the created image location, or null if creation failed.
 */
fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "profile_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )
}
