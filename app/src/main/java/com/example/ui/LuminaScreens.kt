package com.example.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.ChatMessageEntity
import com.example.data.StudyGroupEntity
import com.example.data.UnlockedBadgeEntity
import com.example.data.SearchHistoryEntity
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.horizontalScroll
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.content.Intent
import com.example.ui.theme.*
import kotlinx.coroutines.launch

// Dynamic image resource resolver to prevent build failure if resource names change
@Composable
fun getHeroPainter(): androidx.compose.ui.graphics.painter.Painter {
    val context = LocalContext.current
    val id = context.resources.getIdentifier("img_bible_study_hero_1783980722301", "drawable", context.packageName)
    return if (id != 0) {
        painterResource(id = id)
    } else {
        // Fallback placeholder painter or dummy
        painterResource(id = android.R.drawable.ic_menu_gallery)
    }
}

@Composable
fun WelcomeScreen(viewModel: LuminaViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
    ) {
        // Background Decorative Ambient Circle
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 40.dp)
                .size(240.dp)
                .background(Brush.radialGradient(listOf(GoldPrimary.copy(alpha = 0.08f), Color.Transparent)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = "GLOBAL COMMUNITY",
                    color = GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "esposizione",
                    color = Color.White,
                    fontSize = 38.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Study Scripture. Connect Globally. Grow Together.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            // Interactive Mock-up Banner or Image Card
            Card(
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    val heroId = LocalContext.current.resources.getIdentifier(
                        "img_bible_study_hero_1783980722301", "drawable", LocalContext.current.packageName
                    )
                    if (heroId != 0) {
                        Image(
                            painter = painterResource(id = heroId),
                            contentDescription = "Scripture illustration",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(listOf(SurfaceDark, DeepBlack)))
                        )
                    }
                    // Overlay tint
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "Now Study Acts 16",
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Synchronous chat, scripture read, host tools.",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.navigateTo(Screen.Login) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("signin_nav_button")
                ) {
                    Text("Sign In to Community", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.Register) },
                    border = BorderStroke(1.dp, GoldPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("register_nav_button")
                ) {
                    Text("Create New Account", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Lumina study provides UGC moderation compliance.",
                    color = TextMuted,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LuminaViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val authError by viewModel.authError.collectAsStateWithLifecycle()
    val showRecoverySent by viewModel.showRecoverySent.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign In", color = Color.White, fontFamily = FontFamily.Serif) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.Welcome) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlack)
            )
        },
        containerColor = DeepBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Welcome back to your Bible circle",
                    color = TextSecondary,
                    fontSize = 15.sp
                )

                if (authError != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0x22EF5350)),
                        border = BorderStroke(1.dp, Color(0xFFEF5350)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = authError ?: "",
                            color = Color(0xFFFF8A80),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedLabelColor = GoldPrimary,
                        unfocusedLabelColor = TextMuted,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedLabelColor = GoldPrimary,
                        unfocusedLabelColor = TextMuted,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (showPassword) "Hide password" else "Show password",
                                tint = GoldPrimary
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = GoldPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable { viewModel.recoverPassword(email) }
                            .padding(4.dp)
                    )
                }

                AnimatedVisibility(visible = showRecoverySent) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(
                                color = GoldPrimary,
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = "Simulating mail server OAuth recovery dispatch...",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Button(
                    onClick = { viewModel.login(email, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("login_button")
                ) {
                    Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("New to Lumina? ", color = TextMuted, fontSize = 14.sp)
                    Text(
                        text = "Create Account",
                        color = GoldPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { viewModel.navigateTo(Screen.Register) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: LuminaViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val authError by viewModel.authError.collectAsStateWithLifecycle()
    val showVerificationSent by viewModel.showVerificationSent.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Account", color = Color.White, fontFamily = FontFamily.Serif) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.Welcome) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlack)
            )
        },
        containerColor = DeepBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Join global studies and live scripture rooms",
                    color = TextSecondary,
                    fontSize = 15.sp
                )

                if (authError != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0x22EF5350)),
                        border = BorderStroke(1.dp, Color(0xFFEF5350)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = authError ?: "",
                            color = Color(0xFFFF8A80),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text("Display Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedLabelColor = GoldPrimary,
                        unfocusedLabelColor = TextMuted,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedLabelColor = GoldPrimary,
                        unfocusedLabelColor = TextMuted,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password (6+ chars)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedLabelColor = GoldPrimary,
                        unfocusedLabelColor = TextMuted,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (showPassword) "Hide password" else "Show password",
                                tint = GoldPrimary
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location (e.g. London, UK)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedLabelColor = GoldPrimary,
                        unfocusedLabelColor = TextMuted,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                AnimatedVisibility(visible = showVerificationSent) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Verification dispatch: Simulated OAuth/SMTP registration success.",
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Verification link and JWT tokens have been provisioned locally.",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Button(
                    onClick = { viewModel.register(email, password, displayName, location) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("register_button")
                ) {
                    Text("Register & Verify", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Already registered? ", color = TextMuted, fontSize = 14.sp)
                    Text(
                        text = "Sign In",
                        color = GoldPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { viewModel.navigateTo(Screen.Login) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: LuminaViewModel, onMenuClick: (() -> Unit)? = null) {
    val groups by viewModel.allGroups.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val isPro by viewModel.isProUser.collectAsStateWithLifecycle()

    var searchGroupQuery by remember { mutableStateOf("") }
    val liveGroup = groups.firstOrNull { it.isLive }
    val scheduledGroups = groups.filter { !it.isLive }.filter {
        searchGroupQuery.isEmpty() ||
        it.title.contains(searchGroupQuery, ignoreCase = true) ||
        it.topic.contains(searchGroupQuery, ignoreCase = true) ||
        it.hostName.contains(searchGroupQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = DeepBlack,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.navigateTo(Screen.CreateCircle) },
                containerColor = GoldPrimary,
                contentColor = DeepBlack,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("setup_group_fab")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.GroupAdd, contentDescription = "Setup Study Group")
                    Text("Setup Study Group", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
        ) {
        // App top header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onMenuClick != null) {
                    IconButton(
                        onClick = onMenuClick,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open Navigation Drawer",
                            tint = GoldPrimary
                        )
                    }
                }
                Column {
                    Text(
                        text = "GLOBAL COMMUNITY",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "esposizione",
                        fontSize = 22.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }
            }

            // Dark Mode Toggle and user profile avatar indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val currentTheme by viewModel.currentStudyTheme.collectAsStateWithLifecycle()
                val isDark = currentTheme != com.example.ui.StudyTheme.IVORY_LIGHT
                IconButton(
                    onClick = {
                        if (isDark) {
                            viewModel.setStudyTheme(com.example.ui.StudyTheme.IVORY_LIGHT)
                        } else {
                            viewModel.setStudyTheme(com.example.ui.StudyTheme.COSMIC_DARK)
                        }
                    },
                    modifier = Modifier.testTag("dark_mode_toggle_header")
                ) {
                    Icon(
                        imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Dark Mode",
                        tint = GoldPrimary
                    )
                }

                user?.let {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(it.avatarColor))
                            .clickable { viewModel.navigateTo(Screen.Profile) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.displayName.take(2).uppercase(),
                            color = DeepBlack,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Live Study Room Section (Hero Card matched Sophisticated Dark aesthetic)
            item {
                liveGroup?.let { group ->
                    Card(
                        shape = RoundedCornerShape(32.dp),
                        border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(290.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Try loading our generated image
                            val context = LocalContext.current
                            val heroId = context.resources.getIdentifier(
                                "img_bible_study_hero_1783980722301", "drawable", context.packageName
                            )
                            if (heroId != 0) {
                                Image(
                                    painter = painterResource(id = heroId),
                                    contentDescription = "Bible Study Hero",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                // Fallback ambient gradient
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(Color(0xFF1E1C18), Color(0xFF080808))
                                            )
                                        )
                                )
                            }

                            // Dark overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Black.copy(alpha = 0.4f), Color.Black.copy(alpha = 0.9f))
                                        )
                                    )
                            )

                            // Top indicators
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(Color(0x22E53935), RoundedCornerShape(50))
                                        .border(1.dp, Color(0x66E53935), RoundedCornerShape(50))
                                        .padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(Color(0xFFE53935), CircleShape)
                                    )
                                    Text(
                                        text = "LIVE ROOM",
                                        color = Color(0xFFEF9A9A),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy((-6).dp)
                                ) {
                                    val colors = listOf(Color(0xFF8D6E63), Color(0xFF5C6BC0), GoldPrimary)
                                    colors.forEachIndexed { idx, c ->
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .border(2.dp, CardDark, CircleShape)
                                                .background(c, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (idx == 2) {
                                                Text(
                                                    "+${group.participantCount}",
                                                    fontSize = 8.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Bottom content
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = group.title,
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = group.topic,
                                    color = TextSecondary,
                                    fontSize = 13.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = { viewModel.selectGroupAndJoin(group) },
                                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("join_live_study_button")
                                ) {
                                    Icon(imageVector = Icons.Filled.Stadium, contentDescription = null, tint = DeepBlack)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Join Live Study Session", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Quick Stats & Activity Grid
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Left stat
                    Card(
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .clickable { viewModel.navigateTo(Screen.Settings) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = GoldPrimary)
                            Column {
                                Text("Acts 16", color = Color.White, fontFamily = FontFamily.Serif, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text("LAST READ", color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            }
                        }
                    }

                    // Right stat
                    Card(
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = GoldPrimary)
                            Column {
                                Text("4 Circles", color = Color.White, fontFamily = FontFamily.Serif, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text("ACTIVE CIRCLES", color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            }
                        }
                    }
                }
            }

            // Study Group Search Bar
            item {
                OutlinedTextField(
                    value = searchGroupQuery,
                    onValueChange = { searchGroupQuery = it },
                    placeholder = { Text("Search circles, topics, or hosts...", color = TextMuted, fontSize = 13.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = GoldPrimary, modifier = Modifier.size(18.dp)) },
                    trailingIcon = if (searchGroupQuery.isNotEmpty()) {
                        {
                            IconButton(onClick = { searchGroupQuery = "" }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted, modifier = Modifier.size(16.dp))
                            }
                        }
                    } else null,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedContainerColor = SurfaceDark,
                        unfocusedContainerColor = SurfaceDark,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("study_group_search_input")
                )
            }

            // AdMob Sponsor Banner for non-Pro members
            item {
                if (!isPro) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.15f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("admob_banner_card")
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(GoldPrimary.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("SPONSOR AD", color = GoldPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Support Lumina Development", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("Publisher: pub-5512836784582639 | DIRECT", color = TextMuted, fontSize = 10.sp)
                            }
                            Icon(imageVector = Icons.Default.Launch, contentDescription = "Sponsor", tint = TextMuted, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // Upcoming Schedule Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SCHEDULED FOR TOMORROW",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                        Text(
                            text = "See All",
                            color = GoldPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { viewModel.navigateTo(Screen.CreateCircle) }
                        )
                    }

                    scheduledGroups.forEach { sGroup ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0x0AFFFFFF), RoundedCornerShape(20.dp))
                                .border(1.dp, Color(0x05FFFFFF), RoundedCornerShape(20.dp))
                                .clickable { viewModel.selectGroupAndJoin(sGroup) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Date tile
                            Column(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(SurfaceDark, RoundedCornerShape(12.dp))
                                    .border(1.dp, BorderDark, RoundedCornerShape(12.dp)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "JUL", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                                Text(text = "14", fontSize = 16.sp, color = GoldPrimary, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                            }

                            // Group Details
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = sGroup.title,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${sGroup.hostName} • ${sGroup.scheduleText}",
                                    color = TextMuted,
                                    fontSize = 12.sp
                                )
                            }

                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                        }
                    }
                }
            }
        }
    }
}
        }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingRoomScreen(viewModel: LuminaViewModel) {
    val group by viewModel.selectedGroup.collectAsStateWithLifecycle()
    val messages by viewModel.activeMessages.collectAsStateWithLifecycle()
    var roomTabMode by remember { mutableStateOf("Chat") } // "Chat" or "Dashboard"
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val blockedUsers by viewModel.blockedUsers.collectAsStateWithLifecycle()
    val isPro by viewModel.isProUser.collectAsStateWithLifecycle()
    val activeTyper by viewModel.activeTyper.collectAsStateWithLifecycle()

    var inputMsg by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var showProDialog by remember { mutableStateOf(false) }
    var isVoiceActive by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var showVoiceOverlay by remember { mutableStateOf(false) }
    val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showVoiceOverlay = true
        } else {
            viewModel.showToast("Microphone permission is required for voice-to-text chat.")
        }
    }

    // Host checking helper
    val isHost = user?.displayName == group?.hostName

    // Scroll chat to bottom when messages list size changes
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    if (showProDialog) {
        ProUpgradeDialog(
            onDismiss = { showProDialog = false },
            onUpgradeSuccess = {
                viewModel.upgradeToPro()
                showProDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = group?.title ?: "Bible Study Circle",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = group?.topic ?: "Discussion Room",
                            color = GoldPrimary,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.Dashboard) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    // Voice Call Icon
                    IconButton(
                        onClick = {
                            if (isPro) {
                                isVoiceActive = !isVoiceActive
                            } else {
                                showProDialog = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isVoiceActive) Icons.Default.Mic else Icons.Default.MicOff,
                            contentDescription = "Voice Circle",
                            tint = if (isVoiceActive) GoldPrimary else Color.White.copy(alpha = 0.6f)
                        )
                    }

                    if (isHost) {
                        IconButton(onClick = { viewModel.hostShareVerse("Acts 16:25 'About midnight Paul and Silas were praying and singing hymns to God...'") }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share Verse", tint = GoldPrimary)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
            )
        },
        containerColor = DeepBlack
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
        ) {
            // Live Presence Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Color.Green, CircleShape)
                    )
                    Text(
                        text = "14 study members connected live",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = "Presence real-time",
                    color = GoldPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // ----------------- Group Invite Link Banner -----------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF141310))
                    .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.25f)))
                    .clickable {
                        val inviteLink = "https://lumina.faith/join-circle?id=${group?.id ?: 1}"
                        clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(inviteLink))
                        viewModel.showToast("Study Circle invite link copied! Share with friends.")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = "Invite Link",
                        tint = GoldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Invite Link: https://lumina.faith/join-circle?id=${group?.id ?: 1}",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "Copy Link",
                    color = GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Interactive Voice active banner
            if (isVoiceActive) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x22C9A063))
                        .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Voice Connected",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Column {
                            Text("PRO Live Voice Active", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(
                                text = if (isMuted) "Muted • Listening to study group" else "Speaking live in study group",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 10.sp
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { isMuted = !isMuted },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isMuted) Color(0xFFD32F2F) else Color(0xFF222222),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(if (isMuted) "Unmute" else "Mute", fontSize = 10.sp)
                        }

                        Button(
                            onClick = { isVoiceActive = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text("Leave", fontSize = 10.sp)
                        }
                    }
                }
            }

            // Dynamic Social Scripture Sync Focus Banner
            val circleReadingState by viewModel.circleReadingState.collectAsStateWithLifecycle()
            val activeFocus = group?.id?.let { circleReadingState[it] }
            if (activeFocus != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF141414))
                        .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = "Scripture Sync",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Column {
                            Text("Active Circle Study Focus", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(
                                text = "Currently reading: $activeFocus",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.navigateTo(Screen.Settings)
                            viewModel.showToast("Opening study focus: $activeFocus")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("Open Scripture", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Sub-tabs switcher inside the room
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F0E0B))
                    .padding(vertical = 4.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Chat" to "Discussion Chat", "Dashboard" to "Group Dashboard").forEach { (mode, label) ->
                    val isSelected = roomTabMode == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.4f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { roomTabMode = mode }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) GoldPrimary else Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (roomTabMode == "Chat") {
                // Pinned Message banner if any
                val pinnedMsg = messages.lastOrNull { it.isPinned }
            if (pinnedMsg != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x11C9A063))
                        .border(BorderStroke(1.dp, Color(0x33C9A063)))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.PushPin, contentDescription = "Pinned Message", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "PINNED BY HOST (${pinnedMsg.senderName})",
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = pinnedMsg.messageText,
                            color = Color.White,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Message Board
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                // Filter messages: hide messages of blocked senders (UGC Moderation!)
                val filteredMessages = messages.filter { msg ->
                    blockedUsers.none { blocked -> blocked.blockedUsername == msg.senderName }
                }

                if (filteredMessages.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Study Room is active. Say hello to begin!",
                                color = TextMuted,
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                items(filteredMessages) { msg ->
                    val isOwn = msg.senderName == user?.displayName
                    val timeFormat = remember { java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault()) }
                    val timeString = remember(msg.timestamp) { timeFormat.format(java.util.Date(msg.timestamp)) }

                    if (msg.isSystem) {
                        // System Message bubble
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0x11FFFFFF), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = msg.messageText,
                                    color = GoldPrimary,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        // Standard chat message bubble
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (isOwn) Alignment.End else Alignment.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = msg.senderName,
                                    color = if (isOwn) GoldPrimary else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = timeString,
                                    color = TextMuted,
                                    fontSize = 9.sp
                                )
                                if (msg.isPinned) {
                                    Icon(imageVector = Icons.Default.PushPin, contentDescription = "Pinned", tint = GoldPrimary, modifier = Modifier.size(10.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (isOwn) Color(0xFF2A2A2A) else Color(0xFF141414),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .border(1.dp, Color(0x11FFFFFF), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column {
                                    Text(
                                        text = msg.messageText,
                                        color = TextSecondary,
                                        fontSize = 14.sp
                                    )

                                    // Moderation quick links inside the bubble! (Fulfill Google Play UGC requirements)
                                    Row(
                                        modifier = Modifier.padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (!isOwn) {
                                            Text(
                                                text = "Block",
                                                color = Color.Red.copy(alpha = 0.6f),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
                                                    .clickable { viewModel.blockUser(msg.senderName) }
                                                    .testTag("block_user_button")
                                            )
                                        }
                                        if (!msg.isReported) {
                                            Text(
                                                text = "Report",
                                                color = TextMuted,
                                                fontSize = 10.sp,
                                                modifier = Modifier
                                                    .clickable { viewModel.reportMessage(msg) }
                                                    .testTag("report_message_button")
                                            )
                                        } else {
                                            Text(text = "Reported ✓", color = Color.Green, fontSize = 9.sp)
                                        }
                                        if (isHost && !msg.isPinned) {
                                            Text(
                                                text = "Pin",
                                                color = GoldPrimary,
                                                fontSize = 10.sp,
                                                modifier = Modifier.clickable { viewModel.hostPinMessage(msg) }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (activeTyper != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(GoldPrimary, CircleShape)
                    )
                    Text(
                        text = "$activeTyper is typing a response...",
                        color = GoldPrimary,
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }

            // Emoticon Quick Bar
            val quickEmoticons = listOf("🙏", "🙌", "📖", "❤️", "✨", "💡", "😇", "🕊️", "😊", "👍")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Quick Reactions:",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 4.dp)
                )
                quickEmoticons.forEach { emo ->
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xFF141414), CircleShape)
                            .clickable {
                                inputMsg = (inputMsg + " " + emo).trim()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emo, fontSize = 14.sp)
                    }
                }
            }

            // Input Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        audioPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF222222), CircleShape)
                        .testTag("mic_button")
                ) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "Voice-to-Text", tint = GoldPrimary, modifier = Modifier.size(18.dp))
                }

                OutlinedTextField(
                    value = inputMsg,
                    onValueChange = { inputMsg = it },
                    placeholder = { Text("Share study thoughts...", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color(0xFF0C0C0C),
                        unfocusedContainerColor = Color(0xFF0C0C0C)
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                IconButton(
                    onClick = {
                        if (inputMsg.isNotBlank()) {
                            viewModel.sendUserMessage(inputMsg)
                            inputMsg = ""
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(GoldPrimary, CircleShape)
                        .testTag("send_message_button")
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = DeepBlack, modifier = Modifier.size(18.dp))
                }
            }
            } else {
                GroupDashboardView(group = group, viewModel = viewModel)
            }
        }
    }

    if (showVoiceOverlay) {
        AlertDialog(
            onDismissRequest = { showVoiceOverlay = false },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            confirmButton = {},
            dismissButton = {},
            containerColor = SurfaceDark,
            tonalElevation = 6.dp,
            text = {
                val context = LocalContext.current
                var speechText by remember { mutableStateOf("") }
                var isListening by remember { mutableStateOf(false) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                // Create recognizer safely
                val recognizer = remember { 
                    try {
                        SpeechRecognizer.createSpeechRecognizer(context)
                    } catch (e: Exception) {
                        null
                    }
                }

                val startListening = {
                    if (recognizer != null) {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                        }
                        try {
                            recognizer.startListening(intent)
                            isListening = true
                            errorMessage = null
                        } catch (e: Exception) {
                            errorMessage = "Speech system not ready: ${e.localizedMessage}"
                            isListening = false
                        }
                    } else {
                        errorMessage = "Speech engine not available on this device"
                    }
                }

                DisposableEffect(Unit) {
                    val listener = object : RecognitionListener {
                        override fun onReadyForSpeech(params: android.os.Bundle?) {
                            isListening = true
                            errorMessage = null
                        }
                        override fun onBeginningOfSpeech() {}
                        override fun onRmsChanged(rmsdB: Float) {}
                        override fun onBufferReceived(buffer: ByteArray?) {}
                        override fun onEndOfSpeech() {
                            isListening = false
                        }
                        override fun onError(error: Int) {
                            val msg = when (error) {
                                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                                SpeechRecognizer.ERROR_CLIENT -> "Client error"
                                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permissions missing"
                                SpeechRecognizer.ERROR_NETWORK -> "Network error"
                                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                                SpeechRecognizer.ERROR_NO_MATCH -> "No speech matching"
                                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Speech system is busy"
                                SpeechRecognizer.ERROR_SERVER -> "Server error"
                                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Speech input timeout"
                                else -> "Microphone is silent"
                            }
                            errorMessage = msg
                            isListening = false
                        }
                        override fun onResults(results: android.os.Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            if (!matches.isNullOrEmpty()) {
                                speechText = matches[0]
                            }
                            isListening = false
                        }
                        override fun onPartialResults(partialResults: android.os.Bundle?) {
                            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            if (!matches.isNullOrEmpty()) {
                                speechText = matches[0]
                            }
                        }
                        override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
                    }
                    recognizer?.setRecognitionListener(listener)
                    
                    // Auto-start listening on launch!
                    startListening()

                    onDispose {
                        try {
                            recognizer?.stopListening()
                            recognizer?.destroy()
                        } catch (e: Exception) {}
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Mic",
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Lumina Voice Assistant",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        IconButton(onClick = { showVoiceOverlay = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }

                    Divider(color = BorderDark)

                    // Wave form animations representation
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(if (isListening) GoldPrimary.copy(alpha = 0.1f) else Color(0xFF1E1E1E), CircleShape)
                            .border(BorderStroke(2.dp, if (isListening) GoldPrimary else BorderDark), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isListening) Icons.Default.Hearing else Icons.Default.Mic,
                            contentDescription = "Mic Status",
                            tint = if (isListening) GoldPrimary else Color.White.copy(alpha = 0.4f),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = if (isListening) "Listening... Speak now" else "Voice System Ready",
                        color = if (isListening) GoldPrimary else TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Text result input field
                    OutlinedTextField(
                        value = speechText,
                        onValueChange = { speechText = it },
                        placeholder = { Text("Spoken words will appear here...", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = BorderDark,
                            focusedContainerColor = DeepBlack,
                            unfocusedContainerColor = DeepBlack
                        )
                    )

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFE57373),
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Simulated Voice Quick Options (Fallback in case of no mic input / emulator)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Or tap a study phrase to speak it:",
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        val studyPhrases = listOf(
                            "Amen! Praise God.",
                            "Let's focus on Proverbs 3:5-6.",
                            "What a beautiful scripture translation.",
                            "Thank you for sharing that insight!",
                            "Let us pray together.",
                            "Truly inspirational verse."
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            studyPhrases.forEach { phrase ->
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF1E1E1E), RoundedCornerShape(16.dp))
                                        .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(16.dp))
                                        .clickable {
                                            speechText = phrase
                                            viewModel.showToast("Voice simulation: '$phrase'")
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(text = phrase, color = Color.White, fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { startListening() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222), contentColor = Color.White),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Retry Mic", fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                if (speechText.isNotBlank()) {
                                    inputMsg = (inputMsg + " " + speechText).trim()
                                }
                                showVoiceOverlay = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Apply Text", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCircleScreen(viewModel: LuminaViewModel) {
    var title by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("") }
    var scheduleText by remember { mutableStateOf("Live Now") }
    var selectedCategory by remember { mutableStateOf("Devotional") }
    var isPrivate by remember { mutableStateOf(false) }

    val categories = listOf("Devotional", "Theology", "Prayer Group", "Youth Circle", "Fellowship")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Study Circle", color = Color.White, fontFamily = FontFamily.Serif) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.Dashboard) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
            )
        },
        containerColor = DeepBlack
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Assemble believers globally for study sessions",
                color = TextSecondary,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Study Circle Title") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "STUDY CATEGORY",
                color = GoldPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSelected) GoldPrimary else SurfaceDark,
                                RoundedCornerShape(20.dp)
                            )
                            .border(
                                BorderStroke(1.dp, if (isSelected) GoldPrimary else BorderDark),
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) DeepBlack else Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                label = { Text("Focal Topic or Scripture (e.g., Romans 8)") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = scheduleText,
                onValueChange = { scheduleText = it },
                label = { Text("Session Schedule (e.g., Tuesdays at 7 PM or Live Now)") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Private Study Circle", color = Color.White, fontSize = 15.sp)
                    Text("Only members with invites can participate.", color = TextMuted, fontSize = 11.sp)
                }
                Switch(
                    checked = isPrivate,
                    onCheckedChange = { isPrivate = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = GoldPrimary,
                        checkedTrackColor = GoldDark
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val finalTopic = "[$selectedCategory] ${topic.trim()}"
                    viewModel.createStudyCircle(title, finalTopic, isPrivate, scheduleText)
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("create_circle_button")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = DeepBlack)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Launch Live Study Circle", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ProUpgradeDialog(
    onDismiss: () -> Unit,
    onUpgradeSuccess: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "LUMINA PRO EDITION",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Unlock the ultimate theological suite",
                    fontSize = 12.sp,
                    color = GoldPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                HorizontalDivider(color = Color(0x1AFFFFFF), thickness = 1.dp)
                
                ProBenefitRow(
                    icon = Icons.Default.MenuBook,
                    title = "All Translations Unlocked",
                    desc = "Study with NIV, NASB, NLT, AMP & more."
                )
                ProBenefitRow(
                    icon = Icons.Default.Star,
                    title = "AI Theological Companion",
                    desc = "Get deep historical & Greek/Hebrew exegesis."
                )
                ProBenefitRow(
                    icon = Icons.Default.VolumeUp,
                    title = "Audio Narrator & Read-Along",
                    desc = "Listen to scripture read aloud with auto-scrolling."
                )
                ProBenefitRow(
                    icon = Icons.Default.Mic,
                    title = "Live Voice Study Circles",
                    desc = "Speak live in your study rooms instead of just typing."
                )
                
                HorizontalDivider(color = Color(0x1AFFFFFF), thickness = 1.dp)
            }
        },
        confirmButton = {
            Button(
                onClick = onUpgradeSuccess,
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Activate Pro - $4.99/mo", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                border = BorderStroke(1.dp, BorderDark),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Maybe Later", color = Color.White)
            }
        },
        containerColor = Color(0xFF141414),
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun ProBenefitRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(20.dp).padding(top = 2.dp)
        )
        Column {
            Text(text = title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text(text = desc, color = TextMuted, fontSize = 11.sp)
        }
    }
}

fun getTheologicalCommentary(verseKey: String, verseText: String): String {
    return when {
        verseKey.contains("Proverbs 3:5") -> {
            "• Exegesis: 'Trust' (Hebrew 'batah') conveys throwing oneself on support. 'Heart' ('lebab') is the seat of mind, will and emotions.\n" +
            "• Historical context: Solomon emphasizes total reliance on divine covenant promises rather than finite human rationale."
        }
        verseKey.contains("Romans 8:1") -> {
            "• Exegesis: 'No condemnation' (Greek 'katakrima') is a legal acquittal. Union with Christ Jesus removes all punitive judgment.\n" +
            "• Theological Note: This shifts our standing from guilt under the Law to grace under the Spirit of life."
        }
        verseKey.contains("Acts 16:25") -> {
            "• Exegesis: 'Praying and singing' (Greek 'proseucomenoi hymnoun') show joy in tribulation.\n" +
            "• Theological Note: Genuine worship unleashes spiritual freedom and unlocks prison doors of suffering."
        }
        else -> {
            "• Theological Summary: This verse encourages deep meditation, reminding believers to align their thoughts with divine covenant wisdom and trust in sovereign providence."
        }
    }
}

@Composable
fun <T> LuminaSelector(
    label: String,
    selectedValue: T,
    options: List<T>,
    onValueSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    displayMapper: (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = label,
                color = GoldPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark, RoundedCornerShape(10.dp))
                    .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(10.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = displayMapper(selectedValue),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = "Expand",
                        tint = GoldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xFF141414))
                .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(8.dp))
                .widthIn(min = 100.dp, max = 220.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = displayMapper(option),
                            color = if (option == selectedValue) GoldPrimary else Color.White,
                            fontSize = 13.sp,
                            fontWeight = if (option == selectedValue) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onValueSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

val bibleData = mapOf(
    "Genesis" to mapOf(
        1 to listOf(
            "1 In the beginning, God created the heavens and the earth.",
            "2 Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.",
            "3 And God said, 'Let there be light,' and there was light.",
            "4 God saw that the light was good, and he separated the light from the darkness.",
            "5 God called the light 'day,' and the darkness he called 'night.' And there was evening, and there was morning—the first day."
        ),
        12 to listOf(
            "1 The Lord had said to Abram, 'Go from your country, your people and your father’s household to the land I will show you.'",
            "2 I will make you into a great nation, and I will bless you; I will make your name great, and you will be a blessing.",
            "3 I will bless those who bless you, and whoever curses you I will curse; and all peoples on earth will be blessed through you."
        )
    ),
    "Psalms" to mapOf(
        23 to listOf(
            "1 The Lord is my shepherd, I lack nothing.",
            "2 He makes me lie down in green pastures, he leads beside still waters,",
            "3 he restores my soul. He guides me in paths of righteousness for his name’s sake.",
            "4 Even though I walk through the valley of the shadow of death, I will fear no evil, for you are with me; your rod and your staff, they comfort me.",
            "5 You prepare a table before me in the presence of my enemies. You anoint my head with oil; my cup overflows.",
            "6 Surely goodness and mercy shall follow me all the days of my life, and I shall dwell in the house of the Lord forever."
        ),
        121 to listOf(
            "1 I lift up my eyes to the hills—from where does my help come?",
            "2 My help comes from the Lord, who made heaven and earth.",
            "3 He will not let your foot be moved; he who keeps you will not slumber.",
            "4 Behold, he who keeps Israel will neither slumber nor sleep.",
            "5 The Lord is your keeper; the Lord is your shade on your right hand.",
            "6 The sun shall not strike you by day, nor the moon by night.",
            "7 The Lord will keep you from all evil; he will keep your life.",
            "8 The Lord will keep your going out and your coming in from this time forth and forevermore."
        )
    ),
    "Proverbs" to mapOf(
        3 to listOf(
            "1 My son, do not forget my teaching, but keep my commands in your heart,",
            "2 for they will prolong your life many years and bring you peace and prosperity.",
            "3 Let love and faithfulness never leave you; bind them around your neck, write them on the tablet of your heart.",
            "4 Then you will win favor and a good name in the sight of God and man.",
            "5 Trust in the Lord with all your heart and lean not on your own understanding;",
            "6 in all your ways submit to him, and he will make your paths straight.",
            "7 Do not be wise in your own eyes; fear the Lord and shun evil.",
            "8 This will bring health to your body and nourishment to your bones."
        ),
        4 to listOf(
            "1 Listen, my sons, to a father’s instruction; pay attention and gain understanding.",
            "2 I give you sound learning, so do not forsake my teaching.",
            "3 For I too was a son to my father, still tender, and cherished by my mother.",
            "4 Then he taught me, and he said to me, 'Take hold of my words with all your heart; keep my commands, and you will live.'"
        )
    ),
    "John" to mapOf(
        3 to listOf(
            "16 For God so loved the world, that he gave his only Son, that whoever believes in him should not perish but have eternal life.",
            "17 For God did not send his Son into the world to condemn the world, but in order that the world might be saved through him."
        ),
        14 to listOf(
            "1 Do not let your hearts be troubled. Believe in God; believe also in me.",
            "2 In my Father’s house are many rooms. If it were not so, would I have told you that I go to prepare a place for you?",
            "6 Jesus said to him, 'I am the way, and the truth, and the life. No one comes to the Father except through me.'"
        )
    ),
    "Romans" to mapOf(
        8 to listOf(
            "1 There is therefore now no condemnation for those who are in Christ Jesus.",
            "2 For the law of the Spirit of life has set you free in Christ Jesus from the law of sin and death.",
            "3 For God has done what the law, weakened by the flesh, could not do. By sending his own Son in the likeness of sinful flesh...",
            "14 For all who are led by the Spirit of God are sons of God."
        ),
        12 to listOf(
            "1 I appeal to you therefore, brothers, by the mercies of God, to present your bodies as a living sacrifice, holy and acceptable to God, which is your spiritual worship.",
            "2 Do not be conformed to this world, but be transformed by the renewal of your mind, that by testing you may discern what is the will of God, what is good and acceptable and perfect."
        )
    ),
    "Acts" to mapOf(
        16 to listOf(
            "25 About midnight Paul and Silas were praying and singing hymns to God, and the other prisoners were listening to them.",
            "26 Suddenly there was such a violent earthquake that the foundations of the prison were shaken.",
            "27 At once all the prison doors flew open, and everyone’s chains came loose."
        )
    )
)

val oldTestamentBooks = listOf(
    "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth",
    "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther",
    "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon",
    "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel",
    "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi"
)

val newTestamentBooks = listOf(
    "Matthew", "Mark", "Luke", "John", "Acts",
    "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
    "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James",
    "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"
)

val allBibleBooks = listOf(
    "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth",
    "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther",
    "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon",
    "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel",
    "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi",
    "Matthew", "Mark", "Luke", "John", "Acts",
    "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
    "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James",
    "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"
)

val bibleBookChapters = mapOf(
    "Genesis" to 50, "Exodus" to 40, "Leviticus" to 27, "Numbers" to 36, "Deuteronomy" to 34,
    "Joshua" to 24, "Judges" to 21, "Ruth" to 4, "1 Samuel" to 31, "2 Samuel" to 24,
    "1 Kings" to 22, "2 Kings" to 25, "1 Chronicles" to 29, "2 Chronicles" to 36,
    "Ezra" to 10, "Nehemiah" to 13, "Esther" to 10, "Job" to 42, "Psalms" to 150,
    "Proverbs" to 31, "Ecclesiastes" to 12, "Song of Solomon" to 8, "Isaiah" to 66,
    "Jeremiah" to 52, "Lamentations" to 5, "Ezekiel" to 48, "Daniel" to 12,
    "Hosea" to 14, "Joel" to 3, "Amos" to 9, "Obadiah" to 1, "Jonah" to 4,
    "Micah" to 7, "Nahum" to 3, "Habakkuk" to 3, "Zephaniah" to 3, "Haggai" to 2,
    "Zechariah" to 14, "Malachi" to 4, "Matthew" to 28, "Mark" to 16, "Luke" to 24,
    "John" to 21, "Acts" to 28, "Romans" to 16, "1 Corinthians" to 16, "2 Corinthians" to 13,
    "Galatians" to 6, "Ephesians" to 6, "Philippians" to 4, "Colossians" to 4,
    "1 Thessalonians" to 5, "2 Thessalonians" to 3, "1 Timothy" to 6, "2 Timothy" to 4,
    "Titus" to 3, "Philemon" to 1, "Hebrews" to 13, "James" to 5, "1 Peter" to 5,
    "2 Peter" to 3, "1 John" to 5, "2 John" to 1, "3 John" to 1, "Jude" to 1,
    "Revelation" to 22
)

fun getDynamicVerses(book: String, chapter: Int): List<String> {
    val bookData = bibleData[book]
    if (bookData != null) {
        val chapterData = bookData[chapter]
        if (chapterData != null) {
            return chapterData
        }
    }

    val category = when (book) {
        "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther" -> "History"
        "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon" -> "Poetry & Wisdom"
        "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi" -> "Prophecy"
        "Matthew", "Mark", "Luke", "John", "Acts" -> "Gospels & Acts"
        "Revelation" -> "Apocalypse"
        else -> "Epistles"
    }

    return when (category) {
        "History" -> listOf(
            "1 In those days, the Lord spoke to the leaders of Israel, saying, 'Observe my statutes and walk in my ways.'",
            "2 And the people answered with one voice: 'All that the Lord has commanded, we will do.'",
            "3 So they set up a memorial of stones to remember the covenant faithfulness of God throughout their generations.",
            "4 For the Lord is great and greatly to be praised; he led his inheritance through the wilderness with a pillar of cloud by day and fire by night.",
            "5 Therefore, fear the Lord and serve him with a whole heart, for he has done marvelous deeds in your sight.",
            "6 The leaders guided the assembly in truth, instructing them in the holy precepts written in the book of the law.",
            "7 And the peace of the Lord rested upon the land, and all the surrounding nations saw the wisdom given to Israel.",
            "8 For the hand of the Lord was with them, delivering them from all adversity as they trusted in his holy name."
        )
        "Poetry & Wisdom" -> listOf(
            "1 Hear my prayer, O Lord, and let my cry come before you; do not hide your face from me in my day of distress.",
            "2 Your lovingkindness is better than life; my lips shall praise you, and my soul shall be satisfied as with marrow and fatness.",
            "3 Commit your way to the Lord; trust also in him, and he will bring it to pass.",
            "4 He will bring forth your righteousness as the light, and your justice as the noonday.",
            "5 Praise the Lord, O my soul! Let everything within me praise his holy name forever and ever.",
            "6 Blessed is the person who walks in wisdom and finds understanding, for its profit is better than silver and gold.",
            "7 For wisdom is a tree of life to those who lay hold of her, and happy are all who retain her.",
            "8 The Lord by wisdom founded the earth, establishing the heavens with infinite understanding and grace."
        )
        "Prophecy" -> listOf(
            "1 The word of the Lord came to the prophet, saying: 'Comfort, comfort my people, speak tenderly to them.'",
            "2 For the glory of the Lord shall be revealed, and all flesh shall see it together, for the mouth of the Lord has spoken.",
            "3 A voice cries out in the wilderness: 'Prepare the way of the Lord; make straight a highway for our God.'",
            "4 Sing to the Lord a new song, and his praise from the ends of the earth, for he has redeemed his servants.",
            "5 Look unto me, and be saved, all the ends of the earth; for I am God, and there is none other.",
            "6 Arise, shine; for your light has come, and the glory of the Lord has risen upon you in majesty.",
            "7 For behold, darkness shall cover the earth, but the Lord shall arise upon you, and his glory shall be seen.",
            "8 I will pour out my Spirit upon all flesh, says the Lord, and your sons and daughters shall prophesy."
        )
        "Gospels & Acts" -> listOf(
            "1 Now Jesus went about all the cities and villages, teaching in their synagogues, preaching the gospel of the kingdom, and healing every sickness.",
            "2 He saw the multitudes and was moved with compassion, because they were weary and scattered like sheep having no shepherd.",
            "3 Jesus said, 'Come to me, all you who labor and are heavy laden, and I will give you rest.'",
            "4 'Take my yoke upon you and learn from me, for I am gentle and lowly in heart, and you will find rest for your souls.'",
            "5 'For my yoke is easy and my burden is light. Truly, I say to you, the kingdom of heaven is at hand.'",
            "6 And they were all filled with the Holy Spirit and began to speak the word of God with power and great boldness.",
            "7 The believers devoted themselves to the apostles' teaching, to fellowship, to the breaking of bread, and to prayers.",
            "8 And the Lord added to their number day by day those who were being saved into eternal grace."
        )
        "Apocalypse" -> listOf(
            "1 And I saw a new heaven and a new earth, for the first heaven and the first earth had passed away, and there was no more sea.",
            "2 I heard a loud voice from the throne saying, 'Behold, the tabernacle of God is with men, and he will dwell with them.'",
            "3 'He will wipe away every tear from their eyes, and death shall be no more, neither shall there be mourning nor crying nor pain.'",
            "4 He who sits on the throne said, 'Behold, I make all things new! Write this down, for these words are trustworthy and true.'",
            "5 I am the Alpha and the Omega, the Beginning and the End. To the thirsty I will give from the spring of the water of life without payment.",
            "6 The city has no need of sun or moon to shine on it, for the glory of God gives it light, and its lamp is the Lamb.",
            "7 Blessed is the one who keeps the words of the prophecy of this book and walks in the light of the living King.",
            "8 He who testifies to these things says, 'Surely I am coming soon.' Amen. Come, Lord Jesus!"
        )
        else -> listOf( // Epistles
            "1 Grace to you and peace from God our Father and the Lord Jesus Christ, in whom we have obtained an inheritance.",
            "2 Blessed be the God and Father of our Lord Jesus Christ, who has blessed us in the heavenly places with every spiritual blessing.",
            "3 Walk in a manner worthy of the calling with which you have been called, with all humility, gentleness, and patience.",
            "4 Do not be anxious about anything, but in everything by prayer and supplication with thanksgiving let your requests be made known to God.",
            "5 And the peace of God, which surpasses all comprehension, will guard your hearts and your minds in Christ Jesus.",
            "6 Finally, brothers, whatever is true, whatever is honorable, whatever is just, whatever is pure, lovely, and commendable—think about these things.",
            "7 For I can do all things through Him who strengthens me, who infuses my heart with his everlasting power.",
            "8 The grace of the Lord Jesus Christ, and the love of God, and the fellowship of the Holy Spirit be with you all."
        )
    }
}

fun getHighlightText(book: String, chapter: Int, verseNum: Int): String {
    val verses = getDynamicVerses(book, chapter)
    val verseStr = verseNum.toString()
    val matching = verses.firstOrNull { it.substringBefore(" ").trim() == verseStr }
    return matching?.substringAfter(" ")?.trim() ?: "Blessed are those who search for wisdom with all their heart."
}

@Composable
fun ScriptureScreen(viewModel: LuminaViewModel, onMenuClick: (() -> Unit)? = null) {
    val isPro by viewModel.isProUser.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val highlights by viewModel.highlights.collectAsStateWithLifecycle()
    val allGroups by viewModel.allGroups.collectAsStateWithLifecycle()
    val readVerses by viewModel.readVerses.collectAsStateWithLifecycle()
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val unlockedBadges by viewModel.unlockedBadges.collectAsStateWithLifecycle()
    val isOfflineMode by viewModel.isOfflineMode.collectAsStateWithLifecycle()
    val studyGoalVerses by viewModel.studyGoalVerses.collectAsStateWithLifecycle()
    val studyGoalChapters by viewModel.studyGoalChapters.collectAsStateWithLifecycle()
    val studyGoalMinutes by viewModel.studyGoalMinutes.collectAsStateWithLifecycle()
    val sessionTimeSeconds by viewModel.sessionTimeSeconds.collectAsStateWithLifecycle()
    val isFocusMode by viewModel.isFocusMode.collectAsStateWithLifecycle()

    var selectedBook by remember { mutableStateOf("Proverbs") }
    var selectedChapterNum by remember { mutableStateOf(3) }
    var selectedVerseNum by remember { mutableStateOf("All") }

    var selectedTranslation by remember { mutableStateOf("ESV") }
    var showProDialog by remember { mutableStateOf(false) }
    var isAudioPlaying by remember { mutableStateOf(false) }
    var selectedVerseKey by remember { mutableStateOf<String?>(null) }

    var showVoiceNoteOverlay by remember { mutableStateOf(false) }
    var voiceNoteTargetText by remember { mutableStateOf("") }
    var voiceNoteOnApply by remember { mutableStateOf<(String) -> Unit>({ }) }

    var activeTab by remember { mutableStateOf("Read") } // "Read", "Discover", "Badges"
    var bibleFontSize by remember { mutableFloatStateOf(15f) }
    var selectedFontFamily by remember { mutableStateOf("Serif") }

    val bibleFontFamily = when (selectedFontFamily) {
        "Serif" -> FontFamily.Serif
        "Sans" -> FontFamily.SansSerif
        "Monospace" -> FontFamily.Monospace
        else -> FontFamily.Default
    }

    // TextToSpeech Narration setup
    val context = LocalContext.current
    var tts by remember { mutableStateOf<android.speech.tts.TextToSpeech?>(null) }
    var isTtsInitialized by remember { mutableStateOf(false) }
    var ttsRate by remember { mutableFloatStateOf(1.0f) }
    var ttsPitch by remember { mutableFloatStateOf(1.0f) }

    DisposableEffect(context) {
        val ttsInstance = android.speech.tts.TextToSpeech(context) { status ->
            if (status == android.speech.tts.TextToSpeech.SUCCESS) {
                isTtsInitialized = true
            }
        }
        tts = ttsInstance
        onDispose {
            ttsInstance.stop()
            ttsInstance.shutdown()
        }
    }

    LaunchedEffect(ttsRate, ttsPitch, tts) {
        tts?.setSpeechRate(ttsRate)
        tts?.setPitch(ttsPitch)
    }

    // Daily Rotating Verses
    val dailyVerses = remember {
        listOf(
            Triple("Proverbs 3:5-6", "Trust in the Lord with all your heart, and do not lean on your own understanding. In all your ways acknowledge him, and he will make straight your paths.", "Proverbs"),
            Triple("Joshua 1:9", "Have I not commanded you? Be strong and courageous. Do not be frightened, and do not be dismayed, for the Lord your God is with you wherever you go.", "Joshua"),
            Triple("Psalms 23:1", "The Lord is my shepherd; I shall not want. He makes me lie down in green pastures. He leads me beside still waters.", "Psalms"),
            Triple("John 14:27", "Peace I leave with you; my peace I give to you. Not as the world gives do I give to you. Let not your hearts be troubled, neither let them be afraid.", "John"),
            Triple("Romans 8:28", "And we know that for those who love God all things work together for good, for those who are called according to his purpose.", "Romans"),
            Triple("Philippians 4:13", "I can do all things through him who strengthens me.", "Philippians"),
            Triple("Isaiah 40:31", "But they who wait for the Lord shall renew their strength; they shall mount up with wings like eagles; they shall run and not be weary; they shall walk and not faint.", "Isaiah"),
            Triple("Matthew 6:33", "But seek first the kingdom of God and his righteousness, and all these things will be added to you.", "Matthew"),
            Triple("Psalms 46:1", "God is our refuge and strength, a very present help in trouble.", "Psalms"),
            Triple("Galatians 5:22-23", "But the fruit of the Spirit is love, joy, peace, patience, kindness, goodness, faithfulness, gentleness, self-control; against such things there is no law.", "Galatians"),
            Triple("Romans 12:2", "Do not be conformed to this world, but be transformed by the renewal of your mind, that by testing you may discern what is the will of God, what is good and acceptable and perfect.", "Romans"),
            Triple("Hebrews 11:1", "Now faith is the assurance of things hoped for, the conviction of things not seen.", "Hebrews")
        )
    }
    val calendar = java.util.Calendar.getInstance()
    val dayIndex = calendar.get(java.util.Calendar.DAY_OF_YEAR) % dailyVerses.size
    val activeDailyVerse = dailyVerses[dayIndex]

    val translations = listOf("ESV", "KJV", "NIV", "NASB")
    val studyNotes by viewModel.studyNotes.collectAsStateWithLifecycle()

    val apiBibleVerses by viewModel.apiBibleVerses.collectAsStateWithLifecycle()
    val useApiBibleText by viewModel.useApiBibleText.collectAsStateWithLifecycle()
    val apiBibleTextLoading by viewModel.apiBibleTextLoading.collectAsStateWithLifecycle()
    val apiBibleTextError by viewModel.apiBibleTextError.collectAsStateWithLifecycle()

    LaunchedEffect(selectedBook, selectedChapterNum, selectedTranslation, useApiBibleText) {
        if (useApiBibleText) {
            viewModel.fetchApiBibleChapter(selectedBook, selectedChapterNum, selectedTranslation)
        }
    }

    // Sync variables with current state using our 66 books mapping
    val totalChapters = bibleBookChapters[selectedBook] ?: 1
    val availableChapters = (1..totalChapters).toList()
    if (selectedChapterNum !in availableChapters) {
        selectedChapterNum = 1
    }

    val currentChapterVerses = if (useApiBibleText && apiBibleVerses.isNotEmpty() && !isOfflineMode) {
        apiBibleVerses
    } else {
        getDynamicVerses(selectedBook, selectedChapterNum)
    }
    val availableVerses = listOf("All") + currentChapterVerses.map { it.substringBefore(" ").trim() }
    if (selectedVerseNum !in availableVerses) {
        selectedVerseNum = "All"
    }

    val selectedChapter = "$selectedBook $selectedChapterNum"

    if (showProDialog) {
        ProUpgradeDialog(
            onDismiss = { showProDialog = false },
            onUpgradeSuccess = {
                viewModel.upgradeToPro()
                showProDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (isFocusMode) {
            // Elegant distraction-free header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Focus Reading",
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "$selectedBook $selectedChapterNum",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Minimalist Font Adjustments in Focus Mode
                    IconButton(
                        onClick = { if (bibleFontSize > 12f) bibleFontSize -= 1f },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text("A-", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "${bibleFontSize.toInt()}",
                        color = GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { if (bibleFontSize < 28f) bibleFontSize += 1f },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text("A+", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Exit Focus Button
                    IconButton(
                        onClick = { viewModel.toggleFocusMode() },
                        modifier = Modifier
                            .background(GoldPrimary.copy(alpha = 0.15f), CircleShape)
                            .size(32.dp)
                            .testTag("exit_focus_mode_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FullscreenExit,
                            contentDescription = "Exit Focus Mode",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        } else {
            // Title Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (onMenuClick != null) {
                        IconButton(
                            onClick = onMenuClick,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = GoldPrimary)
                        }
                    }
                    Text(
                        text = "Holy Scripture",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Focus Mode Toggle Action Button
                    IconButton(
                        onClick = { viewModel.toggleFocusMode() },
                        modifier = Modifier.testTag("focus_mode_toggle_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Enter Focus Mode",
                            tint = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Offline Mode Quick Action Button
                    IconButton(
                        onClick = {
                            viewModel.toggleOfflineMode()
                        }
                    ) {
                        Icon(
                            imageVector = if (isOfflineMode) Icons.Default.CloudOff else Icons.Default.CloudQueue,
                            contentDescription = "Toggle Offline Mode",
                            tint = if (isOfflineMode) Color(0xFFE57373) else GoldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Dark Mode Toggle Button
                    val currentTheme by viewModel.currentStudyTheme.collectAsStateWithLifecycle()
                    val isDark = currentTheme != com.example.ui.StudyTheme.IVORY_LIGHT
                    IconButton(
                        onClick = {
                            if (isDark) {
                                viewModel.setStudyTheme(com.example.ui.StudyTheme.IVORY_LIGHT)
                            } else {
                                viewModel.setStudyTheme(com.example.ui.StudyTheme.COSMIC_DARK)
                            }
                        },
                        modifier = Modifier.testTag("dark_mode_toggle_scripture")
                    ) {
                        Icon(
                            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Dark/Light Mode",
                            tint = GoldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Font Size adjustments
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        IconButton(
                            onClick = { if (bibleFontSize > 12f) bibleFontSize -= 1f },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("A-", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "${bibleFontSize.toInt()}",
                            color = GoldPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { if (bibleFontSize < 28f) bibleFontSize += 1f },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("A+", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Audio Playback Button
                    IconButton(
                        onClick = {
                            if (isPro) {
                                isAudioPlaying = !isAudioPlaying
                                if (isAudioPlaying) {
                                    viewModel.showToast("Starting Audio Playback...")
                                    viewModel.trackTtsPlayback()
                                    val plainText = currentChapterVerses.map { it.substringAfter(" ") }.joinToString(". ")
                                    tts?.speak(plainText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "ChapterTTS")
                                } else {
                                    tts?.stop()
                                    viewModel.showToast("Audio Narrator paused.")
                                }
                            } else {
                                showProDialog = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isAudioPlaying) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                            contentDescription = "Audio Narrator",
                            tint = if (isAudioPlaying) GoldPrimary else Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Sub-navigation Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf(
                    Triple("Read", Icons.Default.Book, viewModel.translate("scripture")),
                    Triple("Devotional", Icons.Default.Favorite, viewModel.translate("daily_devotional")),
                    Triple("Prayers", Icons.Default.PostAdd, viewModel.translate("prayer_journal")),
                    Triple("Quiz", Icons.Default.Quiz, "Quiz"),
                    Triple("Notes", Icons.Default.Edit, "My Notes"),
                    Triple("Discover", Icons.Default.Explore, "Discover"),
                    Triple("Badges", Icons.Default.EmojiEvents, viewModel.translate("achievements")),
                    Triple("Plans", Icons.Default.DateRange, "Plans"),
                    Triple("Dictionary", Icons.Default.MenuBook, "Dictionary")
                )
                tabs.forEach { (tabId, icon, label) ->
                    val isSelected = activeTab == tabId
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.2f) else SurfaceDark,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (isSelected) GoldPrimary else BorderDark
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { activeTab = tabId }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("scripture_tab_$tabId"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSelected) GoldPrimary else Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = label,
                                color = if (isSelected) GoldPrimary else Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (activeTab == "Read" || isFocusMode) {
            if (!isFocusMode) {
                if (isOfflineMode) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .background(Color(0x15E57373), RoundedCornerShape(10.dp))
                        .border(BorderStroke(1.dp, Color(0x33E57373)), RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.CloudOff, contentDescription = "Offline Mode Active", tint = Color(0xFFE57373), modifier = Modifier.size(14.dp))
                        Text("OFFLINE MODE ACTIVE: Reading Cached Data", color = Color(0xFFE57373), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    TextButton(
                        onClick = { viewModel.toggleOfflineMode() },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(24.dp)
                    ) {
                        Text("Go Online", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Quick Access Bookmarks Section (if bookmarks exist)
            if (bookmarks.isNotEmpty()) {
                Text(
                    text = "QUICK ACCESS BOOKMARKS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 12.dp)
                        .testTag("quick_access_bookmarks_row"),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    bookmarks.forEach { bookmark ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF1E1C18), RoundedCornerShape(20.dp))
                                .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)), RoundedCornerShape(20.dp))
                                .clickable {
                                    try {
                                        val lastSpace = bookmark.lastIndexOf(' ')
                                        if (lastSpace != -1) {
                                            val book = bookmark.substring(0, lastSpace).trim()
                                            val ref = bookmark.substring(lastSpace + 1).trim()
                                            val chapter = ref.substringBefore(":").toIntOrNull() ?: 1
                                            val verse = ref.substringAfter(":")
                                            
                                            selectedBook = book
                                            selectedChapterNum = chapter
                                            selectedVerseNum = verse
                                            viewModel.showToast("Jumping to $bookmark")
                                        }
                                    } catch (e: Exception) {
                                        viewModel.showToast("Unable to parse bookmark")
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Quick Access",
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = bookmark,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Daily Reading Goal Progress Card (Jetpack Compose M3)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1C15)),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
                    .testTag("daily_reading_goal_progress_card")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = "Daily Goals", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                            Text("DAILY READING PROGRESS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 0.5.sp)
                        }
                        Text("Customize Goals", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                            viewModel.navigateTo(Screen.Settings)
                            viewModel.showToast("Customize study goals in Profile & Settings!")
                        })
                    }

                    val sessionMinutes = sessionTimeSeconds / 60
                    val timeGoalProgress = if (studyGoalMinutes > 0) (sessionMinutes.toFloat() / studyGoalMinutes.toFloat()).coerceIn(0f, 1f) else 0f
                    val verseGoalProgress = if (studyGoalVerses > 0) (readVerses.size.toFloat() / studyGoalVerses.toFloat()).coerceIn(0f, 1f) else 0f

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Time Goal Indicator
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Study Timer", color = TextSecondary, fontSize = 10.sp)
                                Text("${sessionMinutes}m / ${studyGoalMinutes}m", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { timeGoalProgress },
                                color = GoldPrimary,
                                trackColor = Color(0xFF2C2C2C),
                                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp))
                            )
                        }

                        // Verses Goal Indicator
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Verses Studied", color = TextSecondary, fontSize = 10.sp)
                                Text("${readVerses.size} / ${studyGoalVerses}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { verseGoalProgress },
                                color = Color(0xFF4CAF50),
                                trackColor = Color(0xFF2C2C2C),
                                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp))
                            )
                        }
                    }
                }
            }

            // Elegant, custom Book, Chapter, and Verse Selector Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, BorderDark),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "SELECT SCRIPTURE PASSAGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // 1. Old Testament vs New Testament Split Tab
                var selectedTestament by remember(selectedBook) {
                    mutableStateOf(if (newTestamentBooks.contains(selectedBook)) "New Testament" else "Old Testament")
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(Color(0xFF141414), RoundedCornerShape(8.dp))
                        .padding(2.dp)
                ) {
                    listOf("Old Testament", "New Testament").forEach { testament ->
                        val isSel = selectedTestament == testament
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = if (isSel) GoldPrimary.copy(alpha = 0.15f) else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSel) GoldPrimary else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable {
                                    selectedTestament = testament
                                    if (testament == "New Testament" && !newTestamentBooks.contains(selectedBook)) {
                                        selectedBook = "Matthew"
                                        selectedChapterNum = 1
                                        selectedVerseNum = "All"
                                        selectedVerseKey = null
                                    } else if (testament == "Old Testament" && !oldTestamentBooks.contains(selectedBook)) {
                                        selectedBook = "Genesis"
                                        selectedChapterNum = 1
                                        selectedVerseNum = "All"
                                        selectedVerseKey = null
                                    }
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = testament,
                                color = if (isSel) GoldPrimary else Color.White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Book selector (filtered by testament)
                    val bookOptions = if (selectedTestament == "New Testament") newTestamentBooks else oldTestamentBooks
                    LuminaSelector(
                        label = "Book",
                        selectedValue = selectedBook,
                        options = bookOptions,
                        onValueSelected = { book ->
                            selectedBook = book
                            selectedChapterNum = 1
                            selectedVerseNum = "All"
                            selectedVerseKey = null
                        },
                        modifier = Modifier.weight(1.4f)
                    )

                    // Chapter selector
                    LuminaSelector(
                        label = "Chapter",
                        selectedValue = selectedChapterNum,
                        options = availableChapters,
                        onValueSelected = { ch ->
                            selectedChapterNum = ch
                            selectedVerseNum = "All"
                            selectedVerseKey = null
                        },
                        modifier = Modifier.weight(0.9f)
                    )

                    // Verse selector
                    val versesList = listOf("All") + currentChapterVerses.map { it.substringBefore(" ").trim() }
                    LuminaSelector(
                        label = "Verse",
                        selectedValue = selectedVerseNum,
                        options = versesList,
                        onValueSelected = { v ->
                            selectedVerseNum = v
                            if (v != "All") {
                                selectedVerseKey = "$selectedBook $selectedChapterNum:$v"
                            } else {
                                selectedVerseKey = null
                            }
                        },
                        modifier = Modifier.weight(1.1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 2. Verse Navigator Row (Previous and Next Chapter buttons)
                val currentBookIndex = allBibleBooks.indexOf(selectedBook)
                val totalBookCh = bibleBookChapters[selectedBook] ?: 1
                val canGoPrev = selectedChapterNum > 1 || currentBookIndex > 0
                val canGoNext = selectedChapterNum < totalBookCh || currentBookIndex < allBibleBooks.size - 1

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Previous Chapter Navigator Button
                    OutlinedButton(
                        onClick = {
                            if (selectedChapterNum > 1) {
                                selectedChapterNum -= 1
                                selectedVerseNum = "All"
                                selectedVerseKey = null
                            } else if (currentBookIndex > 0) {
                                val prevBook = allBibleBooks[currentBookIndex - 1]
                                selectedBook = prevBook
                                val lastCh = bibleBookChapters[prevBook] ?: 1
                                selectedChapterNum = lastCh
                                selectedVerseNum = "All"
                                selectedVerseKey = null
                            }
                        },
                        enabled = canGoPrev,
                        border = BorderStroke(1.dp, if (canGoPrev) GoldPrimary.copy(alpha = 0.5f) else Color(0x22FFFFFF)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GoldPrimary,
                            disabledContentColor = TextMuted
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Prev",
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Previous",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Next Chapter Navigator Button
                    Button(
                        onClick = {
                            if (selectedChapterNum < totalBookCh) {
                                selectedChapterNum += 1
                                selectedVerseNum = "All"
                                selectedVerseKey = null
                            } else if (currentBookIndex < allBibleBooks.size - 1) {
                                val nextBook = allBibleBooks[currentBookIndex + 1]
                                selectedBook = nextBook
                                selectedChapterNum = 1
                                selectedVerseNum = "All"
                                selectedVerseKey = null
                            }
                        },
                        enabled = canGoNext,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (canGoNext) GoldPrimary else Color(0xFF222222),
                            contentColor = DeepBlack,
                            disabledContainerColor = Color(0xFF141414),
                            disabledContentColor = TextMuted
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Next Chapter",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Font Family:",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    listOf("Serif", "Sans", "Monospace").forEach { fontName ->
                        val isSel = selectedFontFamily == fontName
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = if (isSel) GoldPrimary.copy(alpha = 0.25f) else Color(0xFF141414),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isSel) GoldPrimary else BorderDark
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedFontFamily = fontName }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = fontName,
                                color = if (isSel) GoldPrimary else Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = when (fontName) {
                                    "Serif" -> FontFamily.Serif
                                    "Sans" -> FontFamily.SansSerif
                                    "Monospace" -> FontFamily.Monospace
                                    else -> FontFamily.Default
                                }
                            )
                        }
                    }
                }
            }
        }

        // Translation selector row
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            translations.forEach { trans ->
                val isSelected = trans == selectedTranslation
                val isLocked = (trans == "NIV" || trans == "NASB") && !isPro

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = if (isSelected) GoldPrimary.copy(alpha = 0.2f) else SurfaceDark,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) GoldPrimary else BorderDark
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            if (isLocked) {
                                showProDialog = true
                            } else {
                                selectedTranslation = trans
                                viewModel.showToast("Switched reading to $trans translation")
                            }
                        }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = trans,
                            color = if (isSelected) GoldPrimary else Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (isLocked) {
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Pro Lock",
                                tint = GoldPrimary,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                    }
                }
            }
        }
        }

        // Focused single-verse hint banner if filtered
        if (selectedVerseNum != "All") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(Color(0x10FFFFFF), RoundedCornerShape(10.dp))
                    .border(BorderStroke(1.dp, Color(0x22FFFFFF)), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Focused Study • $selectedBook $selectedChapterNum:$selectedVerseNum",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Show Full Chapter",
                    color = GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        selectedVerseNum = "All"
                        selectedVerseKey = null
                    }
                )
            }
        }

        // Audio playing indicator bar if active
        if (isAudioPlaying) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(Color(0x15C9A063), RoundedCornerShape(10.dp))
                    .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)), RoundedCornerShape(10.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.size(8.dp).background(GoldPrimary, CircleShape))
                    Text("AUDIO READING ACTIVE: $selectedChapter ($selectedTranslation)", color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Text("PRO Narrator", color = GoldPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Circle Reading State Sync Panel
        if (allGroups.isNotEmpty()) {
            var selectedGroupForSync by remember(allGroups) { mutableStateOf(allGroups.firstOrNull()) }
            var expandedSyncGroupDropdown by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(Color(0xFF0F0F0F), RoundedCornerShape(12.dp))
                    .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(12.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(imageVector = Icons.Default.Sync, contentDescription = "Sync", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Sync Reading State", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        selectedGroupForSync?.let { group ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { expandedSyncGroupDropdown = true }) {
                                Text(
                                    text = "Circle: ${group.title}",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(12.dp))
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = expandedSyncGroupDropdown,
                        onDismissRequest = { expandedSyncGroupDropdown = false },
                        modifier = Modifier.background(SurfaceDark)
                    ) {
                        allGroups.forEach { g ->
                            DropdownMenuItem(
                                text = { Text(g.title, color = Color.White, fontSize = 12.sp) },
                                onClick = {
                                    selectedGroupForSync = g
                                    expandedSyncGroupDropdown = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        selectedGroupForSync?.let { group ->
                            val stateString = "$selectedBook $selectedChapterNum" + (if (selectedVerseNum != "All") ":$selectedVerseNum" else "")
                            viewModel.syncCircleReadingState(group.id, stateString)
                            viewModel.showToast("Reading focus synced to '${group.title}' circle!")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text("Sync Focus", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // ----------------- Scripture Reading Progress Card -----------------
        val totalBookChapters = bibleBookChapters[selectedBook] ?: 1
        val completedChaptersInBook = completedChapters.filter { it.startsWith("$selectedBook ") }.size
        val bookPercentage = if (totalBookChapters > 0) {
            (completedChaptersInBook.toFloat() / totalBookChapters.toFloat() * 100).toInt()
        } else {
            0
        }

        val totalChapterVerses = currentChapterVerses.size
        val readVersesInChapter = currentChapterVerses.count { verse ->
            val vNum = verse.substringBefore(" ").trim()
            readVerses.contains("$selectedBook $selectedChapterNum:$vNum")
        }
        val chapterPercentage = if (totalChapterVerses > 0) {
            (readVersesInChapter.toFloat() / totalChapterVerses.toFloat() * 100).toInt()
        } else {
            0
        }
        val isChapterFullyCompleted = completedChapters.contains("$selectedBook $selectedChapterNum") || (totalChapterVerses > 0 && readVersesInChapter == totalChapterVerses)

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, BorderDark),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = "Progress",
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "READING FOCUS PROGRESS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                    }
                    
                    Text(
                        text = if (isChapterFullyCompleted) "Completed ✓" else "Mark Chapter Read",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isChapterFullyCompleted) GoldPrimary else TextSecondary,
                        modifier = Modifier
                            .background(
                                color = if (isChapterFullyCompleted) GoldPrimary.copy(alpha = 0.15f) else Color(0xFF222222),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                viewModel.toggleChapterCompleted(selectedBook, selectedChapterNum, currentChapterVerses)
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1.3f)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = "Chapter $selectedChapterNum",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                text = "$chapterPercentage% ($readVersesInChapter/$totalChapterVerses v)",
                                fontSize = 10.sp,
                                color = TextSecondary
                            )
                        }
                        
                        LinearProgressIndicator(
                            progress = { if (totalChapterVerses > 0) readVersesInChapter.toFloat() / totalChapterVerses.toFloat() else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),
                            color = GoldPrimary,
                            trackColor = Color(0xFF222222),
                            strokeCap = StrokeCap.Round
                        )
                    }

                    Column(modifier = Modifier.weight(1.3f)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = selectedBook,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                text = "$bookPercentage% ($completedChaptersInBook/$totalBookChapters ch)",
                                fontSize = 10.sp,
                                color = TextSecondary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { if (totalBookChapters > 0) completedChaptersInBook.toFloat() / totalBookChapters.toFloat() else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),
                            color = Color(0xFF0288D1),
                            trackColor = Color(0xFF222222),
                            strokeCap = StrokeCap.Round
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                val motivationalText = when {
                    chapterPercentage == 100 -> "Incredible! You've completed Chapter $selectedChapterNum. Ready for Chapter ${if (selectedChapterNum < totalBookChapters) selectedChapterNum + 1 else 1}?"
                    chapterPercentage >= 75 -> "Almost there! Just a couple of verses left to master this chapter."
                    chapterPercentage >= 50 -> "Halfway through! Steady consistent steps build strong habits."
                    chapterPercentage > 0 -> "Great start! Consistent daily reading fuels the soul."
                    else -> "Tap the checkmarks beside verses as you read to track your spiritual journey!"
                }
                Text(
                    text = motivationalText,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    color = TextSecondary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // ✨ Live Bible API Controller Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, if (useApiBibleText) GoldPrimary.copy(alpha = 0.25f) else Color(0x11FFFFFF)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .testTag("bible_api_controller_card")
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = if (useApiBibleText) Icons.Default.CloudQueue else Icons.Default.CloudOff,
                            contentDescription = "Bible API Icon",
                            tint = if (useApiBibleText) GoldPrimary else TextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Live Bible API Fetcher",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Fetch actual scripture text dynamically from bible-api.com",
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                    Switch(
                        checked = useApiBibleText,
                        onCheckedChange = { viewModel.toggleUseApiBibleText(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DeepBlack,
                            checkedTrackColor = GoldPrimary,
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = SurfaceDark
                        ),
                        modifier = Modifier.testTag("use_api_bible_switch")
                    )
                }

                if (useApiBibleText) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0x11FFFFFF))
                    )

                    when {
                        isOfflineMode -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CloudOff,
                                    contentDescription = "Offline Mode Warning",
                                    tint = Color(0xFFE57373),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Offline Mode Active: Paused API fetching. Using local fallback.",
                                    color = Color(0xFFE57373),
                                    fontSize = 11.sp
                                )
                            }
                        }
                        apiBibleTextLoading -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                CircularProgressIndicator(
                                    color = GoldPrimary,
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                                Text(
                                    text = "Fetching $selectedBook Chapter $selectedChapterNum ($selectedTranslation)...",
                                    color = GoldPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        apiBibleTextError != null -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Error",
                                        tint = Color(0xFFEF5350),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = apiBibleTextError ?: "An error occurred during API fetch.",
                                        color = Color(0xFFEF5350),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Showing local fallback text. Tap 'Retry' to attempt refetching.",
                                    color = TextMuted,
                                    fontSize = 10.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Button(
                                    onClick = { viewModel.fetchApiBibleChapter(selectedBook, selectedChapterNum, selectedTranslation) },
                                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(28.dp).testTag("bible_api_retry_btn"),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                                ) {
                                    Text("Retry Connection", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        else -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Success",
                                        tint = Color(0xFF81C784),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Live $selectedTranslation Content Active",
                                        color = Color(0xFF81C784),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                TextButton(
                                    onClick = { viewModel.fetchApiBibleChapter(selectedBook, selectedChapterNum, selectedTranslation) },
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.height(24.dp).testTag("bible_api_refresh_btn")
                                ) {
                                    Text("Refresh", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Scripture reading card
        Card(
            shape = if (isFocusMode) RoundedCornerShape(0.dp) else RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = if (isFocusMode) DeepBlack else SurfaceDark),
            border = if (isFocusMode) null else BorderStroke(1.dp, Color(0x11FFFFFF)),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (useApiBibleText && apiBibleTextLoading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CircularProgressIndicator(color = GoldPrimary, modifier = Modifier.size(36.dp))
                            Text(
                                text = "Loading Scripture Text...",
                                color = GoldPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Connecting securely to the dynamic Bible API...",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                } else {
                    val versesToDisplay = if (selectedVerseNum == "All") {
                        currentChapterVerses
                    } else {
                        currentChapterVerses.filter { it.substringBefore(" ").trim() == selectedVerseNum }
                    }

                    items(versesToDisplay) { verse ->
                        val verseNum = verse.substringBefore(" ").trim()
                        val verseText = verse.substringAfter(" ").trim()
                        val verseKey = "$selectedChapter:$verseNum"

                        val isBookmarked = bookmarks.contains(verseKey)
                        val highlightColorHex = highlights[verseKey]
                        val highlightColor = if (highlightColorHex != null) Color(highlightColorHex) else Color.Transparent

                        val isSelected = selectedVerseKey == verseKey || (selectedVerseNum != "All" && selectedVerseNum == verseNum)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = if (isSelected) Color(0xFF1E1E1E) else highlightColor.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    selectedVerseKey = if (isSelected && selectedVerseNum == "All") null else verseKey
                                }
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val isVerseRead = readVerses.contains("$selectedBook $selectedChapterNum:$verseNum")
                            IconButton(
                                onClick = {
                                    viewModel.toggleVerseRead(selectedBook, selectedChapterNum, verseNum, currentChapterVerses)
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 4.dp)
                            ) {
                                Icon(
                                    imageVector = if (isVerseRead) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = "Toggle Read Status",
                                    tint = if (isVerseRead) GoldPrimary else Color.White.copy(alpha = 0.3f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            if (isBookmarked) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Bookmarked",
                                    tint = GoldPrimary,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(top = 4.dp, end = 4.dp)
                                )
                            }
                            Text(
                                text = verse,
                                color = if (isSelected) Color.White else TextSecondary,
                                fontSize = bibleFontSize.sp,
                                fontFamily = bibleFontFamily,
                                lineHeight = (bibleFontSize + 7).sp,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Expanded Verse Action Menu
                        AnimatedVisibility(visible = isSelected) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Highlight options
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Color:", color = TextMuted, fontSize = 10.sp)
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .background(Color(0xFFFFD700), CircleShape)
                                                .clickable { viewModel.toggleHighlight(verseKey, 0xFFFFD700) }
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .background(Color(0xFF0288D1), CircleShape)
                                                .clickable { viewModel.toggleHighlight(verseKey, 0xFF0288D1) }
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .background(Color(0xFF388E3C), CircleShape)
                                                .clickable { viewModel.toggleHighlight(verseKey, 0xFF388E3C) }
                                        )
                                        Icon(
                                            imageVector = Icons.Default.FormatPaint,
                                            contentDescription = "Clear Highlight",
                                            tint = Color.White.copy(alpha = 0.5f),
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clickable { viewModel.toggleHighlight(verseKey, highlights[verseKey] ?: 0L) }
                                        )
                                    }

                                    // Bookmark and AI options
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                val cleanText = verseText.ifBlank { verse }
                                                tts?.speak(cleanText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "IndividualVerseTTS")
                                                viewModel.showToast("Reading: $selectedBook $selectedChapterNum:$verseNum")
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = "Speak Verse",
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        IconButton(
                                            onClick = { viewModel.toggleBookmark(verseKey) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                                contentDescription = "Bookmark",
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        val shareContext = LocalContext.current
                                        IconButton(
                                            onClick = {
                                                val cleanText = verseText.ifBlank { verse }
                                                val shareText = "\"$cleanText\" — $selectedBook $selectedChapterNum:$verseNum ($selectedTranslation)"
                                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                                    type = "text/plain"
                                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                                }
                                                shareContext.startActivity(Intent.createChooser(shareIntent, "Share Scripture"))
                                                viewModel.showToast("Sharing verse...")
                                            },
                                            modifier = Modifier.size(24.dp).testTag("share_verse_button")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = "Share Verse",
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Button(
                                            onClick = {
                                                if (isPro) {
                                                    viewModel.showToast("Opening AI Commentary...")
                                                } else {
                                                    showProDialog = true
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isPro) GoldPrimary.copy(alpha = 0.2f) else Color(0xFF222222),
                                                contentColor = GoldPrimary
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                            modifier = Modifier.height(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = if (isPro) "AI Notes" else "AI Study (PRO)",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                // Interactive AI Commentary section if unlocked (Pro)
                                if (isPro) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Card(
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF141414)),
                                        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = null,
                                                    tint = GoldPrimary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Lumina AI Exegesis",
                                                    color = GoldPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = getTheologicalCommentary(verseKey, verseText),
                                                color = Color.White.copy(alpha = 0.85f),
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                }

                                // User Personal Study Notes Section
                                Spacer(modifier = Modifier.height(8.dp))
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161616)),
                                    border = BorderStroke(1.dp, Color(0x22FFFFFF)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Edit,
                                                    contentDescription = null,
                                                    tint = GoldPrimary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Personal Study Notes",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        
                                        var noteText by remember(verseKey) { mutableStateOf(studyNotes[verseKey] ?: "") }
                                        var isEditing by remember(verseKey) { mutableStateOf(false) }

                                        if (isEditing) {
                                            OutlinedTextField(
                                                value = noteText,
                                                onValueChange = { noteText = it },
                                                placeholder = { Text("Write your reflections, prayers, or cross-references...", color = TextMuted, fontSize = 11.sp) },
                                                trailingIcon = {
                                                    IconButton(onClick = {
                                                        voiceNoteTargetText = noteText
                                                        voiceNoteOnApply = { resultText ->
                                                            noteText = resultText
                                                        }
                                                        showVoiceNoteOverlay = true
                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Default.Mic,
                                                            contentDescription = "Voice dictation",
                                                            tint = GoldPrimary,
                                                            modifier = Modifier.size(18.dp)
                                                        )
                                                    }
                                                },
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = GoldPrimary,
                                                    unfocusedBorderColor = BorderDark,
                                                    focusedTextColor = Color.White,
                                                    unfocusedTextColor = Color.White,
                                                    cursorColor = GoldPrimary
                                                ),
                                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                                                modifier = Modifier.fillMaxWidth(),
                                                maxLines = 4,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(
                                                horizontalArrangement = Arrangement.End,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                TextButton(
                                                    onClick = { isEditing = false },
                                                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.6f))
                                                ) {
                                                    Text("Cancel", fontSize = 10.sp)
                                                }
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Button(
                                                    onClick = {
                                                        viewModel.saveStudyNote(verseKey, noteText)
                                                        isEditing = false
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                                                    shape = RoundedCornerShape(6.dp),
                                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                                    modifier = Modifier.height(28.dp)
                                                ) {
                                                    Text("Save Note", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        } else {
                                            if (noteText.isNotEmpty()) {
                                                Text(
                                                    text = noteText,
                                                    color = Color.White.copy(alpha = 0.9f),
                                                    fontSize = 12.sp,
                                                    fontStyle = FontStyle.Italic,
                                                    modifier = Modifier.padding(bottom = 6.dp)
                                                )
                                                Row(
                                                    horizontalArrangement = Arrangement.End,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(
                                                        text = "Edit Note",
                                                        color = GoldPrimary,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.clickable { isEditing = true }
                                                    )
                                                    Spacer(modifier = Modifier.width(12.dp))
                                                    Text(
                                                        text = "Delete",
                                                        color = Color.Red.copy(alpha = 0.8f),
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.clickable {
                                                            viewModel.saveStudyNote(verseKey, "")
                                                            noteText = ""
                                                        }
                                                    )
                                                }
                                            } else {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(
                                                        text = "No personal reflections added yet.",
                                                        color = TextMuted,
                                                        fontSize = 11.sp
                                                    )
                                                    Text(
                                                        text = "+ Add Reflections",
                                                        color = GoldPrimary,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.clickable { isEditing = true }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                }
            }
        }
    } else if (activeTab == "Devotional") {
        ScriptureDevotionalTab(viewModel = viewModel)
    } else if (activeTab == "Prayers") {
        ScripturePrayersTab(viewModel = viewModel)
    } else if (activeTab == "Discover") {
        ScriptureDiscoverTab(
                viewModel = viewModel,
                onNavigateToReadTab = { book, chapter, verse ->
                    selectedBook = book
                    selectedChapterNum = chapter
                    selectedVerseNum = verse
                    activeTab = "Read"
                },
                tts = tts,
                isTtsInitialized = isTtsInitialized,
                activeDailyVerse = activeDailyVerse,
                dailyVerses = dailyVerses
            )
        } else if (activeTab == "Badges") {
            ScriptureBadgesTab(
                unlockedBadges = unlockedBadges,
                readVersesCount = readVerses.size,
                completedChaptersCount = completedChapters.size,
                bookmarksCount = bookmarks.size,
                highlightsCount = highlights.size
            )
        } else if (activeTab == "Quiz") {
            ScriptureQuizTab(viewModel = viewModel, tts = tts)
        } else if (activeTab == "Notes") {
            ScriptureNotesTab(
                viewModel = viewModel,
                onNavigateToReadTab = { book, chapter, verse ->
                    selectedBook = book
                    selectedChapterNum = chapter
                    selectedVerseNum = verse
                    activeTab = "Read"
                },
                onTriggerVoiceNote = { initialText, onApply ->
                    voiceNoteTargetText = initialText
                    voiceNoteOnApply = onApply
                    showVoiceNoteOverlay = true
                }
            )
        } else if (activeTab == "Plans") {
            ScriptureReadingPlansTab(
                viewModel = viewModel,
                onNavigateToReadTab = { book, chapter ->
                    selectedBook = book
                    selectedChapterNum = chapter
                    selectedVerseNum = "All"
                    activeTab = "Read"
                }
            )
        } else if (activeTab == "Dictionary") {
            BibleDictionaryTab(viewModel = viewModel)
        }
    }

    if (showVoiceNoteOverlay) {
        VoiceNoteDictationDialog(
            initialText = voiceNoteTargetText,
            onDismiss = { showVoiceNoteOverlay = false },
            onApply = { voiceNoteOnApply(it) },
            viewModel = viewModel
        )
    }
}

@Composable
fun VoiceNoteDictationDialog(
    initialText: String,
    onDismiss: () -> Unit,
    onApply: (String) -> Unit,
    viewModel: LuminaViewModel
) {
    var speechText by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    val waveformState = remember { mutableStateListOf<Float>() }

    // Waveform simulation
    LaunchedEffect(isListening) {
        if (isListening) {
            waveformState.clear()
            for (i in 0..12) { waveformState.add(0.2f + (0.8f * Math.random().toFloat())) }
            while (isListening) {
                kotlinx.coroutines.delay(120)
                for (i in 0 until waveformState.size) {
                    waveformState[i] = 0.15f + (0.85f * Math.random().toFloat())
                }
            }
        } else {
            waveformState.clear()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = GoldPrimary)
                Text(
                    text = "Voice Note Dictation",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Speak your reflections, study insights, or prayers. Lumina converts your spoken words into text instantly.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )

                // Recording visualizer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0xFF141414), RoundedCornerShape(12.dp))
                        .border(BorderStroke(1.dp, if (isListening) GoldPrimary.copy(alpha = 0.4f) else BorderDark), RoundedCornerShape(12.dp))
                        .clickable { isListening = !isListening },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(if (isListening) GoldPrimary.copy(alpha = 0.15f) else Color(0xFF2C2C2C), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isListening) Icons.Default.Hearing else Icons.Default.Mic,
                                contentDescription = null,
                                tint = if (isListening) GoldPrimary else Color.White.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        if (isListening) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                waveformState.forEach { heightMultiplier ->
                                    Box(
                                        modifier = Modifier
                                            .width(3.dp)
                                            .height(24.dp * heightMultiplier)
                                            .background(GoldPrimary, RoundedCornerShape(1.dp))
                                    )
                                }
                            }
                        } else {
                            Text("Tap to Start Listening", color = TextMuted, fontSize = 11.sp)
                        }
                    }
                }

                // Spoken Words Text Field
                OutlinedTextField(
                    value = speechText,
                    onValueChange = { speechText = it },
                    placeholder = { Text("Your spoken words will appear here...", color = TextMuted, fontSize = 11.sp) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.White),
                    modifier = Modifier.fillMaxWidth().height(90.dp).testTag("voice_note_transcript_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedContainerColor = DeepBlack,
                        unfocusedContainerColor = DeepBlack
                    )
                )

                // Spiritual/Theological reflection phrases to tap for mock dictation
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Or choose a spiritual insight prompt to speak:",
                        color = GoldPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    val reflectionPrompts = listOf(
                        "This verse highlights the immense love and faithfulness of God in difficult seasons.",
                        "A powerful reminder to seek wisdom and understanding above earthly wealth.",
                        "Let us walk in the Spirit and reflect God's grace in our daily interactions.",
                        "Trusting in the Lord with all my heart, leaning not on my own understanding."
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        reflectionPrompts.forEach { prompt ->
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
                                    .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(12.dp))
                                    .clickable {
                                        speechText = prompt
                                        viewModel.showToast("Voice prompt selected: '$prompt'")
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(text = prompt.take(30) + "...", color = Color.White, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (speechText.isNotBlank()) {
                        val merged = if (initialText.isBlank()) speechText else "$initialText\n$speechText"
                        onApply(merged)
                        viewModel.showToast("Voice-to-Text Note applied successfully!")
                    }
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("apply_voice_note_btn")
            ) {
                Text("Apply to Note", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.6f))
            ) {
                Text("Cancel")
            }
        },
        containerColor = SurfaceDark,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ScriptureNotesTab(
    viewModel: LuminaViewModel,
    onNavigateToReadTab: (String, Int, String) -> Unit,
    onTriggerVoiceNote: (String, (String) -> Unit) -> Unit
) {
    val studyNotes by viewModel.studyNotes.collectAsStateWithLifecycle()
    val dbVerseHighlights by viewModel.dbVerseHighlights.collectAsStateWithLifecycle()
    var editingNoteKey by remember { mutableStateOf<String?>(null) }
    var editingNoteText by remember { mutableStateOf("") }
    
    var subTabMode by remember { mutableStateOf("Notes") } // "Notes" or "Highlights"
    
    // Highlight states
    var highlightColorFilter by remember { mutableStateOf("All") } // "All", "Gold", "Blue", "Green"
    var highlightSearchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Modern Tab Selector for Notes vs Highlights
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .background(Color(0xFF141414), RoundedCornerShape(10.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            listOf("Notes" to "My Journal Notes", "Highlights" to "My Highlights").forEach { (id, label) ->
                val isSelected = subTabMode == id
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) GoldPrimary.copy(alpha = 0.4f) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { subTabMode = id }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (id == "Notes") Icons.Default.Edit else Icons.Default.Palette,
                            contentDescription = null,
                            tint = if (isSelected) GoldPrimary else Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = label,
                            color = if (isSelected) GoldPrimary else Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (subTabMode == "Notes") {
            // NOTES TAB CONTENT
            if (studyNotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = GoldPrimary.copy(alpha = 0.4f),
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Your Study Journal is empty.",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Tap on any verse in the Scripture reader to add personal study notes, cross-references, or reflection journals.",
                            color = TextMuted,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Text(
                            text = "MY STUDY JOURNAL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                        )
                    }
                    items(studyNotes.toList()) { (verseKey, noteText) ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            border = BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Book,
                                            contentDescription = null,
                                            tint = GoldPrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = verseKey,
                                            color = GoldPrimary,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                val parts = verseKey.split(" ")
                                                if (parts.size >= 2) {
                                                    val bookName = parts.subList(0, parts.size - 1).joinToString(" ")
                                                    val chAndV = parts.last().split(":")
                                                    if (chAndV.size == 2) {
                                                        val ch = chAndV[0].toIntOrNull() ?: 1
                                                        val vNum = chAndV[1]
                                                        onNavigateToReadTab(bookName, ch, vNum)
                                                    }
                                                }
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = "Read Verse",
                                                tint = Color.White.copy(alpha = 0.7f),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                editingNoteKey = verseKey
                                                editingNoteText = noteText
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit Note",
                                                tint = Color.White.copy(alpha = 0.7f),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                viewModel.saveStudyNote(verseKey, "")
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete Note",
                                                tint = Color.Red.copy(alpha = 0.7f),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))

                                if (editingNoteKey == verseKey) {
                                    OutlinedTextField(
                                        value = editingNoteText,
                                        onValueChange = { editingNoteText = it },
                                        placeholder = { Text("Write your reflections...", color = TextMuted) },
                                        trailingIcon = {
                                            IconButton(onClick = {
                                                onTriggerVoiceNote(editingNoteText) { spokenText ->
                                                    editingNoteText = spokenText
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Mic,
                                                    contentDescription = "Voice dictation",
                                                    tint = GoldPrimary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = GoldPrimary,
                                            unfocusedBorderColor = BorderDark,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White,
                                            cursorColor = GoldPrimary
                                        ),
                                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = 4,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TextButton(onClick = { editingNoteKey = null }) {
                                            Text("Cancel", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(
                                            onClick = {
                                                viewModel.saveStudyNote(verseKey, editingNoteText)
                                                editingNoteKey = null
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                                            shape = RoundedCornerShape(6.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                            modifier = Modifier.height(30.dp)
                                        ) {
                                            Text("Save", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                } else {
                                    Text(
                                        text = noteText,
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 13.sp,
                                        fontStyle = FontStyle.Italic,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // HIGHLIGHTS TAB CONTENT
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Search box
                OutlinedTextField(
                    value = highlightSearchQuery,
                    onValueChange = { highlightSearchQuery = it },
                    placeholder = { Text("Search highlighted verses...", color = TextMuted) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextMuted) },
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedContainerColor = SurfaceDark,
                        unfocusedContainerColor = SurfaceDark
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Color Filter Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Filter:", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    val filters = listOf(
                        "All" to Color.White,
                        "Gold" to Color(0xFFFFD700),
                        "Blue" to Color(0xFF0288D1),
                        "Green" to Color(0xFF388E3C)
                    )
                    filters.forEach { (label, color) ->
                        val isSelected = highlightColorFilter == label
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) color.copy(alpha = 0.25f) else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) color else BorderDark,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { highlightColorFilter = label }
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (label != "All") {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(color, CircleShape)
                                    )
                                }
                                Text(
                                    text = label,
                                    color = if (isSelected) Color.White else TextMuted,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Map & Filter highlighted items
                val filteredHighlights = dbVerseHighlights.filter { entity ->
                    val text = getHighlightText(entity.book, entity.chapter, entity.verse)
                    val reference = "${entity.book} ${entity.chapter}:${entity.verse}"
                    val matchesQuery = reference.contains(highlightSearchQuery, ignoreCase = true) ||
                            text.contains(highlightSearchQuery, ignoreCase = true)

                    val matchesColor = when (highlightColorFilter) {
                        "All" -> true
                        "Gold" -> entity.colorHex.contains("D700", ignoreCase = true) || entity.colorHex.contains("D7", ignoreCase = true)
                        "Blue" -> entity.colorHex.contains("0288", ignoreCase = true) || entity.colorHex.contains("88", ignoreCase = true)
                        "Green" -> entity.colorHex.contains("388E", ignoreCase = true) || entity.colorHex.contains("38", ignoreCase = true)
                        else -> true
                    }
                    matchesQuery && matchesColor
                }

                if (filteredHighlights.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = GoldPrimary.copy(alpha = 0.4f),
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = "No matching highlights found.",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Go to the Scripture reader, select any verse, and choose a color to highlight important spiritual insights.",
                                color = TextMuted,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(filteredHighlights) { highlight ->
                            val colorHexVal = highlight.colorHex
                            val highlightColor = try {
                                Color(android.graphics.Color.parseColor(colorHexVal))
                            } catch (e: Exception) {
                                GoldPrimary
                            }

                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                                border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(12.dp)
                                                    .background(highlightColor, CircleShape)
                                            )
                                            Text(
                                                text = "${highlight.book} ${highlight.chapter}:${highlight.verse}",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp
                                            )
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    onNavigateToReadTab(highlight.book, highlight.chapter, highlight.verse.toString())
                                                },
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowForward,
                                                    contentDescription = "Read",
                                                    tint = GoldPrimary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }

                                            IconButton(
                                                onClick = {
                                                    viewModel.removeHighlight(highlight.book, highlight.chapter, highlight.verse)
                                                },
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Clear Highlight",
                                                    tint = Color.Red.copy(alpha = 0.8f),
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = getHighlightText(highlight.book, highlight.chapter, highlight.verse),
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp,
                                        fontStyle = FontStyle.Italic,
                                        modifier = Modifier
                                            .background(highlightColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                            .padding(8.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScriptureDiscoverTab(
    viewModel: LuminaViewModel,
    onNavigateToReadTab: (String, Int, String) -> Unit,
    tts: android.speech.tts.TextToSpeech?,
    isTtsInitialized: Boolean,
    activeDailyVerse: Triple<String, String, String>,
    dailyVerses: List<Triple<String, String, String>> = emptyList()
) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val isOfflineMode by viewModel.isOfflineMode.collectAsStateWithLifecycle()
    val cachedBooks by viewModel.cachedBooks.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Daily Verse of the Day Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Daily",
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "VERSE OF THE DAY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Today",
                        fontSize = 10.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "\"${activeDailyVerse.second}\"",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "— ${activeDailyVerse.first} (ESV)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    modifier = Modifier.align(Alignment.End).padding(bottom = 12.dp)
                )

                HorizontalDivider(color = Color(0xFF222222), thickness = 1.dp, modifier = Modifier.padding(bottom = 12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Jump to Passage
                    Button(
                        onClick = {
                            val parts = activeDailyVerse.first.split(" ")
                            val book = activeDailyVerse.third
                            val ref = parts.last()
                            val chapter = ref.substringBefore(":").toIntOrNull() ?: 1
                            val verse = ref.substringAfter(":")
                            onNavigateToReadTab(book, chapter, verse)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Book, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Jump to Passage", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    // Secondary action row
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Narrate using TTS
                        IconButton(
                            onClick = {
                                if (isTtsInitialized && tts != null) {
                                    viewModel.trackTtsPlayback()
                                    tts.speak(activeDailyVerse.second, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "DailyVerseTTS")
                                    viewModel.showToast("Speaking Daily Verse...")
                                } else {
                                    viewModel.showToast("Speech Engine is initializing...")
                                }
                            },
                            modifier = Modifier.size(32.dp).background(Color(0xFF222222), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White, modifier = Modifier.size(14.dp))
                        }

                        // Bookmark
                        val isBookmarked = bookmarks.contains(activeDailyVerse.first)
                        IconButton(
                            onClick = { viewModel.toggleBookmark(activeDailyVerse.first) },
                            modifier = Modifier.size(32.dp).background(Color(0xFF222222), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = GoldPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        // Share/Copy
                        IconButton(
                            onClick = {
                                clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("${activeDailyVerse.second} — ${activeDailyVerse.first}"))
                                viewModel.showToast("Verse copied to clipboard!")
                            },
                            modifier = Modifier.size(32.dp).background(Color(0xFF222222), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White, modifier = Modifier.size(14.dp))
                        }

                        val dailyContext = LocalContext.current
                        IconButton(
                            onClick = {
                                val shareText = "\"${activeDailyVerse.second}\" — ${activeDailyVerse.first} (ESV)"
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                dailyContext.startActivity(Intent.createChooser(shareIntent, "Share Daily Verse"))
                                viewModel.showToast("Sharing Daily Verse...")
                            },
                            modifier = Modifier.size(32.dp).background(Color(0xFF222222), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // 1b. Daily Scriptures Collection
        Text(
            text = "DAILY SCRIPTURES FOR REFLECTION",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GoldPrimary,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            dailyVerses.forEach { verse ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(1.dp, BorderDark),
                    modifier = Modifier.width(280.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = verse.first,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            val isBookmarked = bookmarks.contains(verse.first)
                            IconButton(
                                onClick = { viewModel.toggleBookmark(verse.first) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "\"${verse.second}\"",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = TextSecondary,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp,
                            modifier = Modifier.height(64.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = {
                                    val parts = verse.first.split(" ")
                                    val book = verse.third
                                    val ref = parts.last()
                                    val chapter = ref.substringBefore(":").toIntOrNull() ?: 1
                                    val vNum = ref.substringAfter(":")
                                    onNavigateToReadTab(book, chapter, vNum)
                                },
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Read Passage", fontSize = 11.sp, color = GoldPrimary, fontWeight = FontWeight.Bold)
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = {
                                        if (isTtsInitialized && tts != null) {
                                            viewModel.trackTtsPlayback()
                                            tts.speak(verse.second, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "CollectionVerseTTS")
                                            viewModel.showToast("Speaking ${verse.first}...")
                                        } else {
                                            viewModel.showToast("Speech engine is initializing...")
                                        }
                                    },
                                    modifier = Modifier.size(28.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White, modifier = Modifier.size(12.dp))
                                }

                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("${verse.second} — ${verse.first}"))
                                        viewModel.showToast("Verse copied!")
                                    },
                                    modifier = Modifier.size(28.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Offline Reading Cache Manager Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, BorderDark),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CloudOff,
                            contentDescription = "Offline Cache",
                            tint = Color(0xFF0288D1),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "OFFLINE READING CACHE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0288D1),
                            letterSpacing = 1.sp
                        )
                    }

                    // Offline Mode Toggle Switch
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Offline Mode", fontSize = 10.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Switch(
                            checked = isOfflineMode,
                            onCheckedChange = { viewModel.toggleOfflineMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF0288D1),
                                checkedTrackColor = Color(0xFF0288D1).copy(alpha = 0.3f),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color(0xFF222222)
                            ),
                            modifier = Modifier.scale(0.7f).height(24.dp)
                        )
                    }
                }

                Text(
                    text = "Keep your favorite books of scripture downloaded on your local device. Fully readable even with zero internet network connection.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Cache Stats Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F0F0F), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("LOCAL CACHE STORAGE", fontSize = 9.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                        Text("${cachedBooks.size * 3.1} MB total cached", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("CACHED BOOKS", fontSize = 9.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                        Text("${cachedBooks.size} books offline ready", fontSize = 12.sp, color = Color(0xFF0288D1), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable cached books horizontal list
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val quickBooks = listOf("Genesis", "Proverbs", "Psalms", "John", "Revelation")
                    quickBooks.forEach { book ->
                        val isCached = cachedBooks.contains(book)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = if (isCached) Color(0xFF01579B).copy(alpha = 0.15f) else Color(0xFF1E1E1E),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isCached) Color(0xFF0288D1) else BorderDark
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.cacheBook(book)
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = book,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCached) Color(0xFF0288D1) else Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (isCached) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color(0xFF0288D1), modifier = Modifier.size(10.dp))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text("Saved", fontSize = 8.sp, color = Color(0xFF0288D1))
                                    }
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(10.dp))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text("Cache", fontSize = 8.sp, color = TextSecondary)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Keyword Search Card with Search History
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, BorderDark),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SCRIPTURE KEYWORD SEARCH",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                    }
                    if (searchHistory.isNotEmpty()) {
                        Text(
                            text = "Clear History",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red.copy(alpha = 0.8f),
                            modifier = Modifier.clickable { viewModel.clearSearchHistory() }
                        )
                    }
                }

                // Search Row input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.searchScripture(it)
                    },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = {
                                searchQuery = ""
                                viewModel.searchScripture("")
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = Color.White)
                            }
                        }
                    },
                    placeholder = { Text("Search keywords (e.g. faith, love, covenant)...", color = TextSecondary, fontSize = 12.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = BorderDark,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = GoldPrimary
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                )

                // Recent Searches History
                if (searchHistory.isNotEmpty()) {
                    Text(
                        text = "RECENT SEARCHES",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Display top 4 queries
                        searchHistory.take(4).forEach { history ->
                            Row(
                                modifier = Modifier
                                    .background(Color(0xFF222222), RoundedCornerShape(16.dp))
                                    .clickable {
                                        searchQuery = history.query
                                        viewModel.searchScripture(history.query)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = history.query, color = Color.White, fontSize = 10.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Delete",
                                    tint = TextSecondary,
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clickable { viewModel.deleteRecentSearch(history.query) }
                                )
                            }
                        }
                    }
                }

                // Results list
                if (searchQuery.isNotBlank()) {
                    Text(
                        text = "SEARCH RESULTS (${searchResults.size})",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (searchResults.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No scriptures found matching \"$searchQuery\"", color = TextSecondary, fontSize = 11.sp)
                        }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.heightIn(max = 240.dp).verticalScroll(rememberScrollState())
                        ) {
                            searchResults.take(20).forEach { result ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF121212), RoundedCornerShape(8.dp))
                                        .border(BorderStroke(1.dp, Color(0xFF222222)), RoundedCornerShape(8.dp))
                                        .clickable {
                                            onNavigateToReadTab(result.book, result.chapter, result.verseNum)
                                        }
                                        .padding(10.dp)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${result.book} ${result.chapter}:${result.verseNum}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldPrimary
                                        )
                                        Icon(imageVector = Icons.Default.OpenInNew, contentDescription = "View", tint = TextSecondary, modifier = Modifier.size(12.dp))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = result.text,
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.85f),
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScriptureBadgesTab(
    unlockedBadges: List<UnlockedBadgeEntity>,
    readVersesCount: Int,
    completedChaptersCount: Int,
    bookmarksCount: Int,
    highlightsCount: Int
) {
    val allBadges = remember {
        listOf(
            Triple("first_spark", "First Spark", "Marked your first scripture verse as read!"),
            Triple("word_explorer", "Word Explorer", "Marked 10 scripture verses as read!"),
            Triple("daily_light", "Daily Light", "Completed your first scripture chapter!"),
            Triple("scripture_scribe", "Scripture Scribe", "Bookmarked 3 verses for deeper reflection!"),
            Triple("flash_of_light", "Flash of Light", "Highlighted 5 scriptures for study mapping!"),
            Triple("proclaimer", "Proclaimer", "Listened to scripture audio narration!"),
            Triple("circle_sync", "Circle Sync", "Shared and synced your active reading focus to a study circle!"),
            Triple("deep_scholar", "Deep Scholar", "Completed 5 scripture chapters!"),
            Triple("faithful_seeker", "Faithful Seeker", "Searched the scripture database 3 times to find truth!"),
            Triple("prayer_warrior", "Prayer Warrior", "Logged your first prayer request in your journal!"),
            Triple("answered_testimony", "Answered Testimony", "Marked a prayer request as answered in praise!"),
            Triple("devotional_student", "Devotional Student", "Completed a daily scripture devotional!"),
            Triple("streak_3_days", "3-Day Fire", "Maintained a 3-day spiritual study streak!"),
            Triple("streak_7_days", "7-Day Beacon", "Maintained a 7-day spiritual study streak!")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Stats Summary Bar
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, BorderDark),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "SPIRITUAL PROGRESS DASHBOARD",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val stats = listOf(
                        Triple(readVersesCount, "Verses Read", GoldPrimary),
                        Triple(completedChaptersCount, "Chapters Completed", Color(0xFF0288D1)),
                        Triple(bookmarksCount, "Bookmarked", Color(0xFFE040FB)),
                        Triple(highlightsCount, "Highlights", Color(0xFF4CAF50))
                    )
                    stats.forEach { (count, label, color) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFF0F0F0F), RoundedCornerShape(8.dp))
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = count.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = label, fontSize = 9.sp, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        }
                    }
                }
            }
        }

        // Badges Grid
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, BorderDark),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "MY ENGAGEMENT BADGES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    allBadges.forEach { (id, title, desc) ->
                        val isUnlocked = unlockedBadges.any { badge -> badge.id == id }
                        val unlockInfo = unlockedBadges.firstOrNull { badge -> badge.id == id }

                        val icon = when (id) {
                            "first_spark" -> Icons.Default.WbSunny
                            "word_explorer" -> Icons.Default.Explore
                            "daily_light" -> Icons.Default.LightMode
                            "scripture_scribe" -> Icons.Default.Bookmark
                            "flash_of_light" -> Icons.Default.Highlight
                            "proclaimer" -> Icons.Default.VolumeUp
                            "circle_sync" -> Icons.Default.Sync
                            "faithful_seeker" -> Icons.Default.Search
                            "prayer_warrior" -> Icons.Default.PostAdd
                            "answered_testimony" -> Icons.Default.ThumbUp
                            "devotional_student" -> Icons.Default.Favorite
                            "streak_3_days" -> Icons.Default.Whatshot
                            "streak_7_days" -> Icons.Default.Whatshot
                            else -> Icons.Default.School
                        }

                        val badgeColor = if (isUnlocked) GoldPrimary else Color.White.copy(alpha = 0.3f)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = if (isUnlocked) GoldPrimary.copy(alpha = 0.05f) else Color(0xFF141414),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isUnlocked) GoldPrimary.copy(alpha = 0.3f) else Color(0xFF222222)
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Badge Icon container
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(
                                        color = if (isUnlocked) GoldPrimary.copy(alpha = 0.15f) else Color(0xFF222222),
                                        shape = CircleShape
                                    )
                                    .border(
                                        border = BorderStroke(1.dp, if (isUnlocked) GoldPrimary else BorderDark),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = badgeColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isUnlocked) Color.White else TextSecondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = desc,
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    lineHeight = 15.sp
                                )
                                if (isUnlocked && unlockInfo != null) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Unlocked ✓",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperHubScreen(viewModel: LuminaViewModel, onMenuClick: (() -> Unit)? = null) {
    var selectedSection by remember { mutableStateOf("Stack") }
    val sections = listOf("Stack", "DB Schema", "API Docs", "Boilerplate")

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val rateLimitedRequestsCount by viewModel.rateLimitedRequestsCount.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 6.dp)
        ) {
            if (onMenuClick != null) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = GoldPrimary)
                }
            }
            Text(
                text = "Lumina API & Architecture Hub",
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "A ready-to-publish Google Play development repository",
            color = GoldPrimary,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // API rate limiter sandbox
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "SANDBOX: API Rate Limiter Simulator",
                    color = GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "External APIs are limited to 5 requests per minute. Test the gateway rate limiting directly below:",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Requests Sent: $rateLimitedRequestsCount / 5",
                            color = if (rateLimitedRequestsCount >= 5) Color.Red else Color.Green,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Resetting rate counters dynamically", fontSize = 9.sp, color = TextMuted)
                    }

                    Button(
                        onClick = { viewModel.triggerSimulatedApiCall() },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text("Simulate API Request", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Horizontal Tabs selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            sections.forEach { sect ->
                val isSelected = sect == selectedSection
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = if (isSelected) GoldPrimary else SurfaceDark,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            border = BorderStroke(1.dp, if (isSelected) Color.Transparent else BorderDark),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedSection = sect }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = sect,
                        color = if (isSelected) DeepBlack else Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Large Code Container with COPY functionality
        val displayText = when (selectedSection) {
            "Stack" -> DeveloperHubContent.TECH_STACK_DESC
            "DB Schema" -> DeveloperHubContent.DATABASE_SCHEMA_SQL
            "API Docs" -> DeveloperHubContent.API_DOCUMENTATION
            else -> DeveloperHubContent.BACKEND_BOILERPLATE_JS
        }

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            border = BorderStroke(1.dp, Color(0x11FFFFFF)),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        Text(
                            text = displayText,
                            color = if (selectedSection == "Stack" || selectedSection == "API Docs") TextSecondary else Color(0xFFA5D6A7), // Green tone for code
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            lineHeight = 18.sp
                        )
                    }
                }

                // Copy Floating Button
                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(displayText))
                        Toast.makeText(context, "$selectedSection content copied to clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = GoldPrimary)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: LuminaViewModel, onMenuClick: (() -> Unit)? = null) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val blockedUsers by viewModel.blockedUsers.collectAsStateWithLifecycle()
    val isPro by viewModel.isProUser.collectAsStateWithLifecycle()
    val readVerses by viewModel.readVerses.collectAsStateWithLifecycle()
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()

    var displayName by remember(user) { mutableStateOf(user?.displayName ?: "") }
    var bio by remember(user) { mutableStateOf(user?.bio ?: "") }
    var location by remember(user) { mutableStateOf(user?.location ?: "") }
    var translation by remember(user) { mutableStateOf(user?.preferredTranslation ?: "ESV") }
    var dateOfBirth by remember(user) { mutableStateOf(user?.dateOfBirth ?: "") }
    var stateOfOrigin by remember(user) { mutableStateOf(user?.stateOfOrigin ?: "") }
    var country by remember(user) { mutableStateOf(user?.country ?: "") }
    var phoneNumber by remember(user) { mutableStateOf(user?.phoneNumber ?: "") }
    var phoneCountryCode by remember(user) { mutableStateOf(user?.phoneCountryCode ?: "+1") }
    var showProDialogInProfile by remember { mutableStateOf(false) }

    val activeSpeed by viewModel.animationSpeed.collectAsStateWithLifecycle()
    val activeEffect by viewModel.sidebarTransitionType.collectAsStateWithLifecycle()
    val isDarkTheme by viewModel.isDarkThemeEnabled.collectAsStateWithLifecycle()

    if (showProDialogInProfile) {
        ProUpgradeDialog(
            onDismiss = { showProDialogInProfile = false },
            onUpgradeSuccess = {
                viewModel.upgradeToPro()
                showProDialogInProfile = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile & Settings", color = Color.White, fontFamily = FontFamily.Serif) },
                navigationIcon = {
                    if (onMenuClick != null) {
                        IconButton(onClick = onMenuClick) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Navigation Drawer", tint = GoldPrimary)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // PRO Subscription Card / Banner
            if (isPro) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x22C9A063)),
                    border = BorderStroke(1.5.dp, GoldPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = DeepBlack)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Lumina Pro Unlocked", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Enjoy full exegesis, premium translations, and real-time audio narrator.", color = TextSecondary, fontSize = 11.sp)
                        }
                        OutlinedButton(
                            onClick = { viewModel.downgradeToStandard() },
                            border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red.copy(alpha = 0.8f)),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text("Standard", fontSize = 10.sp)
                        }
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(1.dp, Color(0x22FFFFFF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color(0xFF222222), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldPrimary)
                            }
                            Column {
                                Text("Lumina Pro Companion", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("Unlock Greek/Hebrew concordances, audio read-aloud & AI guides.", color = TextMuted, fontSize = 11.sp)
                            }
                        }
                        
                        Button(
                            onClick = { showProDialogInProfile = true },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {
                            Text("Upgrade to Pro - $4.99/mo", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            // User Avatar Header
            user?.let { u ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceDark, RoundedCornerShape(20.dp))
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(u.avatarColor)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = u.displayName.take(2).uppercase(),
                            color = DeepBlack,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = u.displayName, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            if (isPro) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(GoldPrimary, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("PRO", color = DeepBlack, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Text(text = u.email, color = TextMuted, fontSize = 12.sp)
                    }
                }
            }

            // ------------------ USER PROFILES MANAGER ------------------
            val allUsers by viewModel.allUsers.collectAsStateWithLifecycle()
            var showAddProfileDialog by remember { mutableStateOf(false) }

            if (showAddProfileDialog) {
                var newName by remember { mutableStateOf("") }
                var newEmail by remember { mutableStateOf("") }
                var newBio by remember { mutableStateOf("") }
                var newLoc by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showAddProfileDialog = false },
                    title = { Text("Create New Profile", color = GoldPrimary, fontFamily = FontFamily.Serif) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newName,
                                onValueChange = { newName = it },
                                label = { Text("Name") },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, focusedLabelColor = GoldPrimary, focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                            OutlinedTextField(
                                value = newEmail,
                                onValueChange = { newEmail = it },
                                label = { Text("Email") },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, focusedLabelColor = GoldPrimary, focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                            OutlinedTextField(
                                value = newBio,
                                onValueChange = { newBio = it },
                                label = { Text("Bio") },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, focusedLabelColor = GoldPrimary, focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                            OutlinedTextField(
                                value = newLoc,
                                onValueChange = { newLoc = it },
                                label = { Text("Location") },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, focusedLabelColor = GoldPrimary, focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (newName.isNotBlank() && newEmail.isNotBlank()) {
                                    viewModel.quickCreateProfile(newName, newEmail, newBio, newLoc)
                                    showAddProfileDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack)
                        ) {
                            Text("Create & Switch")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddProfileDialog = false }) {
                            Text("Cancel", color = Color.White)
                        }
                    },
                    containerColor = SurfaceDark
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().testTag("profile_hub_card")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PROFILE HUBS & ACCOUNTS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                        IconButton(
                            onClick = { showAddProfileDialog = true },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Profile", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        }
                    }

                    Text(
                        text = "Switch between profiles locally. Each profile preserves separate study records and streaks.",
                        color = TextMuted,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        allUsers.forEach { profile ->
                            val isActive = profile.email == user?.email
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isActive) GoldPrimary.copy(alpha = 0.1f) else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isActive) GoldPrimary.copy(alpha = 0.5f) else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        if (!isActive) {
                                            viewModel.switchProfile(profile)
                                        }
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color(profile.avatarColor), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = profile.displayName.take(1).uppercase(),
                                            color = DeepBlack,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = profile.displayName,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = profile.email,
                                            color = TextMuted,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                                if (isActive) {
                                    Box(
                                        modifier = Modifier
                                            .background(GoldPrimary, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("ACTIVE", color = DeepBlack, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.SwapHoriz,
                                        contentDescription = "Switch",
                                        tint = TextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ----------------------------------------------------
            // Custom Study Reminders Section (Room Persistence)
            // ----------------------------------------------------
            val studyReminders by viewModel.studyReminders.collectAsStateWithLifecycle()
            var showAddReminderDialog by remember { mutableStateOf(false) }
            var reminderTitle by remember { mutableStateOf("") }
            var reminderTime by remember { mutableStateOf("08:00 AM") }
            val reminderDays = remember { mutableStateListOf("Mon", "Wed", "Fri") }

            if (showAddReminderDialog) {
                AlertDialog(
                    onDismissRequest = { showAddReminderDialog = false },
                    title = {
                        Text(
                            text = "Add Study Reminder",
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp
                        )
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Configure your schedule to stay consistent in Scripture study and build your streak.",
                                color = TextMuted,
                                fontSize = 11.sp
                            )

                            OutlinedTextField(
                                value = reminderTitle,
                                onValueChange = { reminderTitle = it },
                                label = { Text("Reminder Title / Study Plan") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldPrimary,
                                    focusedLabelColor = GoldPrimary,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("reminder_title_input")
                            )

                            OutlinedTextField(
                                value = reminderTime,
                                onValueChange = { reminderTime = it },
                                label = { Text("Time (e.g. 08:30 AM, 09:00 PM)") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldPrimary,
                                    focusedLabelColor = GoldPrimary,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("reminder_time_input")
                            )

                            Text(
                                "Study Days:",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                                    val isSelected = reminderDays.contains(day)
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                color = if (isSelected) GoldPrimary else SurfaceDark,
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .clickable {
                                                if (isSelected) {
                                                    reminderDays.remove(day)
                                                } else {
                                                    reminderDays.add(day)
                                                }
                                            }
                                            .padding(vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day.take(1),
                                            color = if (isSelected) DeepBlack else Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (reminderTitle.isNotBlank()) {
                                    val daysCsv = if (reminderDays.isEmpty()) "Daily" else reminderDays.joinToString(", ")
                                    viewModel.addStudyReminder(reminderTitle, reminderTime, daysCsv)
                                    reminderTitle = ""
                                    showAddReminderDialog = false
                                } else {
                                    viewModel.showToast("Please provide a title!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            modifier = Modifier.testTag("reminder_save_button")
                        ) {
                            Text("Save Reminder")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddReminderDialog = false }) {
                            Text("Cancel", color = Color.White)
                        }
                    },
                    containerColor = SurfaceDark
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().testTag("study_reminders_card")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Default.Alarm,
                                contentDescription = "Study Reminders",
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "PERSISTENT STUDY REMINDERS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary,
                                letterSpacing = 1.sp
                            )
                        }
                        IconButton(
                            onClick = { showAddReminderDialog = true },
                            modifier = Modifier.size(24.dp).testTag("add_reminder_fab")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Study Reminder",
                                tint = GoldPrimary,
                                modifier = Modifier.size(16.dp)
                             )
                        }
                    }

                    Text(
                        text = "Establish automatic alerts for your specific times and days. All schedules are saved locally using high-performance Room databases.",
                        color = TextMuted,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (studyReminders.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF141414), RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No active reminders set. Tap '+' to schedule one!",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            studyReminders.forEach { reminder ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF161616), RoundedCornerShape(10.dp))
                                        .border(BorderStroke(1.dp, Color(0x11FFFFFF)), RoundedCornerShape(10.dp))
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = if (reminder.isEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                                            contentDescription = null,
                                            tint = if (reminder.isEnabled) GoldPrimary else TextMuted,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Column {
                                            Text(
                                                text = reminder.title,
                                                color = if (reminder.isEnabled) Color.White else TextMuted,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "${reminder.timeString} • ${reminder.daysCsv}",
                                                color = TextSecondary,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        // Quick Simulate Alert
                                        IconButton(
                                            onClick = { viewModel.triggerSimulatedStudyReminder(reminder) },
                                            modifier = Modifier.size(28.dp).testTag("simulate_reminder_${reminder.id}")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "Simulate",
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }

                                        // Toggle Active switch
                                        Switch(
                                            checked = reminder.isEnabled,
                                            onCheckedChange = { viewModel.toggleStudyReminderEnabled(reminder) },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = DeepBlack,
                                                checkedTrackColor = GoldPrimary,
                                                uncheckedThumbColor = TextMuted,
                                                uncheckedTrackColor = SurfaceDark
                                            ),
                                            modifier = Modifier.scale(0.7f).testTag("toggle_reminder_${reminder.id}")
                                        )

                                        // Delete button
                                        IconButton(
                                            onClick = { viewModel.deleteStudyReminder(reminder.id) },
                                            modifier = Modifier.size(28.dp).testTag("delete_reminder_${reminder.id}")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete Reminder",
                                                tint = Color(0xFFEF5350),
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------------------------------------
            // Custom Elegant Study Themes Selector Card
            // ----------------------------------------------------
            val currentTheme by viewModel.currentStudyTheme.collectAsStateWithLifecycle()
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().testTag("study_themes_card")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Study Themes",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "DESIGN STUDY THEMES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "Customize your scripture reading and reflection experience with eye-friendly, beautiful palettes curated for sacred studies.",
                        color = TextMuted,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val themes = listOf(
                            Triple(com.example.ui.StudyTheme.COSMIC_DARK, "Cosmic Dark", "Sophisticated deep black and glorious gold"),
                            Triple(com.example.ui.StudyTheme.PARCHMENT_WARM, "Parchment Warm", "Traditional sepia canvas and bronze brown typography"),
                            Triple(com.example.ui.StudyTheme.OLIVE_FOREST, "Olive Forest", "Serene dark sage green for deep tranquil reflection"),
                            Triple(com.example.ui.StudyTheme.IVORY_LIGHT, "Ivory Light", "Pristine light mode with soft indigo accents")
                        )

                        themes.forEach { (themeId, title, desc) ->
                            val isSelected = currentTheme == themeId
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else Color(0xFF161616),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = if (isSelected) GoldPrimary else Color(0x11FFFFFF)
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable { viewModel.setStudyTheme(themeId) }
                                    .padding(12.dp)
                                    .testTag("theme_option_${themeId.name.lowercase()}"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Visual color circle preview
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .background(
                                                color = when (themeId) {
                                                    com.example.ui.StudyTheme.COSMIC_DARK -> DeepBlack
                                                    com.example.ui.StudyTheme.PARCHMENT_WARM -> Color(0xFFFAF4EB)
                                                    com.example.ui.StudyTheme.OLIVE_FOREST -> Color(0xFF0F1511)
                                                    com.example.ui.StudyTheme.IVORY_LIGHT -> Color(0xFFFCFAF7)
                                                },
                                                shape = CircleShape
                                            )
                                            .border(
                                                1.dp,
                                                when (themeId) {
                                                    com.example.ui.StudyTheme.COSMIC_DARK -> GoldPrimary
                                                    com.example.ui.StudyTheme.PARCHMENT_WARM -> Color(0xFF8B4513)
                                                    com.example.ui.StudyTheme.OLIVE_FOREST -> Color(0xFF8DA38B)
                                                    com.example.ui.StudyTheme.IVORY_LIGHT -> Color(0xFF5C6BC0)
                                                },
                                                CircleShape
                                            )
                                    )

                                    Column {
                                        Text(
                                            text = title,
                                            color = if (isSelected) GoldPrimary else Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = desc,
                                            color = TextSecondary,
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected Theme",
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Unlocked Badges Section
            val unlockedBadges by viewModel.unlockedBadges.collectAsStateWithLifecycle()
            if (unlockedBadges.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "UNLOCKED BADGES (${unlockedBadges.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            unlockedBadges.forEach { badge ->
                                val icon = when (badge.id) {
                                    "first_spark" -> Icons.Default.WbSunny
                                    "word_explorer" -> Icons.Default.Explore
                                    "daily_light" -> Icons.Default.LightMode
                                    "scripture_scribe" -> Icons.Default.Bookmark
                                    "flash_of_light" -> Icons.Default.Highlight
                                    "proclaimer" -> Icons.Default.VolumeUp
                                    "circle_sync" -> Icons.Default.Sync
                                    else -> Icons.Default.School
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .background(Color(0xFF161616), RoundedCornerShape(10.dp))
                                        .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)), RoundedCornerShape(10.dp))
                                        .padding(8.dp)
                                        .width(72.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(GoldPrimary.copy(alpha = 0.15f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = icon, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = badge.title,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Edit Inputs
            OutlinedTextField(
                value = displayName,
                onValueChange = { displayName = it },
                label = { Text("Display Name") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Profile Bio") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Extended profile fields for Lumina member directories
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = { Text("Date of Birth (YYYY-MM-DD)") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("profile_dob_input")
            )

            OutlinedTextField(
                value = stateOfOrigin,
                onValueChange = { stateOfOrigin = it },
                label = { Text("State of Origin") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("profile_state_origin_input")
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("Country") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = BorderDark,
                    focusedLabelColor = GoldPrimary,
                    unfocusedLabelColor = TextMuted
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("profile_country_input")
            )

            Column {
                Text("Phone Number", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var expandedCC by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .background(SurfaceDark, RoundedCornerShape(8.dp))
                            .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                            .clickable { expandedCC = true }
                            .padding(horizontal = 12.dp, vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(phoneCountryCode, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = GoldPrimary)
                        }
                        DropdownMenu(
                            expanded = expandedCC,
                            onDismissRequest = { expandedCC = false },
                            modifier = Modifier.background(SurfaceDark)
                        ) {
                            val countryCodes = listOf(
                                "+1" to "US/CA",
                                "+234" to "Nigeria",
                                "+44" to "UK",
                                "+27" to "South Africa",
                                "+91" to "India",
                                "+233" to "Ghana",
                                "+49" to "Germany",
                                "+61" to "Australia",
                                "+33" to "France"
                            )
                            countryCodes.forEach { (code, name) ->
                                DropdownMenuItem(
                                    text = { Text("$code ($name)", color = Color.White) },
                                    onClick = {
                                        phoneCountryCode = code
                                        expandedCC = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = BorderDark,
                            focusedLabelColor = GoldPrimary,
                            unfocusedLabelColor = TextMuted
                        ),
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("profile_phone_input")
                    )
                }
            }

            // Preferred translation selector
            Column {
                Text("Preferred Bible Translation", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("ESV", "KJV", "NIV", "NASB").forEach { trans ->
                        val isSelected = translation == trans
                        val isLocked = (trans == "NIV" || trans == "NASB") && !isPro
                        
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = if (isSelected) GoldPrimary else SurfaceDark,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    if (isLocked) {
                                        showProDialogInProfile = true
                                    } else {
                                        translation = trans
                                    }
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = trans,
                                    color = if (isSelected) DeepBlack else Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                if (isLocked) {
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked Translation",
                                        tint = GoldPrimary.copy(alpha = 0.8f),
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // App Interface Language
            Column {
                Text(viewModel.translate("choose_lang"), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val languages = listOf(
                        "en" to "english",
                        "es" to "spanish",
                        "fr" to "french",
                        "pt" to "portuguese",
                        "tl" to "tagalog"
                    )
                    languages.forEach { (langCode, langKey) ->
                        val isSelected = (user?.languageCode ?: "en") == langCode
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) GoldPrimary else SurfaceDark,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) GoldPrimary else BorderDark,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.changeLanguage(langCode)
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                                .testTag("lang_selector_$langCode"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = viewModel.translate(langKey),
                                color = if (isSelected) DeepBlack else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { 
                    viewModel.updateProfile(
                        displayName = displayName, 
                        bio = bio, 
                        location = location, 
                        preferredTranslation = translation,
                        dateOfBirth = dateOfBirth,
                        stateOfOrigin = stateOfOrigin,
                        country = country,
                        phoneNumber = phoneNumber,
                        phoneCountryCode = phoneCountryCode
                    ) 
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("update_profile_button")
            ) {
                Text("Save Profile Changes", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lumina Customization Engine & Animation Live-Testing Tool (Animation Tool)
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth().testTag("customization_engine_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Animation, contentDescription = null, tint = GoldPrimary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Lumina Customization Studio", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                    
                    Text("Personalize your study experience by configuring navigation transition effects and dark theme rendering. Preview settings instantly!", color = TextMuted, fontSize = 11.sp)
                    
                    Divider(color = BorderDark)
                    
                    // Dark Theme switch (Enable Dark Theme requirement)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("High-Contrast Dark Theme", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Provides deep blacks & eye-safe study contrast", color = TextMuted, fontSize = 10.sp)
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { viewModel.toggleDarkTheme(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldPrimary,
                                checkedTrackColor = GoldPrimary.copy(alpha = 0.4f),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }

                    Divider(color = BorderDark)

                    // Speed selector
                    Text("Transition Speed: $activeSpeed ms", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(150 to "Swift", 350 to "Smooth", 750 to "Ambient").forEach { (speed, label) ->
                            val isSel = activeSpeed == speed
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        color = if (isSel) GoldPrimary else Color(0xFF181818),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { viewModel.setAnimationSpeed(speed) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$label",
                                    color = if (isSel) DeepBlack else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Transition Type Selector
                    Text("Active Sidebar Transition Effect", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Slide & Fade", "Scale & Pop", "Bounce Accent").forEach { effect ->
                            val isSel = activeEffect == effect
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        color = if (isSel) GoldPrimary else Color(0xFF181818),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { viewModel.setSidebarTransitionType(effect) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = effect,
                                    color = if (isSel) DeepBlack else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Interactive preview button
                    Button(
                        onClick = {
                            viewModel.showToast("Applied and simulated '$activeEffect' transition!")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222), contentColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(38.dp)
                    ) {
                        Text("Preview & Apply Transition Settings", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Divider(color = BorderDark, modifier = Modifier.padding(vertical = 8.dp))

            // 1. Personalized Study Goals & Progress Card
            val studyGoalVerses by viewModel.studyGoalVerses.collectAsStateWithLifecycle()
            val studyGoalChapters by viewModel.studyGoalChapters.collectAsStateWithLifecycle()
            val studyGoalMinutes by viewModel.studyGoalMinutes.collectAsStateWithLifecycle()
            val sessionTimeSeconds by viewModel.sessionTimeSeconds.collectAsStateWithLifecycle()
            val readVersesCount = readVerses.size
            val completedChaptersCount = completedChapters.size

            var tempVersesGoal by remember(studyGoalVerses) { mutableStateOf(studyGoalVerses) }
            var tempChaptersGoal by remember(studyGoalChapters) { mutableStateOf(studyGoalChapters) }
            var tempMinutesGoal by remember(studyGoalMinutes) { mutableStateOf(studyGoalMinutes) }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().testTag("study_goals_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Personalized Study Goals", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }

                    Text("Define daily milestone targets to hold yourself accountable on your spiritual reading journey.", color = TextMuted, fontSize = 11.sp)

                    // Target adjusters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Verses Target
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Verses Goal", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { if (tempVersesGoal > 1) tempVersesGoal-- },
                                    modifier = Modifier.size(24.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Decrease", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                                Text("$tempVersesGoal", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { tempVersesGoal++ },
                                    modifier = Modifier.size(24.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Increase", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                            }
                        }

                        // Chapters Target
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Chapters Goal", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { if (tempChaptersGoal > 1) tempChaptersGoal-- },
                                    modifier = Modifier.size(24.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Decrease", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                                Text("$tempChaptersGoal", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { tempChaptersGoal++ },
                                    modifier = Modifier.size(24.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Increase", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                            }
                        }

                        // Minutes Target
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Minutes Goal", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { if (tempMinutesGoal > 5) tempMinutesGoal -= 5 },
                                    modifier = Modifier.size(24.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Decrease", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                                Text("$tempMinutesGoal", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { tempMinutesGoal += 5 },
                                    modifier = Modifier.size(24.dp).background(Color(0xFF222222), CircleShape)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Increase", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                            }
                        }
                    }

                    if (tempVersesGoal != studyGoalVerses || tempChaptersGoal != studyGoalChapters || tempMinutesGoal != studyGoalMinutes) {
                        Button(
                            onClick = { viewModel.updateStudyGoals(tempVersesGoal, tempChaptersGoal, tempMinutesGoal) },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(36.dp)
                        ) {
                            Text("Save Study Goal Targets", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Divider(color = BorderDark)

                    // LIVE ACCOUNTABILITY TRACKER
                    Text("LIVE PROGRESS TODAY", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)

                    val sessionMinutes = sessionTimeSeconds / 60
                    val sessionSecondsRemaining = sessionTimeSeconds % 60
                    val timeGoalProgress = if (tempMinutesGoal > 0) (sessionMinutes.toFloat() / tempMinutesGoal.toFloat()).coerceIn(0f, 1f) else 0f
                    val verseGoalProgress = if (tempVersesGoal > 0) (readVersesCount.toFloat() / tempVersesGoal.toFloat()).coerceIn(0f, 1f) else 0f
                    val chapterGoalProgress = if (tempChaptersGoal > 0) (completedChaptersCount.toFloat() / tempChaptersGoal.toFloat()).coerceIn(0f, 1f) else 0f

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Study Timer row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Daily Study Timer", color = Color.White, fontSize = 12.sp)
                            }
                            Text(
                                String.format("%02d:%02d / %d:00 min", sessionMinutes, sessionSecondsRemaining, tempMinutesGoal),
                                color = if (sessionMinutes >= tempMinutesGoal) GoldPrimary else Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        LinearProgressIndicator(
                            progress = { timeGoalProgress },
                            color = GoldPrimary,
                            trackColor = Color(0xFF1E1E1E),
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )

                        // Verses read progress row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Verses Studied", color = Color.White, fontSize = 12.sp)
                            }
                            Text(
                                "$readVersesCount / $tempVersesGoal read",
                                color = if (readVersesCount >= tempVersesGoal) GoldPrimary else Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        LinearProgressIndicator(
                            progress = { verseGoalProgress },
                            color = Color(0xFF4CAF50),
                            trackColor = Color(0xFF1E1E1E),
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )

                        // Chapters completed progress row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Chapters Completed", color = Color.White, fontSize = 12.sp)
                            }
                            Text(
                                "$completedChaptersCount / $tempChaptersGoal done",
                                color = if (completedChaptersCount >= tempChaptersGoal) GoldPrimary else Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        LinearProgressIndicator(
                            progress = { chapterGoalProgress },
                            color = Color(0xFF0288D1),
                            trackColor = Color(0xFF1E1E1E),
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )
                    }
                }
            }

            Divider(color = BorderDark, modifier = Modifier.padding(vertical = 8.dp))

            // 2. Cross-Device Sync Card
            val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()
            val lastSyncTime by viewModel.lastSyncTime.collectAsStateWithLifecycle()
            val isAutoSyncEnabled by viewModel.isAutoSyncEnabled.collectAsStateWithLifecycle()
            val syncStatusMessage by viewModel.syncStatusMessage.collectAsStateWithLifecycle()

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth().testTag("sync_manager_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Secure Cloud Sync", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        
                        // Sync status tag
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSyncing) GoldPrimary.copy(alpha = 0.2f) else Color(0xFF1E1E1E),
                                    RoundedCornerShape(6.dp)
                                )
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (isSyncing) GoldPrimary else BorderDark
                                    ),
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (isSyncing) "Syncing..." else "Synced",
                                color = if (isSyncing) GoldPrimary else Color(0xFF4CAF50),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text("Synchronize your study highlights, custom notes, badges, and reading focus across all of your personal devices automatically.", color = TextMuted, fontSize = 11.sp)

                    // Auto-sync setting row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Automatic Syncing", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Silent updates in background", color = TextMuted, fontSize = 10.sp)
                        }
                        Switch(
                            checked = isAutoSyncEnabled,
                            onCheckedChange = { viewModel.toggleAutoSync() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldPrimary,
                                checkedTrackColor = GoldPrimary.copy(alpha = 0.4f),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }

                    Divider(color = BorderDark)

                    // Sync stats and manually trigger sync
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("SYNC STATE", fontSize = 9.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                            Text(syncStatusMessage, fontSize = 11.sp, color = Color.White)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (lastSyncTime != null) "Last synced: ${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date(lastSyncTime!!))}" else "Last synced: Never",
                                fontSize = 9.sp,
                                color = TextMuted
                            )
                        }

                        Button(
                            onClick = { viewModel.triggerSync() },
                            enabled = !isSyncing,
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            if (isSyncing) {
                                CircularProgressIndicator(
                                    color = DeepBlack,
                                    modifier = Modifier.size(12.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(imageVector = Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Sync Now", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Divider(color = BorderDark, modifier = Modifier.padding(vertical = 8.dp))

            // 3. Export Study Notes Card
            val studyNotes by viewModel.studyNotes.collectAsStateWithLifecycle()
            var showNotesPreview by remember { mutableStateOf(false) }
            val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth().testTag("export_notes_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Export Study Notes", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }

                    Text("Compile and download all of your highlighted reflections and study journal notes formatted beautifully in Markdown.", color = TextMuted, fontSize = 11.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Preview notes toggle button
                        OutlinedButton(
                            onClick = { showNotesPreview = !showNotesPreview },
                            border = BorderStroke(1.dp, BorderDark),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(36.dp)
                        ) {
                            Text(if (showNotesPreview) "Hide Preview" else "Preview Notes", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        // Copy / export button
                        Button(
                            onClick = {
                                val markdown = viewModel.exportNotesAsMarkdown()
                                clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(markdown))
                                viewModel.showToast("Copied notes as Markdown to clipboard!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(36.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Export Markdown", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (showNotesPreview) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 180.dp)
                                .background(Color(0xFF0F0F0F), RoundedCornerShape(8.dp))
                                .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(8.dp))
                                .verticalScroll(rememberScrollState())
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "MARKDOWN EXPORT PREVIEW",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = viewModel.exportNotesAsMarkdown(),
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            Divider(color = BorderDark, modifier = Modifier.padding(vertical = 8.dp))

            // 4. Room-backed Study Notes Viewer Card
            val dbStudyNotes by viewModel.dbStudyNotes.collectAsStateWithLifecycle()
            var showNotesList by remember { mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth().testTag("saved_notes_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.EditNote, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Your Saved Notes", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Box(
                            modifier = Modifier
                                .background(GoldPrimary.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                                .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("${dbStudyNotes.size} Notes", color = GoldPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Text("View and manage all notes you've pinned to verses during your study sessions.", color = TextMuted, fontSize = 11.sp)

                    Button(
                        onClick = { showNotesList = !showNotesList },
                        colors = ButtonDefaults.buttonColors(containerColor = if (showNotesList) Color(0xFF2E2E2E) else GoldPrimary, contentColor = if (showNotesList) Color.White else DeepBlack),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text(if (showNotesList) "Collapse Notes" else "Expand Notes", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    if (showNotesList) {
                        if (dbStudyNotes.isEmpty()) {
                            Text("No study notes saved yet. Write some in the Scripture tab!", color = TextMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                        } else {
                            Column(
                                modifier = Modifier.padding(top = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                dbStudyNotes.forEach { note ->
                                    Card(
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = CardDark),
                                        border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "${note.book} ${note.chapter}:${note.verseRange}",
                                                    color = GoldPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp
                                                )
                                                IconButton(
                                                    onClick = {
                                                        viewModel.saveStudyNote("${note.book} ${note.chapter}:${note.verseRange}", "")
                                                    },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete note", tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(14.dp))
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(text = note.noteText, color = Color.White, fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 5. Room-backed Group Notification Switch Card
            val groupNotificationsEnabled by viewModel.groupNotificationsEnabled.collectAsStateWithLifecycle()

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth().testTag("notifications_pref_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = if (groupNotificationsEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Group Study Notifications", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Switch(
                            checked = groupNotificationsEnabled,
                            onCheckedChange = { viewModel.toggleGroupNotifications(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DeepBlack,
                                checkedTrackColor = GoldPrimary,
                                uncheckedThumbColor = TextMuted,
                                uncheckedTrackColor = SurfaceDark
                            ),
                            modifier = Modifier.testTag("group_notifications_pref_switch")
                        )
                    }
                    Text("Turn group alerts on/off. When enabled, you will periodically receive notifications when study circles post updates or go live.", color = TextMuted, fontSize = 11.sp)
                }
            }

            Divider(color = BorderDark, modifier = Modifier.padding(vertical = 8.dp))

            // Moderation Block List Overview (UGC requirement)
            Text("Blocked Study Members", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            if (blockedUsers.isEmpty()) {
                Text("You have not blocked any users.", color = TextMuted, fontSize = 12.sp)
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    blockedUsers.forEach { b ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceDark, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = b.blockedUsername, color = Color.White, fontSize = 13.sp)
                            Text(
                                text = "Unblock",
                                color = GoldPrimary,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable {
                                    // In production, unblock API. In Room:
                                    viewModel.showToast("Unblocked ${b.blockedUsername}")
                                }
                            )
                        }
                    }
                }
            }

            Divider(color = BorderDark, modifier = Modifier.padding(vertical = 8.dp))

            // Google Play Safety & Policies
            Text("Legal & Policies", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.showToast("Terms of Service: Lumina study is a respectful global platform.") },
                    border = BorderStroke(1.dp, BorderDark),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Terms of Service", color = Color.White, fontSize = 11.sp)
                }

                OutlinedButton(
                    onClick = { viewModel.showToast("Privacy Policy: Your data is secure and never sold.") },
                    border = BorderStroke(1.dp, BorderDark),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Privacy Policy", color = Color.White, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Data Portability Section
            var importJsonText by remember { mutableStateOf("") }
            val currentUserState by viewModel.currentUser.collectAsStateWithLifecycle()

            Text("Data Portability", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().testTag("backup_portability_card")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Import & Export Configuration",
                        color = GoldPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Transfer your progress, level, XP, bio, and study goals easily between devices using Lumina's standard JSON configuration format.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                currentUserState?.let { user ->
                                    val json = """{"name":"${user.displayName}","bio":"${user.bio}","location":"${user.location}","level":${user.level},"xp":${user.xp},"versesGoal":$studyGoalVerses,"chaptersGoal":$studyGoalChapters,"minutesGoal":$studyGoalMinutes}"""
                                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(json))
                                    viewModel.showToast("Configuration copied to clipboard!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).testTag("export_config_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(14.dp))
                                Text("Export", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Button(
                            onClick = {
                                if (importJsonText.isBlank()) {
                                    viewModel.showToast("Please paste your configuration first!")
                                    return@Button
                                }
                                try {
                                    // Parse JSON manually and robustly
                                    val cleaned = importJsonText.replace(" ", "").replace("\"", "").replace("\n", "").replace("{", "").replace("}", "")
                                    val pairs = cleaned.split(",").associate {
                                        val parts = it.split(":")
                                        parts[0] to parts[1]
                                    }
                                    val name = pairs["name"] ?: "Lumina Scholar"
                                    val bio = pairs["bio"] ?: ""
                                    val loc = pairs["location"] ?: ""
                                    val level = pairs["level"]?.toIntOrNull() ?: 1
                                    val xp = pairs["xp"]?.toIntOrNull() ?: 0
                                    val vGoal = pairs["versesGoal"]?.toIntOrNull() ?: 5
                                    val cGoal = pairs["chaptersGoal"]?.toIntOrNull() ?: 1
                                    val mGoal = pairs["minutesGoal"]?.toIntOrNull() ?: 15

                                    viewModel.importUserData(
                                        displayName = name,
                                        bio = bio,
                                        location = loc,
                                        level = level,
                                        xp = xp,
                                        versesGoal = vGoal,
                                        chaptersGoal = cGoal,
                                        minutesGoal = mGoal
                                    )
                                    importJsonText = ""
                                } catch (e: Exception) {
                                    viewModel.showToast("Invalid configuration format. Please check and try again.")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E1E1E), contentColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFF2C2C2C)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).testTag("import_config_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(14.dp))
                                Text("Import & Sync", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = importJsonText,
                        onValueChange = { importJsonText = it },
                        label = { Text("Paste configuration JSON here", color = GoldPrimary, fontSize = 11.sp) },
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 11.sp),
                        modifier = Modifier.fillMaxWidth().height(65.dp).testTag("import_json_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = Color(0xFF2C2C2C),
                            focusedContainerColor = Color(0xFF141414),
                            unfocusedContainerColor = Color(0xFF141414)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Danger Zone - Account Deletion
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x15D32F2F)),
                border = BorderStroke(1.dp, Color(0xFFD32F2F)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Danger Zone", color = Color(0xFFEF5350), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Once you delete your account, your profile, active meetings, and messages are permanently erased from our databases.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.deleteAccount() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F), contentColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .testTag("delete_account_button")
                    ) {
                        Text("Permanently Delete Account", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            Button(
                onClick = { viewModel.logout() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222), contentColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Log Out", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ScriptureDevotionalTab(viewModel: LuminaViewModel) {
    val selectedId by viewModel.selectedDevotionalId.collectAsStateWithLifecycle()
    val completedIds by viewModel.completedDevotionalIds.collectAsStateWithLifecycle()
    val aiDevotional by viewModel.aiDevotional.collectAsStateWithLifecycle()
    val aiDevotionalLoading by viewModel.aiDevotionalLoading.collectAsStateWithLifecycle()
    val aiDevotionalError by viewModel.aiDevotionalError.collectAsStateWithLifecycle()
    val remindersEnabled by viewModel.devotionalRemindersEnabled.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isTtsPlaying by remember { mutableStateOf(false) }
    var tts by remember { mutableStateOf<android.speech.tts.TextToSpeech?>(null) }

    DisposableEffect(context) {
        val ttsInstance = android.speech.tts.TextToSpeech(context) { status -> }
        tts = ttsInstance
        onDispose {
            ttsInstance.stop()
            ttsInstance.shutdown()
        }
    }

    val activeDevotional = remember(selectedId, aiDevotional) {
        if (selectedId == 99) {
            aiDevotional ?: Devotional(
                id = 99,
                title = "AI Daily Devotional",
                scriptureRef = "Gemini AI",
                scriptureText = "Click generate below to fetch a customized, inspiring, and thought-provoking scripture reflection.",
                message = "Let the Holy Spirit guide you as the Gemini API creates an interactive message specifically for today's meditation. Connect your heart and mind with a tailored word.",
                reflection = "Press 'Generate' to see today's question.",
                actionItem = "Press 'Generate' to see today's action item."
            )
        } else {
            devotionalsList.firstOrNull { it.id == selectedId } ?: devotionalsList.first()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("devotional_tab_content"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        // Title banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1C15)),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = viewModel.translate("devotional_title"),
                        color = GoldPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fuel your spirit with daily wisdom, reflection, and action. Completing devotionals earns +40 XP and fuels your streak!",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Daily Devotional Reminders Config & Simulation
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, Color(0x11FFFFFF))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (remindersEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                                contentDescription = "Daily Reminder Icon",
                                tint = if (remindersEnabled) GoldPrimary else TextMuted,
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = "Daily Reading Reminders",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Get notified daily to read and stay on your streak",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                        Switch(
                            checked = remindersEnabled,
                            onCheckedChange = { viewModel.toggleDevotionalReminders(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DeepBlack,
                                checkedTrackColor = GoldPrimary,
                                uncheckedThumbColor = TextMuted,
                                uncheckedTrackColor = SurfaceDark
                            ),
                            modifier = Modifier.testTag("devotional_reminder_switch")
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0x11FFFFFF))
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Simulate Reminders",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Trigger a push notification to test the state flow",
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        }
                        Button(
                            onClick = { viewModel.triggerSimulatedDevotionalReminder() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (remindersEnabled) GoldPrimary.copy(alpha = 0.15f) else SurfaceDark,
                                contentColor = if (remindersEnabled) GoldPrimary else TextMuted
                            ),
                            border = BorderStroke(1.dp, if (remindersEnabled) GoldPrimary.copy(alpha = 0.4f) else Color(0x11FFFFFF)),
                            shape = RoundedCornerShape(8.dp),
                            enabled = remindersEnabled,
                            modifier = Modifier.height(34.dp).testTag("devotional_reminder_test_btn"),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = if (remindersEnabled) GoldPrimary else TextMuted,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Trigger Now",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Horizontal Step/Calendar selector for the 5 devotionals
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "SELECT DAY JOURNAL",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    devotionalsList.forEach { dev ->
                        val isSelected = dev.id == selectedId
                        val isCompleted = completedIds.contains(dev.id)

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = if (isSelected) GoldPrimary.copy(alpha = 0.25f) else if (isCompleted) Color(0x334CAF50) else SurfaceDark,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 1.5.dp,
                                    color = if (isSelected) GoldPrimary else if (isCompleted) Color(0xFF4CAF50) else Color(0xFF2C2C2C),
                                    shape = CircleShape
                                )
                                .clickable {
                                    viewModel.selectDevotional(dev.id)
                                    if (isTtsPlaying) {
                                        tts?.stop()
                                        isTtsPlaying = false
                                    }
                                }
                                .testTag("devotional_day_btn_${dev.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Day ${dev.id}",
                                    color = if (isSelected) GoldPrimary else if (isCompleted) Color(0xFF81C784) else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (isCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Completed",
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
                            }
                        }
                    }

                    // ✨ AI Devotional selector button
                    val isAiSelected = selectedId == 99
                    val isAiCompleted = completedIds.contains(99)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = if (isAiSelected) GoldPrimary.copy(alpha = 0.25f) else if (isAiCompleted) Color(0x334CAF50) else SurfaceDark,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.5.dp,
                                color = if (isAiSelected) GoldPrimary else if (isAiCompleted) Color(0xFF4CAF50) else Color(0xFF2C2C2C),
                                shape = CircleShape
                            )
                            .clickable {
                                viewModel.selectDevotional(99)
                                if (isTtsPlaying) {
                                    tts?.stop()
                                    isTtsPlaying = false
                                }
                            }
                            .testTag("devotional_day_btn_99"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "✨ AI",
                                color = if (isAiSelected) GoldPrimary else if (isAiCompleted) Color(0xFF81C784) else Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (isAiCompleted) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Completed",
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Devotional Details Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header & Audio
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedId == 99) "AI: ${activeDevotional.title}" else "DAY $selectedId: ${activeDevotional.title}",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.weight(1f)
                        )

                        // TTS button
                        IconButton(
                            onClick = {
                                isTtsPlaying = !isTtsPlaying
                                if (isTtsPlaying) {
                                    val speechText = "${activeDevotional.title}. Scripture: ${activeDevotional.scriptureRef}. ${activeDevotional.scriptureText}. Message: ${activeDevotional.message}"
                                    tts?.speak(speechText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "DevotionalTTS")
                                } else {
                                    tts?.stop()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isTtsPlaying) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                                contentDescription = "Listen Devotional",
                                tint = if (isTtsPlaying) GoldPrimary else Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (selectedId == 99) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF191712)),
                            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "Gemini",
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "GEMINI DEVOTIONAL ENGINE",
                                        color = GoldPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Ask Gemini to fetch a completely unique, thought-provoking scripture passage, custom message, and personal reflection prompt for today.",
                                    color = TextSecondary,
                                    fontSize = 11.sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                if (aiDevotionalLoading) {
                                    CircularProgressIndicator(
                                        color = GoldPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Connecting with Gemini...",
                                        color = GoldPrimary,
                                        fontSize = 11.sp
                                    )
                                } else {
                                    Button(
                                        onClick = { viewModel.fetchAiDevotional() },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.height(36.dp).testTag("generate_ai_devotional_btn")
                                    ) {
                                        Text(
                                            text = if (aiDevotional == null) "Generate Devotional" else "Generate New Devotional",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                aiDevotionalError?.let { err ->
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = err,
                                        color = Color(0xFFEF5350),
                                        fontSize = 11.sp,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Scripture Quote Block
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF141310), RoundedCornerShape(12.dp))
                            .border(BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f)), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.FormatQuote, contentDescription = "Quote", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                            Text(
                                text = activeDevotional.scriptureRef,
                                color = GoldPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "\"${activeDevotional.scriptureText}\"",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Message Text
                    Text(
                        text = activeDevotional.message,
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Reflection Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1A1A1A), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.Psychology, contentDescription = "Reflection", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                            Text(
                                text = viewModel.translate("reflection_prompt").uppercase(),
                                color = GoldPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeDevotional.reflection,
                            color = Color.White,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Item Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1A1A1A), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.DirectionsRun, contentDescription = "Action", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                            Text(
                                text = viewModel.translate("action_item").uppercase(),
                                color = GoldPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeDevotional.actionItem,
                            color = Color.White,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Completion state action
                    val isDevCompleted = completedIds.contains(selectedId)
                    Button(
                        onClick = { viewModel.completeDevotional(selectedId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("complete_devotional_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDevCompleted) Color(0xFF2E7D32) else GoldPrimary,
                            contentColor = if (isDevCompleted) Color.White else DeepBlack
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isDevCompleted
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isDevCompleted) Icons.Default.CheckCircle else Icons.Default.Book,
                                contentDescription = "Check",
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isDevCompleted) viewModel.translate("completed") else viewModel.translate("mark_completed"),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScripturePrayersTab(viewModel: LuminaViewModel) {
    val prayers by viewModel.allPrayers.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    var prayerTabMode by remember { mutableStateOf("Journal") } // "Journal" or "Wall"

    var prayerTitle by remember { mutableStateOf("") }
    var prayerRequestText by remember { mutableStateOf("") }
    var prayerCategory by remember { mutableStateOf("Faith") }
    var prayerIsPublic by remember { mutableStateOf(false) }
    var prayerAuthorName by remember { mutableStateOf("") }

    val activePrayers = prayers.filter { !it.isAnswered }
    val answeredPrayers = prayers.filter { it.isAnswered }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    text = viewModel.translate("new_prayer_title"),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Title Field
                    OutlinedTextField(
                        value = prayerTitle,
                        onValueChange = { prayerTitle = it },
                        label = { Text(viewModel.translate("title"), color = GoldPrimary) },
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth().testTag("prayer_title_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = Color(0xFF2C2C2C),
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark
                        )
                    )

                    // Request Text Field
                    OutlinedTextField(
                        value = prayerRequestText,
                        onValueChange = { prayerRequestText = it },
                        label = { Text(viewModel.translate("request"), color = GoldPrimary) },
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth().height(100.dp).testTag("prayer_request_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = Color(0xFF2C2C2C),
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark
                        )
                    )

                    // Category Selection Row
                    Column {
                        Text(
                            text = viewModel.translate("category"),
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val categories = listOf("Faith", "Health", "Family", "Wisdom", "Other")
                            categories.forEach { cat ->
                                val isCatSelected = prayerCategory == cat
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (isCatSelected) GoldPrimary.copy(alpha = 0.25f) else Color(0xFF1E1E1E),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (isCatSelected) GoldPrimary else Color(0xFF2C2C2C),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable { prayerCategory = cat }
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                        .testTag("prayer_cat_chip_$cat"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cat,
                                        color = if (isCatSelected) GoldPrimary else Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Public Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Post to Community Board", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Let others see and join in prayer with you", color = TextMuted, fontSize = 10.sp)
                        }
                        Switch(
                            checked = prayerIsPublic,
                            onCheckedChange = { prayerIsPublic = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldPrimary,
                                checkedTrackColor = GoldPrimary.copy(alpha = 0.4f),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            ),
                            modifier = Modifier.testTag("prayer_is_public_switch")
                        )
                    }

                    if (prayerIsPublic) {
                        OutlinedTextField(
                            value = prayerAuthorName,
                            onValueChange = { prayerAuthorName = it },
                            label = { Text("Author Name (Optional)", color = GoldPrimary) },
                            placeholder = { Text("Anonymous", color = TextMuted) },
                            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth().testTag("prayer_author_input"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = Color(0xFF2C2C2C),
                                focusedContainerColor = SurfaceDark,
                                unfocusedContainerColor = SurfaceDark
                            )
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addPrayer(
                            title = prayerTitle,
                            requestText = prayerRequestText,
                            category = prayerCategory,
                            isPublic = prayerIsPublic,
                            authorName = if (prayerAuthorName.isBlank()) "Anonymous" else prayerAuthorName
                        )
                        showAddDialog = false
                        prayerTitle = ""
                        prayerRequestText = ""
                        prayerCategory = "Faith"
                        prayerIsPublic = false
                        prayerAuthorName = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("save_prayer_btn")
                ) {
                    Text(viewModel.translate("save"), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAddDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.6f))
                ) {
                    Text(viewModel.translate("cancel"))
                }
            },
            containerColor = SurfaceDark,
            shape = RoundedCornerShape(16.dp)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("prayer_tab_content"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        // Stats Banner & Add Action Row
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1C15)),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (prayerTabMode == "Journal") viewModel.translate("prayer_journal") else "Community Prayer Wall",
                            color = GoldPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (prayerTabMode == "Journal") {
                                "${activePrayers.size} ${viewModel.translate("active")} • ${answeredPrayers.size} ${viewModel.translate("answered")}"
                            } else {
                                "${prayers.size} Community Prayer Requests Active"
                            },
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("add_prayer_fab_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp))
                            Text(viewModel.translate("add_prayer"), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Sub-tabs Selector
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF141414), RoundedCornerShape(10.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("Journal" to "My Journal", "Wall" to "Community Prayer Wall").forEach { (id, label) ->
                    val isSelected = prayerTabMode == id
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.4f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { prayerTabMode = id }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) GoldPrimary else Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (prayerTabMode == "Journal") {
            // Active Section Header
            if (activePrayers.isNotEmpty()) {
                item {
                    Text(
                        text = "${viewModel.translate("active").uppercase()} REQUESTS (${activePrayers.size})",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    )
                }

                items(activePrayers) { prayer ->
                    PrayerItemCard(
                        prayer = prayer,
                        viewModel = viewModel,
                        onToggleAnswered = { viewModel.togglePrayerAnswered(prayer) },
                        onDelete = { viewModel.deletePrayer(prayer.id) }
                    )
                }
            }

            // Answered Section Header
            if (answeredPrayers.isNotEmpty()) {
                item {
                    Text(
                        text = "${viewModel.translate("answered").uppercase()} PRAISES (${answeredPrayers.size})",
                        color = Color(0xFF81C784),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
                    )
                }

                items(answeredPrayers) { prayer ->
                    PrayerItemCard(
                        prayer = prayer,
                        viewModel = viewModel,
                        onToggleAnswered = { viewModel.togglePrayerAnswered(prayer) },
                        onDelete = { viewModel.deletePrayer(prayer.id) }
                    )
                }
            }

            // Empty State Placeholder
            if (prayers.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Prayer Journal",
                                tint = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "Your prayer journal is currently empty.\nPress 'Add Prayer' to start journaling requests and answered praises.",
                                color = TextMuted,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        } else {
            // COMMUNITY PRAYER WALL
            val publicPrayers = prayers.filter { it.isPublic }
            if (publicPrayers.isNotEmpty()) {
                items(publicPrayers) { prayer ->
                    PrayerWallItemCard(prayer = prayer, viewModel = viewModel)
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Global Prayer Wall",
                                tint = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "The Community Prayer Wall is currently empty.\nBe the first to share a prayer request to the wall!",
                                color = TextMuted,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrayerItemCard(
    prayer: com.example.data.PrayerEntity,
    viewModel: LuminaViewModel,
    onToggleAnswered: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("prayer_item_card_${prayer.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (prayer.isAnswered) Color(0xFF141D15) else SurfaceDark
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (prayer.isAnswered) Color(0xFF2E7D32).copy(alpha = 0.4f) else Color(0xFF2C2C2C)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Checkbox to mark answered
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (prayer.isAnswered) Color(0xFF2E7D32) else Color.Transparent,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .border(
                        width = 1.5.dp,
                        color = if (prayer.isAnswered) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { onToggleAnswered() },
                contentAlignment = Alignment.Center
            ) {
                if (prayer.isAnswered) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Answered Check",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            // Body
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = prayer.title,
                        color = if (prayer.isAnswered) Color(0xFF81C784) else Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        textDecoration = if (prayer.isAnswered) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                    )

                    // Category Badge
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (prayer.isAnswered) Color(0x334CAF50) else GoldPrimary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = prayer.category,
                            color = if (prayer.isAnswered) Color(0xFF81C784) else GoldPrimary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = prayer.requestText,
                    color = if (prayer.isAnswered) Color.White.copy(alpha = 0.6f) else TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                val timeFormat = remember { java.text.SimpleDateFormat("MMM dd, yyyy • h:mm a", java.util.Locale.getDefault()) }
                val dateString = remember(prayer.timestamp) { timeFormat.format(java.util.Date(prayer.timestamp)) }
                Text(
                    text = dateString,
                    color = Color.White.copy(alpha = 0.3f),
                    fontSize = 9.sp
                )
            }

            // Delete Button
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp).testTag("delete_prayer_btn_${prayer.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// =========================================================================
// REAL ROOM-BACKED FEATURE SCREENS: NOTIFICATIONS & STUDY PROGRESS TRACKER
// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(viewModel: LuminaViewModel, onMenuClick: (() -> Unit)? = null) {
    val notifications by viewModel.dbGroupNotifications.collectAsStateWithLifecycle()
    val notificationsEnabled by viewModel.groupNotificationsEnabled.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = DeepBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notification Center",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                },
                navigationIcon = {
                    if (onMenuClick != null) {
                        IconButton(onClick = onMenuClick) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = GoldPrimary)
                        }
                    } else {
                        IconButton(onClick = { viewModel.navigateTo(Screen.Dashboard) }) {
                            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back", tint = GoldPrimary)
                        }
                    }
                },
                actions = {
                    if (notifications.any { !it.isRead }) {
                        TextButton(onClick = { viewModel.markNotificationsAsRead() }) {
                            Text("Mark all read", color = GoldPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlack)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
                .padding(horizontal = 16.dp)
        ) {
            // Push Notification Simulator Control Panel Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1C18)),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("push_notification_simulator_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PUSH NOTIFICATION SIMULATOR",
                            color = GoldPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Box(
                            modifier = Modifier
                                .background(GoldPrimary.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "DEV TOOLS",
                                color = GoldPrimary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Real-time push triggers are simulated locally in the browser emulator environment. Click a button below to broadcast an instant push alert:",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Live Start Trigger Button
                        Button(
                            onClick = {
                                viewModel.triggerSimulatedNotification(
                                    type = "LIVE_START",
                                    messageText = "The study group 'Theology & Grace' has just gone live! Tap to join the video room.",
                                    groupTitle = "Theology & Grace",
                                    groupId = 4
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark, contentColor = Color.White),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0x33FFFFFF)),
                            modifier = Modifier.weight(1f).height(36.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Icon(imageVector = Icons.Default.LiveTv, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Go Live Push", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }

                        // Shared Verse Trigger Button
                        Button(
                            onClick = {
                                viewModel.triggerSimulatedNotification(
                                    type = "SHARED_VERSE",
                                    messageText = "Pastor Elias Vance shared a landmark verse: John 3:16. Meditate on the Love of God today.",
                                    groupTitle = "The Wisdom of Proverbs",
                                    groupId = 1
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark, contentColor = Color.White),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0x33FFFFFF)),
                            modifier = Modifier.weight(1f).height(36.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share Verse Push", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }

                        // Discussion Trigger Button
                        Button(
                            onClick = {
                                viewModel.triggerSimulatedNotification(
                                    type = "CHAT_MESSAGE",
                                    messageText = "Sister Maria Santos started a thread: 'How do you practice daily discernment in secular work?'",
                                    groupTitle = "Deep Dive: Romans 8",
                                    groupId = 2
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark, contentColor = Color.White),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0x33FFFFFF)),
                            modifier = Modifier.weight(1f).height(36.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ChatBubble, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Topic Alert", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Notification Setting Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = if (notificationsEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                            contentDescription = "Notification Icon",
                            tint = if (notificationsEnabled) GoldPrimary else TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text("Group Study Alerts", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Notify when study circles go live or post", color = TextMuted, fontSize = 11.sp)
                        }
                    }
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleGroupNotifications(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DeepBlack,
                            checkedTrackColor = GoldPrimary,
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = SurfaceDark
                        ),
                        modifier = Modifier.testTag("notification_toggle_switch")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!notificationsEnabled) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.NotificationsOff, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Notifications Muted", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Enable group study alerts above to receive notifications.", color = TextMuted, fontSize = 12.sp)
                    }
                }
            } else if (notifications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Inbox, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("No notifications yet", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Study group alerts will appear here.", color = TextMuted, fontSize = 12.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(notifications) { item ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = if (item.isRead) SurfaceDark else CardDark),
                            border = BorderStroke(1.dp, if (item.isRead) Color(0x11FFFFFF) else GoldPrimary.copy(alpha = 0.2f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Navigate to circles when clicked
                                    viewModel.navigateTo(Screen.Dashboard)
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                val icon = when (item.notificationType) {
                                    "LIVE_START" -> Icons.Default.LiveTv
                                    "SHARED_VERSE" -> Icons.Default.MenuBook
                                    "CHAT_MESSAGE" -> Icons.Default.ChatBubble
                                    else -> Icons.Default.Notifications
                                }
                                val tint = if (item.isRead) TextMuted else GoldPrimary
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(tint.copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item.groupTitle,
                                            color = if (item.isRead) TextSecondary else Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        if (!item.isRead) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .background(GoldPrimary, CircleShape)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = item.messageText, color = if (item.isRead) TextMuted else TextSecondary, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    val timeFormat = remember { java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault()) }
                                    Text(text = timeFormat.format(java.util.Date(item.timestamp)), color = TextMuted, fontSize = 9.sp)
                                }

                                IconButton(
                                    onClick = { viewModel.deleteNotification(item.id) },
                                    modifier = Modifier.size(24.dp).testTag("delete_notification_btn_${item.id}")
                                ) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Dismiss", tint = TextMuted, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyTrackerScreen(viewModel: LuminaViewModel, onMenuClick: (() -> Unit)? = null) {
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    var trackerTab by remember { mutableStateOf("Progress") }
    val booksList = listOf("Proverbs", "Romans", "John", "Genesis", "Psalms")
    var selectedBook by remember { mutableStateOf("Proverbs") }
    val totalChapters = bibleBookChapters[selectedBook] ?: 1

    val completedInSelectedBook = completedChapters.filter { it.startsWith(selectedBook) }
    val completedCount = completedInSelectedBook.size
    val progressPercent = if (totalChapters > 0) (completedCount * 100 / totalChapters) else 0

    Scaffold(
        containerColor = DeepBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Study Progress",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                },
                navigationIcon = {
                    if (onMenuClick != null) {
                        IconButton(onClick = onMenuClick) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = GoldPrimary)
                        }
                    } else {
                        IconButton(onClick = { viewModel.navigateTo(Screen.Dashboard) }) {
                            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back", tint = GoldPrimary)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlack)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlack)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Progress", "Reading History", "Advanced Analytics").forEach { tab ->
                    val isSelected = trackerTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else SurfaceDark,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                border = BorderStroke(1.dp, if (isSelected) GoldPrimary else BorderDark),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { trackerTab = tab }
                            .padding(vertical = 10.dp)
                            .testTag("tracker_tab_$tab"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.uppercase(),
                            color = if (isSelected) GoldPrimary else Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (trackerTab == "Progress") {
                // Level & XP Summary Header
            user?.let { u ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.15f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(GoldPrimary.copy(alpha = 0.15f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
                                }
                                Column {
                                    Text("Scholar Level ${u.level}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("Daily Streak: ${u.streakDays} days 🔥", color = GoldLight, fontSize = 11.sp)
                                }
                            }
                            Text("${u.xp} XP", color = GoldPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Progress Bar to next level
                        val threshold = 100 * u.level
                        val levelProgress = if (threshold > 0) (u.xp.toFloat() / threshold) else 0f
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            LinearProgressIndicator(
                                progress = { levelProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = GoldPrimary,
                                trackColor = Color.White.copy(alpha = 0.1f)
                            )
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("Level progress", color = TextMuted, fontSize = 10.sp)
                                Text("${u.xp} / $threshold XP", color = TextMuted, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Current Book Progress Stats Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier.size(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { progressPercent / 100f },
                            modifier = Modifier.fillMaxSize(),
                            color = GoldPrimary,
                            strokeWidth = 6.dp,
                            trackColor = Color.White.copy(alpha = 0.05f)
                        )
                        Text("$progressPercent%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Column {
                        Text(selectedBook, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, fontFamily = FontFamily.Serif)
                        Text(
                            "$completedCount of $totalChapters chapters completed",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Book Selection Row
            Text("SELECT BOOK", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                booksList.forEach { book ->
                    val isSelected = selectedBook == book
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isSelected) GoldPrimary else SurfaceDark,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) GoldPrimary else Color(0x22FFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedBook = book }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = book,
                            color = if (isSelected) DeepBlack else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chapters Grid Header
            Text("CHAPTERS CHECKLIST", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Chapters Grid Layout
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 64.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize().weight(1f)
            ) {
                items(totalChapters) { index ->
                    val chapterNum = index + 1
                    val chapterKey = "$selectedBook $chapterNum"
                    val isChapterCompleted = completedChapters.contains(chapterKey)

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isChapterCompleted) GoldPrimary.copy(alpha = 0.15f) else SurfaceDark
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isChapterCompleted) GoldPrimary else Color(0x11FFFFFF)
                        ),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable {
                                viewModel.toggleChapterCompleted(selectedBook, chapterNum, emptyList())
                            }
                            .testTag("chapter_card_${selectedBook}_$chapterNum")
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = "$chapterNum",
                                    color = if (isChapterCompleted) GoldPrimary else Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                if (isChapterCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Completed",
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            } else if (trackerTab == "Reading History") {
                // Reading History Screen
                val historyList by viewModel.dbStudyProgress.collectAsStateWithLifecycle()
                val sortedHistory = remember(historyList) { historyList.sortedByDescending { it.timestamp } }

                if (sortedHistory.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = GoldPrimary.copy(alpha = 0.4f),
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = "No reading history recorded yet.",
                                color = TextMuted,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Mark chapters as completed in the Scripture tab or progress checklist to build your history log.",
                                color = TextMuted.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(sortedHistory) { progress ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                                border = BorderStroke(1.dp, Color(0x11FFFFFF)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(GoldPrimary.copy(alpha = 0.12f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Book,
                                            contentDescription = null,
                                            tint = GoldPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${progress.book} Chapter ${progress.chapter}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Text(
                                                text = "Completed on ${progress.dateCompleted}",
                                                color = TextSecondary,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.toggleChapterCompleted(progress.book, progress.chapter, emptyList())
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Remove from history",
                                            tint = Color.Red.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                StudyTrackerAnalytics(
                    viewModel = viewModel,
                    completedChapters = completedChapters,
                    user = user
                )
            }
        }
    }
}

@Composable
fun ScriptureReadingPlansTab(
    viewModel: LuminaViewModel,
    onNavigateToReadTab: (String, Int) -> Unit
) {
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val subscribedPlanIds by viewModel.subscribedPlanIds.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("reading_plans_tab"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "SPIRITUAL READING PLANS",
                color = GoldPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Nurture your daily spiritual discipline with structured scripture reading programs. Read the chapters to automatically track progress.",
                color = TextSecondary,
                fontSize = 11.sp
            )
        }

        // Subscribed Plans Section
        val subscribedPlans = viewModel.availableReadingPlans.filter { subscribedPlanIds.contains(it.id) }
        if (subscribedPlans.isNotEmpty()) {
            item {
                Text(
                    text = "ACTIVE PLANS",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            items(subscribedPlans) { plan ->
                ReadingPlanCard(
                    plan = plan,
                    completedChapters = completedChapters,
                    isSubscribed = true,
                    onSubscribe = { viewModel.subscribeToPlan(plan.id) },
                    onUnsubscribe = { viewModel.unsubscribeFromPlan(plan.id) },
                    onChapterClick = onNavigateToReadTab
                )
            }
        }

        // Explore More Plans Section
        val otherPlans = viewModel.availableReadingPlans.filter { !subscribedPlanIds.contains(it.id) }
        if (otherPlans.isNotEmpty()) {
            item {
                Text(
                    text = "EXPLORE PLANS",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            items(otherPlans) { plan ->
                ReadingPlanCard(
                    plan = plan,
                    completedChapters = completedChapters,
                    isSubscribed = false,
                    onSubscribe = { viewModel.subscribeToPlan(plan.id) },
                    onUnsubscribe = { viewModel.unsubscribeFromPlan(plan.id) },
                    onChapterClick = onNavigateToReadTab
                )
            }
        }
    }
}

@Composable
fun ReadingPlanCard(
    plan: LuminaViewModel.ReadingPlan,
    completedChapters: Set<String>,
    isSubscribed: Boolean,
    onSubscribe: () -> Unit,
    onUnsubscribe: () -> Unit,
    onChapterClick: (String, Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Calculate progress
    val planChapters = plan.chapters
    val completedCount = planChapters.count { completedChapters.contains(it) }
    val progressPercent = if (planChapters.isNotEmpty()) (completedCount.toFloat() / planChapters.size.toFloat() * 100).toInt() else 0
    val progressFloat = if (planChapters.isNotEmpty()) completedCount.toFloat() / planChapters.size.toFloat() else 0f

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        border = BorderStroke(1.dp, if (isSubscribed) GoldPrimary.copy(alpha = 0.3f) else BorderDark),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .testTag("reading_plan_card_${plan.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(GoldPrimary.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = plan.category.uppercase(),
                                color = GoldPrimary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "${plan.durationDays} Days",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = plan.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                Button(
                    onClick = {
                        if (isSubscribed) onUnsubscribe() else onSubscribe()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSubscribed) Color(0xFF222222) else GoldPrimary,
                        contentColor = if (isSubscribed) Color.White else DeepBlack
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = if (isSubscribed) "Active" else "Subscribe",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = plan.description,
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progress: $progressPercent%",
                    color = if (isSubscribed) GoldPrimary else TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$completedCount of ${planChapters.size} chapters",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { progressFloat },
                color = GoldPrimary,
                trackColor = Color(0xFF222222),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    Divider(color = BorderDark, modifier = Modifier.padding(bottom = 10.dp))
                    Text(
                        text = "PLAN READING SCHEDULE (TAP TO READ)",
                        color = GoldPrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        planChapters.forEachIndexed { index, chapter ->
                            val isChapterCompleted = completedChapters.contains(chapter)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF141414), RoundedCornerShape(8.dp))
                                    .border(BorderStroke(1.dp, Color(0x11FFFFFF)), RoundedCornerShape(8.dp))
                                    .clickable {
                                        try {
                                            val lastSpace = chapter.lastIndexOf(' ')
                                            if (lastSpace != -1) {
                                                val book = chapter.substring(0, lastSpace).trim()
                                                val chNum = chapter.substring(lastSpace + 1).toIntOrNull() ?: 1
                                                onChapterClick(book, chNum)
                                            }
                                        } catch (e: Exception) {}
                                    }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "Day ${index + 1}",
                                        color = GoldLight,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = chapter,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Icon(
                                    imageVector = if (isChapterCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = if (isChapterCompleted) "Completed" else "Uncompleted",
                                    tint = if (isChapterCompleted) GoldPrimary else Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VerseConcordanceSection(verseKey: String, verseText: String) {
    val isOldTestament = !verseKey.startsWith("Matthew") &&
            !verseKey.startsWith("Mark") &&
            !verseKey.startsWith("Luke") &&
            !verseKey.startsWith("John") &&
            !verseKey.startsWith("Acts") &&
            !verseKey.startsWith("Romans") &&
            !verseKey.startsWith("Galatians") &&
            !verseKey.startsWith("Philippians") &&
            !verseKey.startsWith("Hebrews") &&
            !verseKey.startsWith("Revelation")

    val rootWords = remember(verseKey, verseText) {
        val list = mutableListOf<Triple<String, String, String>>()
        val lowerText = verseText.lowercase()
        
        if (isOldTestament) {
            if (lowerText.contains("trust")) {
                list.add(Triple("Trust", "H982 (בָּטַח - Baṭaḥ)", "To feel secure, be bold, confide in. Trust wholly."))
            }
            if (lowerText.contains("heart")) {
                list.add(Triple("Heart", "H3824 (לֵבָב - Lēḇāḇ)", "Inner man, mind, will, heart, center of life."))
            }
            if (lowerText.contains("understanding")) {
                list.add(Triple("Understanding", "H998 (בִּינָה - Bīnāh)", "Discernment, intelligence, wisdom, insight."))
            }
            if (lowerText.contains("commanded") || lowerText.contains("statutes") || lowerText.contains("law")) {
                list.add(Triple("Command", "H6680 (צָוָה - Ṣāwāh)", "To charge, order, command, lay charge upon."))
            }
            if (lowerText.contains("strong") || lowerText.contains("strengthens")) {
                list.add(Triple("Strong", "H2388 (חָזַק - Ḥāzaq)", "To fasten upon, seize, be courageous, strengthen."))
            }
            if (lowerText.contains("shepherd")) {
                list.add(Triple("Shepherd", "H7462 (רָעָה - Rā‘āh)", "To pasture, tend, feed, keep sheep as shepherd."))
            }
            if (lowerText.contains("peace")) {
                list.add(Triple("Peace", "H7965 (שָׁלוֹם - Shālōm)", "Completeness, wholeness, health, peace, welfare."))
            }
            if (lowerText.contains("wisdom")) {
                list.add(Triple("Wisdom", "H2111 (חָכְמָה - Ḥāḵmāh)", "Skill, shrewdness, wisdom in administration and life."))
            }
            if (list.isEmpty()) {
                list.add(Triple("Lord (Yahweh)", "H3068 (יהוה)", "The self-existent, eternal Covenant God of Israel."))
                list.add(Triple("God (Elohim)", "H430 (אֱלֹהִים)", "Plural of majesty; the Almighty Creator God."))
            }
        } else {
            if (lowerText.contains("peace")) {
                list.add(Triple("Peace", "G1515 (εἰρήνη - Eirēnē)", "One, quietness, rest, peace with God and man."))
            }
            if (lowerText.contains("gospel")) {
                list.add(Triple("Gospel", "G2098 (εὐαγγέλιον - Euangelion)", "Good tidings, good news, preach the word of God."))
            }
            if (lowerText.contains("heart")) {
                list.add(Triple("Heart", "G2588 (καρδία - Kardia)", "The center of physical and spiritual life; inner mind."))
            }
            if (lowerText.contains("faith") || lowerText.contains("faithful")) {
                list.add(Triple("Faith", "G4102 (πίστις - Pistis)", "Conviction of the truth, belief, trust, holy reliance."))
            }
            if (lowerText.contains("love") || lowerText.contains("lovingkindness")) {
                list.add(Triple("Love", "G26 (ἀγάπη - Agapē)", "Benevolent, sacrificial, unconditional divine love."))
            }
            if (lowerText.contains("rest")) {
                list.add(Triple("Rest", "G372 (ἀνάπαυσις - Anapausis)", "Cessation from toil, refreshment, spiritual rest."))
            }
            if (list.isEmpty()) {
                list.add(Triple("Grace", "G5485 (χάρις - Charis)", "Unmerited divine favor, spiritual gift, gratitude."))
                list.add(Triple("Christ", "G5547 (Χριστός - Christos)", "The Anointed One, Messiah, Saviour."))
            }
        }
        list
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141311)),
        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.15f)),
        modifier = Modifier.fillMaxWidth().testTag("verse_concordance_section")
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Strong's Concordance Study",
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                rootWords.forEach { (word, strongs, def) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1F1E1B), RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(word, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Text(strongs, color = GoldLight, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(def, color = TextMuted, fontSize = 10.sp, lineHeight = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun StudyTrackerAnalytics(
    viewModel: LuminaViewModel,
    completedChapters: Set<String>,
    user: com.example.data.UserEntity?
) {
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val highlights by viewModel.highlights.collectAsStateWithLifecycle()
    val readVerses by viewModel.readVerses.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("advanced_analytics_tab"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Analytics Summary
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.15f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "SPIRITUAL VELOCITY METRICS",
                        color = GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${completedChapters.size}",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Text("Chapters Read", color = TextMuted, fontSize = 10.sp)
                        }

                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${readVerses.size}",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Text("Verses Studied", color = TextMuted, fontSize = 10.sp)
                        }

                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            val velocityValue = if (completedChapters.isEmpty()) "0" else String.format("%.1f", readVerses.size.toFloat() / completedChapters.size.toFloat())
                            Text(
                                text = velocityValue,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Text("Verses/Chapter", color = TextMuted, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // Weekly Study Heatmap Grid
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "WEEKLY CONSISTENCY HEATMAP",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Consistency: 86%",
                            color = GoldPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // Represent 7 days of the week with custom squares
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                        val activeIntensity = listOf(0.15f, 0.4f, 0.8f, 0.15f, 0.6f, 0.9f, 0.3f)

                        days.forEachIndexed { index, day ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(GoldPrimary.copy(alpha = activeIntensity[index]), RoundedCornerShape(8.dp))
                                        .border(BorderStroke(1.dp, if (activeIntensity[index] > 0.5f) GoldPrimary else BorderDark), RoundedCornerShape(8.dp))
                                )
                                Text(day, color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }

        // Theological Subject Area Focus
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "THEOLOGICAL TOPIC DISTRIBUTION",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    // Wisdom (Proverbs)
                    val wisdomProgress = 0.75f
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Wisdom & Discernment (Proverbs)", color = TextSecondary, fontSize = 11.sp)
                            Text("75%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { wisdomProgress },
                            color = GoldPrimary,
                            trackColor = Color(0xFF222222),
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )
                    }

                    // Theology & Doctrine (Romans)
                    val theologyProgress = 0.45f
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Theology & Justification (Romans)", color = TextSecondary, fontSize = 11.sp)
                            Text("45%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { theologyProgress },
                            color = Color(0xFF0288D1),
                            trackColor = Color(0xFF222222),
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )
                    }

                    // Gospel & Salvation (John)
                    val gospelsProgress = 0.30f
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Christology & Eternal Life (John)", color = TextSecondary, fontSize = 11.sp)
                            Text("30%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { gospelsProgress },
                            color = Color(0xFF388E3C),
                            trackColor = Color(0xFF222222),
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )
                    }
                }
            }
        }

        // Annotation & Engagement Summary
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                border = BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "ENGAGEMENT COMPARATIVE RATIOS",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Bookmarks count ratio
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFF141414), RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${bookmarks.size}", color = GoldPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("Bookmarks", color = TextMuted, fontSize = 10.sp)
                        }

                        // Highlights count ratio
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFF141414), RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${highlights.size}", color = Color(0xFFE57373), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("Highlights", color = TextMuted, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BibleDictionaryTab(viewModel: LuminaViewModel) {
    val bibleDictionary = remember {
        listOf(
            Triple("Covenant", "A solemn agreement (Hebrew: 'bĕrîth', Greek: 'diathēkē') between God and His people, establishing a relationship with promises, obligations, and oaths (e.g., Abrahamic, Mosaic, New Covenant).", "Genesis 15:18, Jeremiah 31:31, Hebrews 8:6"),
            Triple("Grace", "Unmerited, free favor of God (Greek: 'charis') bestowed upon mankind for salvation and spiritual enablement, independent of human works.", "Ephesians 2:8-9, Romans 3:24, Titus 2:11"),
            Triple("Faith", "Complete trust, assurance, and loyalty (Greek: 'pistis') toward God, believing His word, and acting upon His truth.", "Hebrews 11:1, Romans 10:17, Ephesians 2:8"),
            Triple("Righteousness", "The state of being in right standing with God (Greek: 'dikaiosynē', Hebrew: 'tsedaqah'), both legally imputed through Christ and practically lived out in obedience.", "Romans 3:22, Matthew 6:33, 2 Corinthians 5:21"),
            Triple("Sanctification", "The ongoing process (Greek: 'hagiasmos') of being set apart for God's holy use and transformed into the likeness of Christ through the Holy Spirit.", "1 Thessalonians 4:3, Hebrews 10:14, 1 Peter 1:2"),
            Triple("Redemption", "The act of buying back or releasing from captivity/sin (Greek: 'apolytrōsis') by paying a ransom, specifically the sacrificial blood of Jesus Christ.", "Ephesians 1:7, Colossians 1:14, Galatians 3:13"),
            Triple("Prophecy", "Divinely inspired declaration or revelation (Greek: 'prophēteia') of God's will, purpose, or future events, to edify, exhort, and comfort His people.", "2 Peter 1:21, 1 Corinthians 14:3, Revelation 19:10"),
            Triple("Justification", "The judicial act of God declaring a sinner righteous (Greek: 'dikaiōsis') solely through faith in the sacrificial death of Jesus Christ.", "Romans 5:1, Romans 3:28, Galatians 2:16"),
            Triple("Mercy", "Compassion, lovingkindness, or forbearance (Greek: 'eleos', Hebrew: 'chesed') shown to someone whom it is within one's power to punish or harm.", "Ephesians 2:4, Lamentations 3:22-23, Hebrews 4:16"),
            Triple("Atonement", "The reconciliation of God and humankind (Hebrew: 'kippur') through the sacrificial offering, specifically the death of Jesus, covering and washing away sin.", "Romans 5:11, Leviticus 17:11, 1 John 2:2"),
            Triple("Discernment", "The spiritual ability to distinguish (Greek: 'diakrisis') between truth and error, good and evil, and to perceive God's direction in situations.", "Hebrews 5:14, Philippians 1:9-10, Proverbs 3:21"),
            Triple("Holiness", "The quality of being morally pure, sacred, and set apart (Hebrew: 'qodesh', Greek: 'hagios'), reflecting God's absolute perfection.", "1 Peter 1:15-16, Hebrews 12:14, Leviticus 19:2"),
            Triple("Fellowship", "Koinonia (Greek) - intimate spiritual communion, sharing, and mutual support among believers united in Christ.", "1 John 1:3, Acts 2:42, 2 Corinthians 13:14")
        )
    }

    var dictionaryQuery by remember { mutableStateOf("") }
    val filteredDictionary = remember(dictionaryQuery) {
        if (dictionaryQuery.isEmpty()) {
            bibleDictionary
        } else {
            bibleDictionary.filter {
                it.first.contains(dictionaryQuery, ignoreCase = true) ||
                it.second.contains(dictionaryQuery, ignoreCase = true) ||
                it.third.contains(dictionaryQuery, ignoreCase = true)
            }
        }
    }

    var subTabMode by remember { mutableStateOf("local") } // "local" or "google"

    // Google Theological Insights States
    var googleQuery by remember { mutableStateOf("") }
    val googleInsight by viewModel.googleTheologicalInsight.collectAsStateWithLifecycle()
    val googleInsightLoading by viewModel.googleTheologicalInsightLoading.collectAsStateWithLifecycle()
    val googleInsightError by viewModel.googleTheologicalInsightError.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("bible_dictionary_tab")
    ) {
        // Mode Switcher Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .background(SurfaceDark, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = { subTabMode = "local" },
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .testTag("dictionary_mode_local"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (subTabMode == "local") GoldPrimary else Color.Transparent,
                    contentColor = if (subTabMode == "local") CardDark else Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text("Local Dictionary", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Button(
                onClick = { subTabMode = "google" },
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .testTag("dictionary_mode_google"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (subTabMode == "google") GoldPrimary else Color.Transparent,
                    contentColor = if (subTabMode == "google") CardDark else Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Public, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text("Google Web Insights", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (subTabMode == "local") {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Text(
                        text = "BIBLE DICTIONARY & CONCORDANCE",
                        color = GoldPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Deepen your comprehension of scriptural terminology, original Hebrew/Greek roots, and theological foundations.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = dictionaryQuery,
                        onValueChange = { dictionaryQuery = it },
                        placeholder = { Text("Search dictionary words or scriptures...", color = TextMuted, fontSize = 13.sp) },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = GoldPrimary, modifier = Modifier.size(18.dp)) },
                        trailingIcon = if (dictionaryQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { dictionaryQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted, modifier = Modifier.size(16.dp))
                                }
                            }
                        } else null,
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = BorderDark,
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("bible_dictionary_search")
                    )
                }

                if (filteredDictionary.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No dictionary results found for \"$dictionaryQuery\"", color = TextMuted, fontSize = 12.sp)
                        }
                    }
                } else {
                    items(filteredDictionary) { (term, definition, keyVerses) ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            border = BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth().testTag("dictionary_card_$term").clickable {
                                googleQuery = term
                                subTabMode = "google"
                                viewModel.fetchGoogleTheologicalInsight(term)
                            }
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = term,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Serif
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Web Insight", color = GoldLight, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
                                        Icon(
                                            imageVector = Icons.Default.Public,
                                            contentDescription = "Search Google",
                                            tint = GoldPrimary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = definition,
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(color = BorderDark, modifier = Modifier.padding(vertical = 4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Key References:",
                                        color = GoldLight,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = keyVerses,
                                        color = TextMuted,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Google Web Insights Mode
            LazyColumn(
                modifier = Modifier.fillMaxSize().testTag("google_theological_tab"),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Text(
                        text = "GOOGLE THEOLOGICAL RESEARCH",
                        color = GoldPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Query Google.com and Google's Gemini models for real-time theological analysis, comparative denominational frameworks, and academic citations.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = googleQuery,
                            onValueChange = { googleQuery = it },
                            placeholder = { Text("Search any topic (e.g. 'Grace', 'Ephesians 2:8')...", color = TextMuted, fontSize = 13.sp) },
                            leadingIcon = { Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp)) },
                            trailingIcon = if (googleQuery.isNotEmpty()) {
                                {
                                    IconButton(onClick = { googleQuery = "" }) {
                                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted, modifier = Modifier.size(16.dp))
                                    }
                                }
                            } else null,
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = BorderDark,
                                focusedContainerColor = SurfaceDark,
                                unfocusedContainerColor = SurfaceDark,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1.0f).testTag("google_theological_search_input")
                        )

                        Button(
                            onClick = { viewModel.fetchGoogleTheologicalInsight(googleQuery) },
                            enabled = googleQuery.isNotBlank() && !googleInsightLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = CardDark,
                                disabledContainerColor = BorderDark,
                                disabledContentColor = TextMuted
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(52.dp).testTag("google_theological_search_button")
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", modifier = Modifier.size(18.dp))
                        }
                    }
                }

                if (googleInsightLoading) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            border = BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                CircularProgressIndicator(color = GoldPrimary, strokeWidth = 3.dp)
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Querying Google Search...",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Retrieving resources and synthesizing scholarly exegesis...",
                                        color = TextMuted,
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                } else if (googleInsightError != null) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            border = BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Search Query Error", color = Color(0xFFE57373), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(googleInsightError ?: "Unknown search error", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                } else if (googleInsight != null) {
                    val insight = googleInsight!!
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp).testTag("google_insight_result_card")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                        Text(
                                            text = "EXEGESIS: ${insight.query.uppercase()}",
                                            color = GoldPrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            val shareText = "Lumina Google Theological Insight: ${insight.query}\n\nSummary:\n${insight.summary}\n\nFramework:\n${insight.theologicalFramework}\n\nKey Verses:\n${insight.keyVerses}"
                                            clipboardManager.setText(AnnotatedString(shareText))
                                            Toast.makeText(context, "Copied insight to clipboard!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Share, contentDescription = "Copy Insight", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Summary
                                Text(
                                    text = insight.summary,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    fontFamily = FontFamily.Serif
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Divider(color = BorderDark)
                                Spacer(modifier = Modifier.height(12.dp))

                                // Theological Frameworks
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.Book, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(14.dp))
                                    Text(
                                        text = "Denominational & Historical Frameworks",
                                        color = GoldLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = insight.theologicalFramework,
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Divider(color = BorderDark)
                                Spacer(modifier = Modifier.height(12.dp))

                                // Key Verses
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.Book, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(14.dp))
                                    Text(
                                        text = "Primary Biblical References",
                                        color = GoldLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = CardDark),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = insight.keyVerses,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Divider(color = BorderDark)
                                Spacer(modifier = Modifier.height(12.dp))

                                // Citations from Google Search
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(14.dp))
                                    Text(
                                        text = "Sources & Grounding (Google Search)",
                                        color = GoldLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    insight.searchCitations.forEach { citation ->
                                        Card(
                                            shape = RoundedCornerShape(10.dp),
                                            colors = CardDefaults.cardColors(containerColor = CardDark),
                                            border = BorderStroke(1.dp, BorderDark),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = citation.title,
                                                        color = GoldPrimary,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 11.sp,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    IconButton(
                                                        onClick = {
                                                            clipboardManager.setText(AnnotatedString(citation.url))
                                                            Toast.makeText(context, "Source URL copied!", Toast.LENGTH_SHORT).show()
                                                        },
                                                        modifier = Modifier.size(24.dp)
                                                    ) {
                                                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy URL", tint = TextMuted, modifier = Modifier.size(12.dp))
                                                    }
                                                }
                                                Text(
                                                    text = citation.url,
                                                    color = TextMuted,
                                                    fontSize = 9.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = citation.snippet,
                                                    color = TextSecondary,
                                                    fontSize = 11.sp,
                                                    lineHeight = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Empty state (no query searched yet)
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            border = BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = GoldPrimary.copy(alpha = 0.4f), modifier = Modifier.size(48.dp))
                                Text(
                                    text = "Ready for Theological Research",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Type any biblical concept, doctrine, Greek/Hebrew term, or scripture passage in the input above to begin researching with live search citations.",
                                    color = TextSecondary,
                                    fontSize = 11.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrayerWallItemCard(
    prayer: com.example.data.PrayerEntity,
    viewModel: LuminaViewModel
) {
    val authors = listOf("Sister Sarah", "Brother John", "Deacon Robert", "Mary Lopez", "David Kim", "Sister Hannah", "Brother James", "Grace Park")
    val authorName = remember(prayer.id) { authors[prayer.id % authors.size] }
    
    var amensCount by remember { mutableIntStateOf(12 + (prayer.id % 25)) }
    var hasAmened by remember { mutableStateOf(false) }
    
    var showEncourageMenu by remember { mutableStateOf(false) }
    var encouragementScripture by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("prayer_wall_card_${prayer.id}"),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        border = BorderStroke(1.dp, if (hasAmened) GoldPrimary.copy(alpha = 0.4f) else Color(0xFF2C2C2C)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Header: Author & Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(GoldPrimary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = authorName.take(1),
                            color = GoldPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            text = authorName,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        val timeFormat = remember { java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()) }
                        val dateString = remember(prayer.timestamp) { timeFormat.format(java.util.Date(prayer.timestamp)) }
                        Text(
                            text = dateString,
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 9.sp
                        )
                    }
                }

                // Category Badge
                Box(
                    modifier = Modifier
                        .background(GoldPrimary.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = prayer.category,
                        color = GoldPrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Title & Content
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = prayer.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = prayer.requestText,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            // Encouragement sticker if any
            if (encouragementScripture.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1C15)),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Column {
                            Text(
                                text = "Encouragement Sticker Shared:",
                                color = GoldLight,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = encouragementScripture,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }

            Divider(color = Color(0xFF222222), modifier = Modifier.padding(vertical = 2.dp))

            // Action row: Amen count & encourage button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Amen / Join in prayer
                Button(
                    onClick = {
                        if (!hasAmened) {
                            hasAmened = true
                            amensCount++
                            viewModel.gainXp(10)
                            viewModel.showToast("Joined in prayer with $authorName. Amen!")
                            viewModel.checkAndUnlockBadge("prayer_warrior", "Prayer Warrior", "Logged your first prayer request in your journal!")
                        } else {
                            hasAmened = false
                            amensCount--
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (hasAmened) GoldPrimary else Color(0xFF1A1A1A),
                        contentColor = if (hasAmened) DeepBlack else Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(32.dp).testTag("amen_btn_${prayer.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (hasAmened) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Amen",
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (hasAmened) "Amen ($amensCount)" else "Amen ($amensCount)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Encourage Sticker dropdown button
                Button(
                    onClick = { showEncourageMenu = !showEncourageMenu },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF222222),
                        contentColor = GoldPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(32.dp).testTag("encourage_btn_${prayer.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Encourage", modifier = Modifier.size(12.dp))
                        Text("Encourage", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Simple custom overlay menu for encouragement scriptures
            if (showEncourageMenu) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFF2C2C2C), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Choose an Encouragement Scripture Sticker:",
                        color = GoldPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    
                    val scriptures = listOf(
                        "Philippians 4:13 - 'I can do all things through Him who strengthens me.'",
                        "Romans 8:28 - 'All things work together for good to those who love God.'",
                        "Isaiah 41:10 - 'Fear not, for I am with you; be not dismayed...'",
                        "Proverbs 3:5 - 'Trust in the Lord with all your heart...'"
                    )

                    scriptures.forEach { script ->
                        Text(
                            text = script,
                            color = Color.White,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    encouragementScripture = script
                                    showEncourageMenu = false
                                    viewModel.gainXp(5)
                                    viewModel.showToast("Shared encouragement to the Prayer Wall!")
                                }
                                .padding(vertical = 4.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScriptureQuizTab(
    viewModel: LuminaViewModel,
    tts: android.speech.tts.TextToSpeech?
) {
    val quizQuestions = remember {
        listOf(
            Triple(
                "For God so loved the world, that he gave his only Son, that whoever believes in him should not perish but have eternal life.",
                listOf("John 3:16", "Romans 5:8", "Galatians 2:20", "John 1:1"),
                0
            ),
            Triple(
                "Trust in the Lord with all your heart, and do not lean on your own understanding.",
                listOf("Proverbs 3:5", "Psalms 23:1", "Isaiah 40:31", "Matthew 6:33"),
                0
            ),
            Triple(
                "The Lord is my shepherd; I shall not want.",
                listOf("Psalms 23:1", "Genesis 1:1", "John 10:11", "Romans 12:1"),
                0
            ),
            Triple(
                "I can do all things through him who strengthens me.",
                listOf("Philippians 4:13", "Romans 8:28", "Galatians 5:22", "Joshua 1:9"),
                0
            ),
            Triple(
                "But the fruit of the Spirit is love, joy, peace, patience, kindness, goodness, faithfulness, gentleness, self-control...",
                listOf("Galatians 5:22-23", "Ephesians 2:8", "Proverbs 4:23", "Romans 12:2"),
                0
            ),
            Triple(
                "In the beginning, God created the heavens and the earth.",
                listOf("Genesis 1:1", "John 1:1", "Revelation 1:1", "Psalms 19:1"),
                0
            )
        )
    }

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var answerSubmitted by remember { mutableStateOf(false) }
    var correctAnswersCount by remember { mutableStateOf(0) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val currentQuestion = quizQuestions[currentQuestionIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("quiz_tab_content"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Card / Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1C15)),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(GoldPrimary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
                    }
                    Column {
                        Text(
                            text = "Bible Verse Trivia Quiz",
                            color = GoldPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Test your scriptural knowledge and earn daily rewards!",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        if (isQuizCompleted) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("quiz_completed_card"),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(GoldPrimary.copy(alpha = 0.15f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Trophy",
                                tint = GoldPrimary,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Quiz Completed!",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            val rankText = when (correctAnswersCount) {
                                6 -> "Scripture Scholar!"
                                in 4..5 -> "Devoted Bible Student!"
                                else -> "Diligent Reader!"
                            }
                            Text(
                                text = "Rank: $rankText",
                                color = GoldPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = "You scored $correctAnswersCount out of ${quizQuestions.size} correct answers.",
                            color = TextSecondary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )

                        // Progress indicator for score
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(80.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { correctAnswersCount.toFloat() / quizQuestions.size.toFloat() },
                                color = GoldPrimary,
                                trackColor = Color(0xFF1E1E1E),
                                strokeWidth = 8.dp,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = "${(correctAnswersCount * 100 / quizQuestions.size)}%",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                currentQuestionIndex = 0
                                selectedOptionIndex = null
                                answerSubmitted = false
                                correctAnswersCount = 0
                                isQuizCompleted = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Restart Quiz", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(1.dp, BorderDark)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Progress / Count Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "QUESTION ${currentQuestionIndex + 1} OF ${quizQuestions.size}",
                                color = GoldPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            IconButton(
                                onClick = {
                                    val textToSpeak = "Which scripture reference matches this verse? ${currentQuestion.first}"
                                    tts?.speak(textToSpeak, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "QuizQuestionTTS")
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Listen to question",
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        LinearProgressIndicator(
                            progress = { (currentQuestionIndex + 1).toFloat() / quizQuestions.size.toFloat() },
                            color = GoldPrimary,
                            trackColor = Color(0xFF1E1E1E),
                            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp))
                        )

                        // Question Scripture text
                        Text(
                            text = "“${currentQuestion.first}”",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Text(
                            text = "Which book and chapter matches this scripture?",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Multiple Choice Options
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            currentQuestion.second.forEachIndexed { idx, option ->
                                val isSelected = selectedOptionIndex == idx
                                val isCorrectOption = idx == currentQuestion.third
                                val optionColor = when {
                                    answerSubmitted && isCorrectOption -> Color(0xFF2E7D32) // Green for correct
                                    answerSubmitted && isSelected && !isCorrectOption -> Color(0xFFC62828) // Red for selected incorrect
                                    isSelected -> GoldPrimary.copy(alpha = 0.25f)
                                    else -> Color(0xFF1E1E1E)
                                }
                                val optionBorderColor = when {
                                    answerSubmitted && isCorrectOption -> Color(0xFF81C784)
                                    answerSubmitted && isSelected && !isCorrectOption -> Color(0xFFE57373)
                                    isSelected -> GoldPrimary
                                    else -> Color(0xFF2C2C2C)
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(optionColor, RoundedCornerShape(12.dp))
                                        .border(1.dp, optionBorderColor, RoundedCornerShape(12.dp))
                                        .clickable(enabled = !answerSubmitted) {
                                            selectedOptionIndex = idx
                                        }
                                        .padding(14.dp)
                                        .testTag("quiz_option_$idx"),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = option,
                                        color = if (isSelected && !answerSubmitted) GoldPrimary else Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    if (answerSubmitted) {
                                        if (isCorrectOption) {
                                            Icon(imageVector = Icons.Default.Check, contentDescription = "Correct", tint = Color(0xFF81C784), modifier = Modifier.size(16.dp))
                                        } else if (isSelected) {
                                            Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrect", tint = Color(0xFFE57373), modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }

                        // Explanation and narration options on submit
                        if (answerSubmitted) {
                            val isCorrect = selectedOptionIndex == currentQuestion.third
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = if (isCorrect) Color(0x104CAF50) else Color(0x10F44336)),
                                border = BorderStroke(1.dp, (if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)).copy(alpha = 0.3f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = if (isCorrect) "✨ Correct! Well Done." else "❌ Incorrect.",
                                        color = if (isCorrect) Color(0xFF81C784) else Color(0xFFE57373),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    val quoteOption = currentQuestion.second[currentQuestion.third]
                                    Text(
                                        text = "This verse is from $quoteOption. Spend some time reflecting on its wisdom today.",
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth().clickable {
                                            tts?.speak("The correct reference is $quoteOption. ${currentQuestion.first}", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "QuizExplanationTTS")
                                        },
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = GoldPrimary, modifier = Modifier.size(12.dp))
                                        Text("Listen to this scripture", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        // Action button
                        Button(
                            onClick = {
                                if (!answerSubmitted) {
                                    if (selectedOptionIndex != null) {
                                        answerSubmitted = true
                                        val isCorrect = selectedOptionIndex == currentQuestion.third
                                        if (isCorrect) {
                                            correctAnswersCount++
                                            viewModel.gainXp(15)
                                        }
                                    } else {
                                        viewModel.showToast("Please choose an option first!")
                                    }
                                } else {
                                    if (currentQuestionIndex < quizQuestions.size - 1) {
                                        currentQuestionIndex++
                                        selectedOptionIndex = null
                                        answerSubmitted = false
                                    } else {
                                        isQuizCompleted = true
                                        if (correctAnswersCount == quizQuestions.size) {
                                            viewModel.checkAndUnlockBadge("quiz_master", "Scripture Scholar", "Achieved a perfect score in the Bible Verse Quiz!")
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(42.dp).testTag("quiz_submit_btn")
                        ) {
                            Text(
                                text = if (!answerSubmitted) "Submit Answer" else if (currentQuestionIndex < quizQuestions.size - 1) "Next Question" else "Finish Quiz",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GroupDashboardView(
    group: StudyGroupEntity?,
    viewModel: LuminaViewModel
) {
    var loggedChapters by remember { mutableIntStateOf(0) }
    var groupGoalProgress by remember { mutableIntStateOf(74) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("group_dashboard_content")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Group Info / Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.25f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = group?.title ?: "Bible Study Circle",
                        color = GoldPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Topic: ${group?.topic ?: "Scripture Discussion"}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Founded by ${group?.hostName ?: "Pastor David"}. A warm community dedicated to reading, meditating, and growing together in God's Holy Word.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Analytics Widget Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(
                    Triple(Icons.Default.Group, "Connected", "14 Members"),
                    Triple(Icons.Default.Chat, "Total Messages", "248 Chats"),
                    Triple(Icons.Default.LocalFireDepartment, "Streak", "18 Days")
                ).forEach { (icon, title, value) ->
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF141414)),
                        border = BorderStroke(1.dp, BorderDark),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = icon, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                            Text(text = title, color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text(text = value, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Shared Goal Widget Progress
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = BorderStroke(1.dp, BorderDark),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Shared Reading Goal",
                                color = GoldPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                text = "Pool our progress together to finish our chapter goal!",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(GoldPrimary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Romans Focus",
                                color = GoldPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    val totalGoal = 100
                    val currentProgress = groupGoalProgress + loggedChapters
                    val ratio = currentProgress.toFloat() / totalGoal.toFloat()

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Progress: $currentProgress / $totalGoal Chapters Read",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${(ratio * 100).toInt()}% Done",
                                color = GoldPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        LinearProgressIndicator(
                            progress = { ratio },
                            color = GoldPrimary,
                            trackColor = Color(0xFF141414),
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contribute your study: did you finish read chapters?",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                        Button(
                            onClick = {
                                loggedChapters++
                                viewModel.gainXp(20)
                                viewModel.showToast("Logged read chapter! You contributed +1 chapter and gained +20 XP.")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DeepBlack),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(28.dp).testTag("contribute_goal_btn")
                        ) {
                            Text("Log +1 Chapter", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Live Presence Members List
        item {
            Text(
                text = "STUDY CIRCLE ACTIVE MEMBERS",
                color = GoldPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        val members = listOf(
            Triple("Pastor David", "Host • Teaching", Color.Green),
            Triple("Sister Sarah", "Reading Romans 8", Color.Green),
            Triple("Brother John", "Praying • Active", Color.Green),
            Triple("Mary Lopez", "Writing Study Notes", Color.Green),
            Triple("David Kim", "Listen-only Mode", Color.Gray),
            Triple("Sister Hannah", "Away • Online", Color.Yellow)
        )

        items(members) { (name, status, indicatorColor) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.showToast("Sent virtual encouragement hug to $name!")
                        viewModel.gainXp(5)
                    },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
                border = BorderStroke(1.dp, Color(0xFF222222)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(GoldPrimary.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = name.take(1),
                                color = GoldPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {
                            Text(
                                text = name,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = status,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(indicatorColor, CircleShape)
                        )
                        Text(
                            text = if (indicatorColor == Color.Green) "Active" else if (indicatorColor == Color.Yellow) "Away" else "Offline",
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
