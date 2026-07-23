package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.testTag
import com.example.data.AppDatabase
import com.example.data.LuminaRepository
import com.example.data.UserEntity
import com.example.ui.*
import com.example.ui.theme.DeepBlack
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.LuminaStudyTheme
import com.example.ui.theme.SurfaceDark
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase
    private lateinit var repository: LuminaRepository
    private lateinit var viewModel: LuminaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Instantiate SQLite Room Database
        try {
            com.google.firebase.FirebaseApp.initializeApp(this)
            android.util.Log.d("LuminaFirebase", "Firebase initialized successfully!")
        } catch (e: Exception) {
            android.util.Log.e("LuminaFirebase", "Firebase initialization bypassed: ${e.message}")
        }

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "lumina_study_db"
        )
        .fallbackToDestructiveMigration()
        .build()

        // 2. Initialize Repositories and ViewModels
        repository = LuminaRepository(
            userDao = database.userDao(),
            studyGroupDao = database.studyGroupDao(),
            chatMessageDao = database.chatMessageDao(),
            blockedUserDao = database.blockedUserDao(),
            searchHistoryDao = database.searchHistoryDao(),
            unlockedBadgeDao = database.unlockedBadgeDao(),
            prayerDao = database.prayerDao(),
            studyNoteDao = database.studyNoteDao(),
            studyProgressDao = database.studyProgressDao(),
            verseHighlightDao = database.verseHighlightDao(),
            groupNotificationDao = database.groupNotificationDao(),
            studyReminderDao = database.studyReminderDao()
        )
        viewModel = LuminaViewModel(repository)

        setContent {
            val currentStudyTheme by viewModel.currentStudyTheme.collectAsStateWithLifecycle()
            LuminaStudyTheme(studyTheme = currentStudyTheme) {
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
                val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
                val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
                val context = LocalContext.current

                // Handle global visual feedback toasts
                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearToast()
                    }
                }

                // Global Layout Shell
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (currentUser == null) {
                        // User not authenticated yet: route through authorization screens
                        when (currentScreen) {
                            Screen.Welcome -> WelcomeScreen(viewModel)
                            Screen.Login -> LoginScreen(viewModel)
                            Screen.Register -> RegisterScreen(viewModel)
                            else -> {
                                // Default fallback to welcome
                                viewModel.navigateTo(Screen.Welcome)
                                WelcomeScreen(viewModel)
                            }
                        }
                    } else {
                        // Authenticated user session - Responsive layout logic
                        val configuration = LocalConfiguration.current
                        val screenWidthDp = configuration.screenWidthDp
                        val isExpanded = screenWidthDp >= 600

                        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                        val scope = rememberCoroutineScope()
                        val navController = rememberNavController()

                        if (isExpanded) {
                            // Expanded layouts (Tablets, Foldables, Landscape Mobile) -> Persistent Sidebar on left
                            Row(modifier = Modifier.fillMaxSize()) {
                                LuminaSidebar(
                                    activeScreen = currentScreen,
                                    onTabSelected = { targetScreen ->
                                        viewModel.navigateTo(targetScreen)
                                    },
                                    currentUser = currentUser,
                                    onLogout = { viewModel.logout() },
                                    viewModel = viewModel
                                )
                                VerticalDivider(color = Color(0x11FFFFFF), thickness = 1.dp)
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                ) {
                                    LuminaHashNavHost(
                                        navController = navController,
                                        currentScreen = currentScreen,
                                        viewModel = viewModel
                                    )
                                }
                            }
                        } else {
                            // Compact layouts (Phones) -> Modal Navigation Drawer + Bottom Bar for optimal ergonomics
                            ModalNavigationDrawer(
                                drawerState = drawerState,
                                drawerContent = {
                                    ModalDrawerSheet(
                                        drawerContainerColor = Color(0xFF0F0F0F),
                                        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                                        modifier = Modifier.width(300.dp)
                                    ) {
                                        LuminaDrawerContent(
                                            activeScreen = currentScreen,
                                            onTabSelected = { targetScreen ->
                                                viewModel.navigateTo(targetScreen)
                                                scope.launch { drawerState.close() }
                                            },
                                            currentUser = currentUser,
                                            onLogout = {
                                                viewModel.logout()
                                                scope.launch { drawerState.close() }
                                            },
                                            viewModel = viewModel
                                        )
                                    }
                                }
                            ) {
                                Scaffold(
                                    modifier = Modifier.fillMaxSize(),
                                    containerColor = DeepBlack,
                                    bottomBar = {
                                        // Hide navigation bar in active focus-heavy screens like the live MeetingRoom or active Focus Reading mode
                                        val isFocusMode by viewModel.isFocusMode.collectAsStateWithLifecycle()
                                        if (currentScreen != Screen.MeetingRoom && !isFocusMode) {
                                            LuminaBottomNavigation(
                                                activeScreen = currentScreen,
                                                onTabSelected = { targetScreen ->
                                                    viewModel.navigateTo(targetScreen)
                                                }
                                            )
                                        }
                                    }
                                ) { innerPadding ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding)
                                    ) {
                                        LuminaHashNavHost(
                                            navController = navController,
                                            currentScreen = currentScreen,
                                            viewModel = viewModel,
                                            onMenuClick = { scope.launch { drawerState.open() } }
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

@Composable
fun LuminaBottomNavigation(
    activeScreen: Screen,
    onTabSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF0A0A0A),
        tonalElevation = 0.dp,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        val items = listOf(
            Triple(Screen.Dashboard, Icons.Default.Home, "Home"),
            Triple(Screen.Settings, Icons.Default.Book, "Scripture"),
            Triple(Screen.Profile, Icons.Default.Person, "Profile"),
            Triple(Screen.DeveloperHub, Icons.Default.Terminal, "Dev Hub")
        )

        items.forEach { (screen, icon, label) ->
            val isSelected = activeScreen == screen || (screen == Screen.Dashboard && activeScreen == Screen.MeetingRoom)

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(screen) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (isSelected) GoldPrimary else Color(0xFF555555)
                    )
                },
                label = {
                    Text(
                        text = label,
                        color = if (isSelected) GoldPrimary else Color(0xFF555555),
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0x22C9A063) // Subtle gold active pill highlight
                )
            )
        }
    }
}

@Composable
fun LuminaDrawerContent(
    activeScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    currentUser: UserEntity?,
    onLogout: () -> Unit,
    viewModel: LuminaViewModel
) {
    val animationSpeed by viewModel.animationSpeed.collectAsStateWithLifecycle()
    val sidebarTransitionType by viewModel.sidebarTransitionType.collectAsStateWithLifecycle()

    var triggerAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(activeScreen) {
        triggerAnimation = false
        kotlinx.coroutines.delay(50)
        triggerAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // App brand header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "esposizione",
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(
                    text = "GLOBAL BIBLE STUDY",
                    color = GoldPrimary,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        HorizontalDivider(color = Color(0x1AFFFFFF), thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        // Logged-in user card
        currentUser?.let { user ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF141414), RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(user.avatarColor), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.displayName.take(2).uppercase(),
                        color = DeepBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = user.displayName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = user.email,
                        color = Color(0xFF888888),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Menu title
        Text(
            text = "NAVIGATION",
            color = Color(0xFF666666),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
        )

        val navItems = listOf(
            Triple(Screen.Dashboard, Icons.Default.Home, "Group Chat Circles"),
            Triple(Screen.Settings, Icons.Default.Book, "Scripture Reading"),
            Triple(Screen.Notifications, Icons.Default.Notifications, "Notifications"),
            Triple(Screen.StudyTracker, Icons.Default.TrendingUp, "Study Tracker"),
            Triple(Screen.Profile, Icons.Default.Person, "Profile & Settings"),
            Triple(Screen.DeveloperHub, Icons.Default.Terminal, "Developer Hub")
        )

        navItems.forEachIndexed { index, (screen, icon, label) ->
            val isSelected = activeScreen == screen
            val delay = index * 50
            val duration = animationSpeed

            val alpha by animateFloatAsState(
                targetValue = if (triggerAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = duration, delayMillis = delay, easing = FastOutSlowInEasing),
                label = "alpha"
            )

            val offsetX by animateDpAsState(
                targetValue = if (triggerAnimation) {
                    0.dp
                } else {
                    when (sidebarTransitionType) {
                        "Slide & Fade" -> (-40).dp
                        "Scale & Pop" -> 0.dp
                        "Bounce Accent" -> (-20).dp
                        else -> (-40).dp
                    }
                },
                animationSpec = if (sidebarTransitionType == "Bounce Accent" && triggerAnimation) {
                    spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
                } else {
                    tween(durationMillis = duration, delayMillis = delay, easing = FastOutSlowInEasing)
                },
                label = "offset"
            )

            val scale by animateFloatAsState(
                targetValue = if (triggerAnimation) {
                    1f
                } else {
                    if (sidebarTransitionType == "Scale & Pop") 0.8f else 1f
                },
                animationSpec = if (sidebarTransitionType == "Scale & Pop" && triggerAnimation) {
                    spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
                } else {
                    tween(durationMillis = duration, delayMillis = delay)
                },
                label = "scale"
            )

            NavigationDrawerItem(
                label = { Text(label, fontWeight = FontWeight.Bold) },
                selected = isSelected,
                onClick = { onTabSelected(screen) },
                icon = { Icon(icon, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0x22C9A063),
                    selectedIconColor = GoldPrimary,
                    selectedTextColor = GoldPrimary,
                    unselectedContainerColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF888888),
                    unselectedTextColor = Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .graphicsLayer {
                        this.alpha = alpha
                        this.scaleX = scale
                        this.scaleY = scale
                    }
                    .offset(x = offsetX)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(color = Color(0x1AFFFFFF), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Logout
        NavigationDrawerItem(
            label = { Text("Log Out", fontWeight = FontWeight.Bold) },
            selected = false,
            onClick = onLogout,
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Log Out") },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                unselectedIconColor = Color(0xFFFF5555),
                unselectedTextColor = Color(0xFFFF5555)
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun LuminaSidebar(
    activeScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    currentUser: UserEntity?,
    onLogout: () -> Unit,
    viewModel: LuminaViewModel
) {
    val animationSpeed by viewModel.animationSpeed.collectAsStateWithLifecycle()
    val sidebarTransitionType by viewModel.sidebarTransitionType.collectAsStateWithLifecycle()

    var triggerAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(activeScreen) {
        triggerAnimation = false
        kotlinx.coroutines.delay(50)
        triggerAnimation = true
    }

    Column(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight()
            .background(Color(0xFF0A0A0A))
            .padding(20.dp)
    ) {
        // App brand header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "esposizione",
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "GLOBAL BIBLE STUDY",
                    color = GoldPrimary,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        HorizontalDivider(color = Color(0x1AFFFFFF), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Logged-in user
        currentUser?.let { user ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF141414), RoundedCornerShape(12.dp))
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(Color(user.avatarColor), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.displayName.take(2).uppercase(),
                        color = DeepBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = user.displayName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = user.email,
                        color = Color(0xFF888888),
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu items
        Text(
            text = "NAVIGATION",
            color = Color(0xFF666666),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
        )

        val navItems = listOf(
            Triple(Screen.Dashboard, Icons.Default.Home, "Group Chat Circles"),
            Triple(Screen.Settings, Icons.Default.Book, "Scripture Reading"),
            Triple(Screen.Notifications, Icons.Default.Notifications, "Notifications"),
            Triple(Screen.StudyTracker, Icons.Default.TrendingUp, "Study Tracker"),
            Triple(Screen.Profile, Icons.Default.Person, "Profile & Settings"),
            Triple(Screen.DeveloperHub, Icons.Default.Terminal, "Developer Hub")
        )

        navItems.forEachIndexed { index, (screen, icon, label) ->
            val isSelected = activeScreen == screen
            val delay = index * 50
            val duration = animationSpeed

            val alpha by animateFloatAsState(
                targetValue = if (triggerAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = duration, delayMillis = delay, easing = FastOutSlowInEasing),
                label = "alpha"
            )

            val offsetX by animateDpAsState(
                targetValue = if (triggerAnimation) {
                    0.dp
                } else {
                    when (sidebarTransitionType) {
                        "Slide & Fade" -> (-40).dp
                        "Scale & Pop" -> 0.dp
                        "Bounce Accent" -> (-20).dp
                        else -> (-40).dp
                    }
                },
                animationSpec = if (sidebarTransitionType == "Bounce Accent" && triggerAnimation) {
                    spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
                } else {
                    tween(durationMillis = duration, delayMillis = delay, easing = FastOutSlowInEasing)
                },
                label = "offset"
            )

            val scale by animateFloatAsState(
                targetValue = if (triggerAnimation) {
                    1f
                } else {
                    if (sidebarTransitionType == "Scale & Pop") 0.8f else 1f
                },
                animationSpec = if (sidebarTransitionType == "Scale & Pop" && triggerAnimation) {
                    spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
                } else {
                    tween(durationMillis = duration, delayMillis = delay)
                },
                label = "scale"
            )

            NavigationDrawerItem(
                label = { Text(label, fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                selected = isSelected,
                onClick = { onTabSelected(screen) },
                icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp)) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0x22C9A063),
                    selectedIconColor = GoldPrimary,
                    selectedTextColor = GoldPrimary,
                    unselectedContainerColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF888888),
                    unselectedTextColor = Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .graphicsLayer {
                        this.alpha = alpha
                        this.scaleX = scale
                        this.scaleY = scale
                    }
                    .offset(x = offsetX)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(color = Color(0x1AFFFFFF), thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        // Logout
        NavigationDrawerItem(
            label = { Text("Log Out", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
            selected = false,
            onClick = onLogout,
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Log Out", modifier = Modifier.size(20.dp)) },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                unselectedIconColor = Color(0xFFFF5555),
                unselectedTextColor = Color(0xFFFF5555)
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
fun LuminaHashNavHost(
    navController: NavHostController,
    currentScreen: Screen,
    viewModel: LuminaViewModel,
    onMenuClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(currentScreen) {
        val targetRoute = currentScreen.hashRoute
        if (navController.currentDestination?.route != targetRoute) {
            navController.navigate(targetRoute) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.hashRoute,
        modifier = modifier.testTag("hash_router_nav_host")
    ) {
        // 1. Bible Reader View (#reader / #scripture)
        composable(Screen.Settings.hashRoute) {
            ScriptureScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }
        composable("#scripture") {
            ScriptureScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }

        // 2. Group Management View (#groups / #dashboard)
        composable(Screen.Dashboard.hashRoute) {
            DashboardScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }
        composable("#dashboard") {
            DashboardScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }

        // 3. Real-Time Chat View (#chat / #meeting)
        composable(Screen.MeetingRoom.hashRoute) {
            MeetingRoomScreen(viewModel = viewModel)
        }
        composable("#meeting") {
            MeetingRoomScreen(viewModel = viewModel)
        }

        // 4. Auxiliary App Screens
        composable(Screen.CreateCircle.hashRoute) {
            CreateCircleScreen(viewModel = viewModel)
        }
        composable(Screen.Profile.hashRoute) {
            ProfileScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }
        composable(Screen.DeveloperHub.hashRoute) {
            DeveloperHubScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }
        composable(Screen.Notifications.hashRoute) {
            NotificationsScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }
        composable(Screen.StudyTracker.hashRoute) {
            StudyTrackerScreen(viewModel = viewModel, onMenuClick = onMenuClick)
        }
        composable(Screen.Welcome.hashRoute) {
            WelcomeScreen(viewModel = viewModel)
        }
        composable(Screen.Login.hashRoute) {
            LoginScreen(viewModel = viewModel)
        }
        composable(Screen.Register.hashRoute) {
            RegisterScreen(viewModel = viewModel)
        }
    }
}
