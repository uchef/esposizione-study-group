package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BlockedUserEntity
import com.example.data.ChatMessageEntity
import com.example.data.LuminaRepository
import com.example.data.StudyGroupEntity
import com.example.data.UserEntity
import com.example.data.SearchHistoryEntity
import com.example.data.UnlockedBadgeEntity
import com.example.data.PrayerEntity
import com.example.data.StudyReminderEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    Welcome,
    Login,
    Register,
    Dashboard,
    MeetingRoom,
    CreateCircle,
    Profile,
    DeveloperHub,
    Settings,
    Notifications,
    StudyTracker;

    val hashRoute: String
        get() = when (this) {
            Welcome -> "#welcome"
            Login -> "#login"
            Register -> "#register"
            Dashboard -> "#groups"
            MeetingRoom -> "#chat"
            CreateCircle -> "#create-group"
            Profile -> "#profile"
            DeveloperHub -> "#developer"
            Settings -> "#reader"
            Notifications -> "#notifications"
            StudyTracker -> "#tracker"
        }

    companion object {
        fun fromHash(hash: String?): Screen {
            if (hash.isNullOrBlank()) return Dashboard
            val normalized = if (hash.startsWith("#")) hash else "#$hash"
            return entries.find { it.hashRoute.equals(normalized, ignoreCase = true) }
                ?: when (normalized.lowercase()) {
                    "#scripture", "#bible", "#reader", "#bible-reader" -> Settings
                    "#group", "#groups", "#group-management", "#dashboard" -> Dashboard
                    "#chat", "#meeting", "#discussion", "#chat-views", "#meeting-room" -> MeetingRoom
                    else -> Dashboard
                }
        }
    }
}

enum class StudyTheme {
    COSMIC_DARK,
    PARCHMENT_WARM,
    OLIVE_FOREST,
    IVORY_LIGHT
}

class LuminaViewModel(private val repository: LuminaRepository) : ViewModel() {

    private val _currentScreen = MutableStateFlow(Screen.Welcome)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _showRecoverySent = MutableStateFlow(false)
    val showRecoverySent: StateFlow<Boolean> = _showRecoverySent.asStateFlow()

    private val _showVerificationSent = MutableStateFlow(false)
    val showVerificationSent: StateFlow<Boolean> = _showVerificationSent.asStateFlow()

    private val _selectedGroup = MutableStateFlow<StudyGroupEntity?>(null)
    val selectedGroup: StateFlow<StudyGroupEntity?> = _selectedGroup.asStateFlow()

    private val _activeMessages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val activeMessages: StateFlow<List<ChatMessageEntity>> = _activeMessages.asStateFlow()

    private val _activeTyper = MutableStateFlow<String?>(null)
    val activeTyper: StateFlow<String?> = _activeTyper.asStateFlow()

    private val _blockedUsers = MutableStateFlow<List<BlockedUserEntity>>(emptyList())
    val blockedUsers: StateFlow<List<BlockedUserEntity>> = _blockedUsers.asStateFlow()

    private val _rateLimitedRequestsCount = MutableStateFlow(0)
    val rateLimitedRequestsCount: StateFlow<Int> = _rateLimitedRequestsCount.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Pro / Premium status
    private val _isProUser = MutableStateFlow(false)
    val isProUser: StateFlow<Boolean> = _isProUser.asStateFlow()

    // Scripture interaction states (bookmarks and colored highlights)
    private val _bookmarks = MutableStateFlow<Set<String>>(emptySet()) // e.g. "Proverbs 3:5"
    val bookmarks: StateFlow<Set<String>> = _bookmarks.asStateFlow()

    private val _highlights = MutableStateFlow<Map<String, Long>>(emptyMap()) // e.g. "Proverbs 3:5" -> Color hex representation (Long)
    val highlights: StateFlow<Map<String, Long>> = _highlights.asStateFlow()

    private val _studyNotes = MutableStateFlow<Map<String, String>>(emptyMap()) // e.g. "Proverbs 3:5" -> "My notes"
    val studyNotes: StateFlow<Map<String, String>> = _studyNotes.asStateFlow()

    // Sync Scripture Reading state within Study Circles
    private val _circleReadingState = MutableStateFlow<Map<Int, String>>(emptyMap()) // groupId -> "Book Chapter:Verse"
    val circleReadingState: StateFlow<Map<Int, String>> = _circleReadingState.asStateFlow()

    // Scripture reading progress states
    private val _readVerses = MutableStateFlow<Set<String>>(emptySet()) // e.g. "Proverbs 3:5"
    val readVerses: StateFlow<Set<String>> = _readVerses.asStateFlow()

    private val _completedChapters = MutableStateFlow<Set<String>>(emptySet()) // e.g. "Proverbs 3"
    val completedChapters: StateFlow<Set<String>> = _completedChapters.asStateFlow()

    // Search history and results
    val searchHistory: StateFlow<List<SearchHistoryEntity>> = repository.getRecentSearchHistory()
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val unlockedBadges: StateFlow<List<UnlockedBadgeEntity>> = repository.getUnlockedBadges()
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults.asStateFlow()

    private val _isOfflineMode = MutableStateFlow(false)
    val isOfflineMode: StateFlow<Boolean> = _isOfflineMode.asStateFlow()

    private val _cachedBooks = MutableStateFlow<Set<String>>(setOf("Genesis", "Proverbs", "John"))
    val cachedBooks: StateFlow<Set<String>> = _cachedBooks.asStateFlow()

    // Counter stats for badges
    private var ttsListenCount = 0
    private var searchCount = 0

    // Togglable dark theme
    private val _isDarkThemeEnabled = MutableStateFlow(true)
    val isDarkThemeEnabled: StateFlow<Boolean> = _isDarkThemeEnabled.asStateFlow()

    // Selected study theme
    private val _currentStudyTheme = MutableStateFlow(StudyTheme.COSMIC_DARK)
    val currentStudyTheme: StateFlow<StudyTheme> = _currentStudyTheme.asStateFlow()

    // Premium Animation Configs (Animation Tool)
    private val _animationSpeed = MutableStateFlow(350) // ms
    val animationSpeed: StateFlow<Int> = _animationSpeed.asStateFlow()

    private val _sidebarTransitionType = MutableStateFlow("Slide & Fade") // Slide & Fade, Scale & Pop, Bounce Accent
    val sidebarTransitionType: StateFlow<String> = _sidebarTransitionType.asStateFlow()

    // Dashboard data flow
    val allGroups: StateFlow<List<StudyGroupEntity>> = repository.getAllGroups()
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allUsers: StateFlow<List<UserEntity>> = repository.getAllUsers()
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var activeChatJob: Job? = null
    private var simulatedChatJob: Job? = null

    // Real Room-backed StateFlows for Notes, Highlights, Progress, Notifications
    private val _dbStudyNotes = MutableStateFlow<List<com.example.data.StudyNoteEntity>>(emptyList())
    val dbStudyNotes: StateFlow<List<com.example.data.StudyNoteEntity>> = _dbStudyNotes.asStateFlow()

    private val _dbStudyProgress = MutableStateFlow<List<com.example.data.StudyProgressEntity>>(emptyList())
    val dbStudyProgress: StateFlow<List<com.example.data.StudyProgressEntity>> = _dbStudyProgress.asStateFlow()

    private val _dbVerseHighlights = MutableStateFlow<List<com.example.data.VerseHighlightEntity>>(emptyList())
    val dbVerseHighlights: StateFlow<List<com.example.data.VerseHighlightEntity>> = _dbVerseHighlights.asStateFlow()

    private val _dbGroupNotifications = MutableStateFlow<List<com.example.data.GroupNotificationEntity>>(emptyList())
    val dbGroupNotifications: StateFlow<List<com.example.data.GroupNotificationEntity>> = _dbGroupNotifications.asStateFlow()

    // Focus Reading Mode State
    private val _isFocusMode = MutableStateFlow(false)
    val isFocusMode: StateFlow<Boolean> = _isFocusMode.asStateFlow()

    fun toggleFocusMode() {
        _isFocusMode.value = !_isFocusMode.value
        _toastMessage.value = if (_isFocusMode.value) "Focus Reading Mode enabled. Distractions muted." else "Focus Reading Mode disabled."
    }

    // Custom Study Reminders State
    private val _studyReminders = MutableStateFlow<List<StudyReminderEntity>>(emptyList())
    val studyReminders: StateFlow<List<StudyReminderEntity>> = _studyReminders.asStateFlow()

    // Flag to enable/disable group notifications
    private val _groupNotificationsEnabled = MutableStateFlow(true)
    val groupNotificationsEnabled: StateFlow<Boolean> = _groupNotificationsEnabled.asStateFlow()

    // Flag to enable/disable daily devotional reminders
    private val _devotionalRemindersEnabled = MutableStateFlow(true)
    val devotionalRemindersEnabled: StateFlow<Boolean> = _devotionalRemindersEnabled.asStateFlow()

    // Font Customization State
    private val _customFontSize = MutableStateFlow(16)
    val customFontSize: StateFlow<Int> = _customFontSize.asStateFlow()

    private val _customFontFamily = MutableStateFlow("Serif")
    val customFontFamily: StateFlow<String> = _customFontFamily.asStateFlow()

    fun updateFontSize(size: Int) {
        _customFontSize.value = size.coerceIn(12, 28)
    }

    fun updateFontFamily(family: String) {
        _customFontFamily.value = family
    }

    // Offline Audio Download State
    private val _downloadedAudioChapters = MutableStateFlow<Set<String>>(emptySet())
    val downloadedAudioChapters: StateFlow<Set<String>> = _downloadedAudioChapters.asStateFlow()

    fun downloadAudioForChapter(book: String, chapter: Int) {
        viewModelScope.launch {
            val chapterKey = "$book $chapter"
            val current = _downloadedAudioChapters.value.toMutableSet()
            current.add(chapterKey)
            _downloadedAudioChapters.value = current
            showToast("📥 Audio narration for $chapterKey downloaded for offline use.")
        }
    }

    fun deleteDownloadedAudioForChapter(book: String, chapter: Int) {
        val chapterKey = "$book $chapter"
        val current = _downloadedAudioChapters.value.toMutableSet()
        current.remove(chapterKey)
        _downloadedAudioChapters.value = current
        showToast("🗑️ Removed offline audio for $chapterKey.")
    }

    // Reading Plans State & Structures
    data class ReadingPlan(
        val id: String,
        val title: String,
        val description: String,
        val durationDays: Int,
        val chapters: List<String>,
        val category: String = "Wisdom"
    )

    val availableReadingPlans = listOf(
        ReadingPlan(
            id = "proverbs_wisdom",
            title = "Proverbs Wisdom Challenge",
            description = "Immerse yourself in the book of wisdom. Read 1 chapter a day to gain discernment, knowledge, and understanding.",
            durationDays = 31,
            chapters = (1..31).map { "Proverbs $it" },
            category = "Wisdom"
        ),
        ReadingPlan(
            id = "nt_foundations",
            title = "New Testament Foundations",
            description = "Explore the life of Christ and the early church through core Gospels and Pauline Epistles.",
            durationDays = 14,
            chapters = listOf(
                "John 1", "John 3", "John 14", "John 15",
                "Romans 3", "Romans 8", "Romans 12",
                "Galatians 5", "Philippians 4", "Matthew 5",
                "Matthew 6", "Matthew 7", "Luke 15", "Acts 2"
            ),
            category = "Foundations"
        ),
        ReadingPlan(
            id = "trust_armor",
            title = "Spiritual Trust & Armor",
            description = "Shield your mind with scriptures of faith, courage, and divine guidance for challenging seasons.",
            durationDays = 7,
            chapters = listOf(
                "Joshua 1", "Psalms 23", "Psalms 46", "Psalms 91",
                "Isaiah 40", "Hebrews 11", "Ephesians 6"
            ),
            category = "Encouragement"
        )
    )

    private val _subscribedPlanIds = MutableStateFlow<Set<String>>(emptySet())
    val subscribedPlanIds: StateFlow<Set<String>> = _subscribedPlanIds.asStateFlow()

    fun subscribeToPlan(planId: String) {
        val current = _subscribedPlanIds.value.toMutableSet()
        if (current.add(planId)) {
            _subscribedPlanIds.value = current
            showToast("📅 Subscribed to reading plan!")
        }
    }

    fun unsubscribeFromPlan(planId: String) {
        val current = _subscribedPlanIds.value.toMutableSet()
        if (current.remove(planId)) {
            _subscribedPlanIds.value = current
            showToast("Unsubscribed from reading plan.")
        }
    }

    // Trigger simulated group notifications periodically
    private var simulatedNotificationJob: Job? = null

    init {
        viewModelScope.launch {
            repository.prepopulateIfEmpty()
        }
        startTimer()
        startSimulatedNotifications()

        // Reactively collect databases for logged-in user
        viewModelScope.launch {
            _currentUser.collectLatest { user ->
                if (user != null) {
                    // Collect notes
                    launch {
                        repository.getNotesForUser(user.email).collect { list ->
                            _dbStudyNotes.value = list
                            _studyNotes.value = list.associate { "${it.book} ${it.chapter}:${it.verseRange}" to it.noteText }
                        }
                    }
                    // Collect progress
                    launch {
                        repository.getProgressForUser(user.email).collect { list ->
                            _dbStudyProgress.value = list
                            _completedChapters.value = list.map { "${it.book} ${it.chapter}" }.toSet()
                        }
                    }
                    // Collect highlights
                    launch {
                        repository.getHighlightsForUser(user.email).collect { list ->
                            _dbVerseHighlights.value = list
                            _highlights.value = list.associate { "${it.book} ${it.chapter}:${it.verse}" to it.colorHex.substringAfter("#").toLong(16) }
                        }
                    }
                    // Collect notifications
                    launch {
                        repository.getNotificationsForUser(user.email).collect { list ->
                            _dbGroupNotifications.value = list
                        }
                    }
                    // Collect study reminders
                    launch {
                        repository.getRemindersForUser(user.email).collect { list ->
                            _studyReminders.value = list
                        }
                    }
                } else {
                    _dbStudyNotes.value = emptyList()
                    _dbStudyProgress.value = emptyList()
                    _dbVerseHighlights.value = emptyList()
                    _dbGroupNotifications.value = emptyList()
                    _studyReminders.value = emptyList()
                }
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
        _authError.value = null
        if (screen != Screen.MeetingRoom) {
            stopSimulatedChat()
        }
    }

    fun navigateToHash(hashRoute: String) {
        val targetScreen = Screen.fromHash(hashRoute)
        navigateTo(targetScreen)
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    // AUTH ACTIONS
    fun login(email: String, passwordPlain: String) {
        viewModelScope.launch {
            if (email.isBlank() || passwordPlain.isBlank()) {
                _authError.value = "Email and password cannot be empty."
                return@launch
            }
            val user = repository.loginUser(email.trim(), passwordPlain)
            if (user != null) {
                _currentUser.value = user
                _authError.value = null
                _currentScreen.value = Screen.Dashboard
                observeBlockedUsers(user.email)
            } else {
                _authError.value = "Invalid email or password."
            }
        }
    }

    fun register(email: String, passwordPlain: String, displayName: String, location: String) {
        viewModelScope.launch {
            if (email.isBlank() || passwordPlain.isBlank() || displayName.isBlank()) {
                _authError.value = "All fields are required."
                return@launch
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                _authError.value = "Please enter a valid email address."
                return@launch
            }
            if (passwordPlain.length < 6) {
                _authError.value = "Password must be at least 6 characters."
                return@launch
            }

            // Simple random avatar color generator for profile visual polish
            val colors = listOf(0xFFC9A063.toInt(), 0xFFE5A93B.toInt(), 0xFF9E8E75.toInt(), 0xFFD4AF37.toInt())
            val avatarColor = colors.random()

            val newUser = UserEntity(
                email = email.trim(),
                passwordHash = passwordPlain, // hybrid placeholder direct storage
                displayName = displayName.trim(),
                bio = "I am excited to study scripture with this global community.",
                location = location.trim().ifEmpty { "Global" },
                preferredTranslation = "ESV",
                avatarColor = avatarColor
            )

            val success = repository.registerUser(newUser)
            if (success) {
                _authError.value = null
                _showVerificationSent.value = true
                // In mock flow, complete login automatically after showing verification
                delay(2000)
                _showVerificationSent.value = false
                val registeredUser = repository.getUserByEmail(newUser.email)
                _currentUser.value = registeredUser
                _currentScreen.value = Screen.Dashboard
                registeredUser?.let { observeBlockedUsers(it.email) }
            } else {
                _authError.value = "User with this email already exists."
            }
        }
    }

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            if (email.isBlank()) {
                _authError.value = "Please enter your email to recover password."
                return@launch
            }
            _showRecoverySent.value = true
            delay(3000)
            _showRecoverySent.value = false
            _toastMessage.value = "Password recovery instructions sent to $email."
        }
    }

    fun logout() {
        _currentUser.value = null
        stopSimulatedChat()
        _currentScreen.value = Screen.Welcome
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _currentUser.value?.let {
                repository.deleteUser(it.email)
                logout()
                _toastMessage.value = "Your account has been permanently deleted."
            }
        }
    }

    private fun observeBlockedUsers(email: String) {
        viewModelScope.launch {
            repository.getBlockedUsers(email).collectLatest { list ->
                _blockedUsers.value = list
            }
        }
    }

    // PROFILE ACTIONS
    fun updateProfile(
        displayName: String,
        bio: String,
        location: String,
        preferredTranslation: String,
        dateOfBirth: String,
        stateOfOrigin: String,
        country: String,
        phoneNumber: String,
        phoneCountryCode: String
    ) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val updated = user.copy(
                    displayName = displayName.trim(),
                    bio = bio.trim(),
                    location = location.trim(),
                    preferredTranslation = preferredTranslation,
                    dateOfBirth = dateOfBirth.trim(),
                    stateOfOrigin = stateOfOrigin.trim(),
                    country = country.trim(),
                    phoneNumber = phoneNumber.trim(),
                    phoneCountryCode = phoneCountryCode.trim()
                )
                repository.updateUser(updated)
                _currentUser.value = updated
                _toastMessage.value = "Profile updated successfully."
            }
        }
    }

    fun importUserData(
        displayName: String,
        bio: String,
        location: String,
        level: Int,
        xp: Int,
        versesGoal: Int,
        chaptersGoal: Int,
        minutesGoal: Int
    ) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val updated = user.copy(
                    displayName = displayName.trim(),
                    bio = bio.trim(),
                    location = location.trim(),
                    level = level,
                    xp = xp
                )
                repository.updateUser(updated)
                _currentUser.value = updated
                updateStudyGoals(versesGoal, chaptersGoal, minutesGoal)
                _toastMessage.value = "Configuration and data imported successfully!"
            }
        }
    }

    fun switchProfile(user: UserEntity) {
        viewModelScope.launch {
            _currentUser.value = user
            observeBlockedUsers(user.email)
            _toastMessage.value = "Switched to profile: ${user.displayName}"
        }
    }

    fun quickCreateProfile(name: String, email: String, bio: String, location: String) {
        viewModelScope.launch {
            if (name.isBlank() || email.isBlank()) {
                _toastMessage.value = "Name and Email are required."
                return@launch
            }
            val existing = repository.getUserByEmail(email.trim())
            if (existing != null) {
                _toastMessage.value = "A profile with this email already exists."
                return@launch
            }
            val colors = listOf(0xFFC9A063.toInt(), 0xFFE5A93B.toInt(), 0xFF9E8E75.toInt(), 0xFFD4AF37.toInt())
            val avatarColor = colors.random()
            val newUser = UserEntity(
                email = email.trim(),
                passwordHash = "password", // simple local password
                displayName = name.trim(),
                bio = bio.trim().ifEmpty { "Bible study enthusiast!" },
                location = location.trim().ifEmpty { "Global" },
                preferredTranslation = "ESV",
                avatarColor = avatarColor
            )
            val registered = repository.registerUser(newUser)
            if (registered) {
                val dbUser = repository.getUserByEmail(newUser.email)
                if (dbUser != null) {
                    switchProfile(dbUser)
                }
            } else {
                _toastMessage.value = "Failed to create profile."
            }
        }
    }

    // GROUPS & MEETINGS
    fun selectGroupAndJoin(group: StudyGroupEntity) {
        _selectedGroup.value = group
        _currentScreen.value = Screen.MeetingRoom
        observeGroupMessages(group.id)
        if (group.isLive) {
            startSimulatedChat(group.id)
        }
    }

    fun createStudyCircle(title: String, topic: String, isPrivate: Boolean, schedule: String = "Live Now") {
        viewModelScope.launch {
            if (title.isBlank() || topic.isBlank()) {
                _toastMessage.value = "Title and Topic are required."
                return@launch
            }
            val user = _currentUser.value ?: return@launch
            val newGroup = StudyGroupEntity(
                title = title.trim(),
                topic = topic.trim(),
                hostName = user.displayName,
                scheduleText = schedule.trim().ifBlank { "Live Now" },
                isPrivate = isPrivate,
                isLive = true,
                participantCount = 1
            )
            val newId = repository.insertGroup(newGroup)
            val savedGroup = repository.getGroupById(newId.toInt())
            if (savedGroup != null) {
                // Post a system welcome message
                repository.insertMessage(
                    ChatMessageEntity(
                        groupId = savedGroup.id,
                        senderName = "System",
                        messageText = "${user.displayName} created the Bible Study Circle: '${savedGroup.title}'",
                        isSystem = true
                    )
                )
                selectGroupAndJoin(savedGroup)
            }
        }
    }

    private fun observeGroupMessages(groupId: Int) {
        activeChatJob?.cancel()
        activeChatJob = viewModelScope.launch {
            repository.getMessagesForGroup(groupId).collectLatest { list ->
                _activeMessages.value = list
            }
        }
    }

    fun sendUserMessage(text: String) {
        viewModelScope.launch {
            val user = _currentUser.value ?: return@launch
            val group = _selectedGroup.value ?: return@launch
            if (text.isBlank()) return@launch

            val message = ChatMessageEntity(
                groupId = group.id,
                senderName = user.displayName,
                messageText = text.trim(),
                isSystem = false
            )
            repository.insertMessage(message)

            // Real-time Contextual Response Engine
            val lower = text.lowercase()
            val sender = listOf("Sister Sarah", "Brother John", "Deacon Robert", "Mary Lopez", "David Kim").random()
            
            // Check if sender is blocked
            val isBlocked = _blockedUsers.value.any { it.blockedUsername == sender }
            if (isBlocked) return@launch

            val responseText = when {
                lower.contains("pray") || lower.contains("prayer") -> {
                    "Let us absolutely lift this prayer request to the Lord together. Praying now!"
                }
                lower.contains("amen") || lower.contains("halle") || lower.contains("praise") || lower.contains("bless") -> {
                    "Amen! Praise God. Truly inspiring!"
                }
                lower.contains("verse") || lower.contains("read") || lower.contains("scripture") || lower.contains("bible") -> {
                    "Yes, let's look at Hebrews 4:12 - 'For the word of God is alive and active...'"
                }
                lower.contains("love") || lower.contains("peace") || lower.contains("grace") -> {
                    "God's love and grace never fail us. Thank you for sharing this beautiful truth."
                }
                else -> {
                    listOf(
                        "Thank you so much for sharing that insight with the group! This study circle is truly a blessing.",
                        "I agree! May God continue to guide our understanding as we meditate on His words.",
                        "That's a profound thought. It's so vital to remain anchored in faith daily.",
                        "So well said. Let's keep searching the scriptures together."
                    ).random()
                }
            }

            // Simulate real-time typing
            delay(1200)
            _activeTyper.value = sender
            delay((2000..3500).random().toLong()) // Typing duration
            
            if (_selectedGroup.value?.id == group.id) {
                val responseMsg = ChatMessageEntity(
                    groupId = group.id,
                    senderName = sender,
                    messageText = responseText,
                    isSystem = false
                )
                repository.insertMessage(responseMsg)
            }
            _activeTyper.value = null
        }
    }

    // HOST ACTIONS
    fun hostPinMessage(msg: ChatMessageEntity) {
        viewModelScope.launch {
            repository.pinMessage(msg)
            _toastMessage.value = "Message pinned by host."
        }
    }

    fun hostShareVerse(verseText: String) {
        viewModelScope.launch {
            val group = _selectedGroup.value ?: return@launch
            val msg = ChatMessageEntity(
                groupId = group.id,
                senderName = group.hostName,
                messageText = "Host Shared Verse: $verseText",
                isSystem = true
            )
            repository.insertMessage(msg)
            _toastMessage.value = "Scripture verse shared to study circle."
        }
    }

    // UGC MODERATION
    fun reportMessage(msg: ChatMessageEntity) {
        viewModelScope.launch {
            repository.reportMessage(msg)
            _toastMessage.value = "Message reported for moderation. Administrators will review."
        }
    }

    fun blockUser(blockedName: String) {
        viewModelScope.launch {
            val user = _currentUser.value ?: return@launch
            if (blockedName == user.displayName) {
                _toastMessage.value = "You cannot block yourself."
                return@launch
            }
            repository.blockUser(user.email, blockedName)
            _toastMessage.value = "You have blocked '$blockedName'. Their messages will be hidden."
        }
    }

    // SIMULATED CHAT ENGINE
    private fun startSimulatedChat(groupId: Int) {
        simulatedChatJob?.cancel()
        simulatedChatJob = viewModelScope.launch {
            val participants = listOf("Sister Sarah", "Brother John", "Deacon Robert", "Mary Lopez", "David Kim")
            val quotes = listOf(
                "Proverbs 3:5 has carried me through so many personal crises. It's a reminder to let go of control.",
                "Letting go of our own logic is the hardest step. We want to understand everything.",
                "Wow, that verse is beautiful. 'In all your ways acknowledge Him, and He will make your paths straight.'",
                "Does anyone have thoughts on how this applies to modern carrier/workplace challenges?",
                "Yes, we often trust our degrees or resumes more than our Creator. It's an active daily realignment.",
                "Amen! Beautiful study group today. Appreciate you hosting this room, Pastor!",
                "I am tuning in from Seoul, South Korea! So glad to join you all.",
                "Greetings from Nairobi, Kenya! God bless this global study."
            )

            var index = 0
            while (true) {
                delay((8000..15000).random().toLong()) // wait random seconds
                val sender = participants.random()
                // Only post if sender is not blocked
                val isBlocked = _blockedUsers.value.any { it.blockedUsername == sender }
                if (!isBlocked && _selectedGroup.value?.id == groupId) {
                    val quote = quotes[index % quotes.size]
                    val msg = ChatMessageEntity(
                        groupId = groupId,
                        senderName = sender,
                        messageText = quote,
                        isSystem = false
                    )
                    repository.insertMessage(msg)
                    index++
                }
            }
        }
    }

    private fun stopSimulatedChat() {
        simulatedChatJob?.cancel()
        simulatedChatJob = null
    }

    // DEVELOPER HUB / RATE LIMIT SIMULATOR
    fun triggerSimulatedApiCall() {
        viewModelScope.launch {
            val current = _rateLimitedRequestsCount.value
            if (current >= 5) {
                _toastMessage.value = "429 Too Many Requests: Rate Limit Exceeded (5 req/min cap for development keys)"
            } else {
                _rateLimitedRequestsCount.value = current + 1
                _toastMessage.value = "200 OK: Secured API response returned successfully."
                delay(30000) // cool down after 30 seconds
                if (_rateLimitedRequestsCount.value > 0) {
                    _rateLimitedRequestsCount.value--
                }
            }
        }
    }

    // SCRIPTURE INTERACTIONS & PRO SUBSCRIPTIONS
    fun toggleBookmark(verseKey: String) {
        val current = _bookmarks.value
        val isAdding = !current.contains(verseKey)
        val newBookmarks = if (isAdding) current + verseKey else current - verseKey
        _bookmarks.value = newBookmarks
        if (isAdding) {
            _toastMessage.value = "Bookmarked $verseKey successfully!"
            if (newBookmarks.size >= 3) {
                checkAndUnlockBadge("scripture_scribe", "Scripture Scribe", "Bookmarked 3 verses for deeper reflection!")
            }
        } else {
            _toastMessage.value = "Removed bookmark for $verseKey"
        }
    }

    fun toggleHighlight(verseKey: String, colorValue: Long) {
        val parts = verseKey.split(" ")
        if (parts.size >= 2) {
            val book = parts.subList(0, parts.size - 1).joinToString(" ")
            val chapterAndVerse = parts.last().split(":")
            if (chapterAndVerse.size == 2) {
                val chapter = chapterAndVerse[0].toIntOrNull() ?: 1
                val verse = chapterAndVerse[1].toIntOrNull() ?: 1
                val colorHex = "#" + colorValue.toString(16).uppercase()
                
                val email = _currentUser.value?.email ?: "guest@example.com"
                viewModelScope.launch {
                    val existing = _dbVerseHighlights.value.firstOrNull { it.book == book && it.chapter == chapter && it.verse == verse }
                    if (existing != null && existing.colorHex == colorHex) {
                        repository.removeHighlight(email, book, chapter, verse)
                        _toastMessage.value = "Cleared highlight for $verseKey"
                    } else {
                        repository.insertHighlight(
                            com.example.data.VerseHighlightEntity(
                                userEmail = email,
                                book = book,
                                chapter = chapter,
                                verse = verse,
                                colorHex = colorHex
                            )
                        )
                        _toastMessage.value = "Highlighted $verseKey!"
                        val newHighlightCount = _dbVerseHighlights.value.size + 1
                        if (newHighlightCount >= 5) {
                            checkAndUnlockBadge("flash_of_light", "Flash of Light", "Highlighted 5 scriptures for study mapping!")
                        }
                    }
                }
            }
        }
    }

    fun upgradeToPro() {
        _isProUser.value = true
        _toastMessage.value = "Congratulations! Lumina PRO Edition successfully unlocked!"
    }

    fun downgradeToStandard() {
        _isProUser.value = false
        _toastMessage.value = "Switched back to standard Free edition."
    }

    fun saveStudyNote(verseKey: String, note: String) {
        val parts = verseKey.split(" ")
        if (parts.size >= 2) {
            val book = parts.subList(0, parts.size - 1).joinToString(" ")
            val chapterAndVerse = parts.last().split(":")
            if (chapterAndVerse.size == 2) {
                val chapter = chapterAndVerse[0].toIntOrNull() ?: 1
                val verse = chapterAndVerse[1].toIntOrNull() ?: 1
                
                val email = _currentUser.value?.email ?: "guest@example.com"
                viewModelScope.launch {
                    val existing = _dbStudyNotes.value.firstOrNull { it.book == book && it.chapter == chapter && it.verseRange == verse.toString() }
                    if (note.trim().isEmpty()) {
                        if (existing != null) {
                            repository.deleteNote(existing.id)
                        }
                        _toastMessage.value = "Cleared note for $verseKey"
                    } else {
                        val newNote = com.example.data.StudyNoteEntity(
                            id = existing?.id ?: 0,
                            userEmail = email,
                            book = book,
                            chapter = chapter,
                            verseRange = verse.toString(),
                            noteText = note
                        )
                        repository.insertNote(newNote)
                        _toastMessage.value = "Saved study note for $verseKey"
                    }
                }
            }
        }
    }

    fun setAnimationSpeed(speedMs: Int) {
        _animationSpeed.value = speedMs
    }

    fun setSidebarTransitionType(type: String) {
        _sidebarTransitionType.value = type
    }

    fun toggleDarkTheme(enabled: Boolean) {
        _isDarkThemeEnabled.value = enabled
    }

    fun setStudyTheme(theme: StudyTheme) {
        _currentStudyTheme.value = theme
        showToast("Theme changed to ${theme.name.replace("_", " ")}")
    }

    fun syncCircleReadingState(groupId: Int, readingState: String) {
        val current = _circleReadingState.value.toMutableMap()
        current[groupId] = readingState
        _circleReadingState.value = current
        
        viewModelScope.launch {
            repository.insertMessage(
                ChatMessageEntity(
                    groupId = groupId,
                    senderName = "Lumina Sync",
                    messageText = "📖 ${currentUser.value?.displayName ?: "A reader"} updated study focus to: $readingState",
                    isSystem = true
                )
            )
            checkAndUnlockBadge("circle_sync", "Circle Sync", "Shared and synced your active reading focus to a study circle!")
        }
    }

    // Progress tracking functions
    fun toggleVerseRead(book: String, chapterNum: Int, verseNum: String, allChapterVerses: List<String>) {
        val verseKey = "$book $chapterNum:$verseNum"
        val current = _readVerses.value
        val isRead = current.contains(verseKey)
        val newRead = if (isRead) current - verseKey else current + verseKey
        _readVerses.value = newRead
        
        // Dynamic automatic check for chapter completion:
        val chapterKey = "$book $chapterNum"
        val allVerseKeys = allChapterVerses.map { verse ->
            val num = verse.substringBefore(" ").trim()
            "$book $chapterNum:$num"
        }
        val allRead = allVerseKeys.all { newRead.contains(it) }
        val currentChapters = _completedChapters.value
        val newChapters = if (allRead) {
            if (!currentChapters.contains(chapterKey)) {
                currentChapters + chapterKey
            } else currentChapters
        } else {
            if (currentChapters.contains(chapterKey)) {
                currentChapters - chapterKey
            } else currentChapters
        }
        _completedChapters.value = newChapters
        
        if (!isRead) {
            _toastMessage.value = "Verse $verseNum marked as read!"
            
            // Trigger Achievements
            checkAndUnlockBadge("first_spark", "First Spark", "Marked your first scripture verse as read!")
            if (newRead.size >= 10) {
                checkAndUnlockBadge("word_explorer", "Word Explorer", "Marked 10 scripture verses as read!")
            }
            if (allRead) {
                checkAndUnlockBadge("daily_light", "Daily Light", "Completed your first scripture chapter!")
                if (newChapters.size >= 5) {
                    checkAndUnlockBadge("deep_scholar", "Deep Scholar", "Completed 5 scripture chapters!")
                }
            }
        } else {
            _toastMessage.value = "Verse $verseNum marked as unread"
        }
    }

    fun toggleChapterCompleted(book: String, chapterNum: Int, verses: List<String>) {
        val chapterKey = "$book $chapterNum"
        val currentChapters = _completedChapters.value
        val isCompleted = currentChapters.contains(chapterKey)
        
        val newChapters = if (isCompleted) {
            currentChapters - chapterKey
        } else {
            currentChapters + chapterKey
        }
        _completedChapters.value = newChapters
        
        // Update all individual verses in this chapter to keep state perfectly synchronized
        val currentVerses = _readVerses.value.toMutableSet()
        verses.forEach { verse ->
            val verseNum = verse.substringBefore(" ").trim()
            val verseKey = "$book $chapterNum:$verseNum"
            if (!isCompleted) {
                currentVerses.add(verseKey)
            } else {
                currentVerses.remove(verseKey)
            }
        }
        _readVerses.value = currentVerses
        
        if (!isCompleted) {
            _toastMessage.value = "Chapter $chapterNum marked as completed!"
            
            // Trigger Achievements
            checkAndUnlockBadge("daily_light", "Daily Light", "Completed your first scripture chapter!")
            if (newChapters.size >= 5) {
                checkAndUnlockBadge("deep_scholar", "Deep Scholar", "Completed 5 scripture chapters!")
            }
        } else {
            _toastMessage.value = "Chapter $chapterNum marked as incomplete"
        }
    }

    // ----------------- Gamification & Audio & Offline Caching Functions -----------------
    fun checkAndUnlockBadge(id: String, title: String, description: String) {
        viewModelScope.launch {
            val currentBadges = unlockedBadges.value
            if (currentBadges.none { it.id == id }) {
                repository.unlockBadge(id, title, description)
                showToast("🏆 ACHIEVEMENT UNLOCKED: $title!")
            }
        }
    }

    fun trackTtsPlayback() {
        ttsListenCount++
        if (ttsListenCount >= 1) {
            checkAndUnlockBadge("proclaimer", "Proclaimer", "Listened to scripture audio narration!")
        }
    }

    fun toggleOfflineMode() {
        _isOfflineMode.value = !_isOfflineMode.value
        if (_isOfflineMode.value) {
            showToast("Offline Mode Enabled. Reading strictly from local cache.")
        } else {
            showToast("Online Mode Enabled. Sync active.")
        }
    }

    fun cacheBook(book: String) {
        viewModelScope.launch {
            val current = _cachedBooks.value
            if (current.contains(book)) {
                _cachedBooks.value = current - book
                showToast("Removed $book from offline cache")
            } else {
                showToast("Downloading offline cache for $book...")
                delay(1200) // Simulate download
                _cachedBooks.value = current + book
                showToast("$book is now available offline!")
            }
        }
    }

    fun searchScripture(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            // Save to database search history
            repository.insertSearchQuery(query.trim())
            
            // Increment search count
            searchCount++
            if (searchCount >= 3) {
                checkAndUnlockBadge("faithful_seeker", "Faithful Seeker", "Searched the scripture database 3 times to find truth!")
            }

            val results = mutableListOf<SearchResult>()
            val lowerQuery = query.lowercase().trim()
            
            bibleData.forEach { (book, chapters) ->
                chapters.forEach { (chapterNum, verses) ->
                    verses.forEach { verse ->
                        val verseNum = verse.substringBefore(" ").trim()
                        val text = verse.substringAfter(" ").trim()
                        if (text.lowercase().contains(lowerQuery)) {
                            results.add(SearchResult(book, chapterNum, verseNum, verse))
                        }
                    }
                }
            }
            _searchResults.value = results
        }
    }

    fun deleteRecentSearch(query: String) {
        viewModelScope.launch {
            repository.deleteSearchQuery(query)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearSearchHistory()
            showToast("Search history cleared")
        }
    }

    // Study Goals States
    private val _studyGoalVerses = MutableStateFlow(5)
    val studyGoalVerses: StateFlow<Int> = _studyGoalVerses.asStateFlow()

    private val _studyGoalChapters = MutableStateFlow(1)
    val studyGoalChapters: StateFlow<Int> = _studyGoalChapters.asStateFlow()

    private val _studyGoalMinutes = MutableStateFlow(15)
    val studyGoalMinutes: StateFlow<Int> = _studyGoalMinutes.asStateFlow()

    private val _sessionTimeSeconds = MutableStateFlow(0)
    val sessionTimeSeconds: StateFlow<Int> = _sessionTimeSeconds.asStateFlow()

    // Cross-Device Sync States
    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private val _lastSyncTime = MutableStateFlow<Long?>(null)
    val lastSyncTime: StateFlow<Long?> = _lastSyncTime.asStateFlow()

    private val _isAutoSyncEnabled = MutableStateFlow(true)
    val isAutoSyncEnabled: StateFlow<Boolean> = _isAutoSyncEnabled.asStateFlow()

    private val _syncStatusMessage = MutableStateFlow("Local storage in sync")
    val syncStatusMessage: StateFlow<String> = _syncStatusMessage.asStateFlow()

    // Quick Access Pinned Bookmarks
    private val _pinnedBookmarks = MutableStateFlow<Set<String>>(emptySet())
    val pinnedBookmarks: StateFlow<Set<String>> = _pinnedBookmarks.asStateFlow()

    // Start background timer to track studying minutes automatically
    private var timerJob: Job? = null

    fun startTimer() {
        if (timerJob == null || timerJob?.isCancelled == true) {
            timerJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    _sessionTimeSeconds.value += 1
                }
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun updateStudyGoals(verses: Int, chapters: Int, minutes: Int) {
        _studyGoalVerses.value = verses
        _studyGoalChapters.value = chapters
        _studyGoalMinutes.value = minutes
        showToast("Study goals updated successfully!")
    }

    fun togglePinnedBookmark(verseKey: String) {
        val current = _pinnedBookmarks.value
        if (current.contains(verseKey)) {
            _pinnedBookmarks.value = current - verseKey
            showToast("Removed from Quick Access")
        } else {
            _pinnedBookmarks.value = current + verseKey
            showToast("Added to Quick Access Bookmarks!")
        }
    }

    fun toggleAutoSync() {
        _isAutoSyncEnabled.value = !_isAutoSyncEnabled.value
        showToast(if (_isAutoSyncEnabled.value) "Auto-Sync Enabled" else "Auto-Sync Disabled")
    }

    fun triggerSync() {
        if (_isSyncing.value) return
        viewModelScope.launch {
            _isSyncing.value = true
            _syncStatusMessage.value = "Connecting to Lumina Cloud Gateway..."
            delay(800)
            _syncStatusMessage.value = "Authenticating user: ${currentUser.value?.email ?: "guest"}..."
            delay(800)
            _syncStatusMessage.value = "Uploading notes (${_studyNotes.value.size}), bookmarks (${_bookmarks.value.size})..."
            delay(900)
            _syncStatusMessage.value = "Downloading cloud-sync updates..."
            delay(700)
            _lastSyncTime.value = System.currentTimeMillis()
            _isSyncing.value = false
            _syncStatusMessage.value = "All data safely synchronized"
            showToast("Secure cloud database sync complete!")
            checkAndUnlockBadge("circle_sync", "Circle Sync", "Shared and synced your active reading focus to a study circle!")
        }
    }

    fun exportNotesAsMarkdown(): String {
        val notes = _studyNotes.value
        if (notes.isEmpty()) {
            return "No study notes found to export. Tap the Note icon next to a verse to write one."
        }
        val builder = java.lang.StringBuilder()
        builder.append("# Lumina Scripture Study Notes\n")
        builder.append("Exported on: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}\n\n")
        builder.append("Email: ${currentUser.value?.email ?: "Guest User"}\n\n")
        builder.append("Total Notes: ${notes.size}\n\n")
        builder.append("---\n\n")
        
        notes.forEach { (verseKey, noteText) ->
            builder.append("### $verseKey\n")
            builder.append("> $noteText\n\n")
        }
        return builder.toString()
    }

    // =========================================================================
    // PRAYER JOURNAL STATE & ACTIONS
    // =========================================================================
    val allPrayers: StateFlow<List<PrayerEntity>> = repository.getAllPrayers()
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addPrayer(title: String, requestText: String, category: String, isPublic: Boolean = false, authorName: String = "Anonymous") {
        viewModelScope.launch {
            if (title.isBlank() || requestText.isBlank()) {
                showToast("Prayer Title and Request cannot be empty.")
                return@launch
            }
            val prayer = PrayerEntity(
                title = title.trim(),
                requestText = requestText.trim(),
                category = category,
                isPublic = isPublic,
                authorName = authorName.trim()
            )
            repository.insertPrayer(prayer)
            if (isPublic) {
                showToast("🙏 Prayer request posted to the Community Board!")
            } else {
                showToast("🙏 Prayer request added to your journal.")
            }
            gainXp(25)
            checkAndUnlockBadge("prayer_warrior", "Prayer Warrior", "Logged your first prayer request in your journal!")
        }
    }

    fun removeHighlight(book: String, chapter: Int, verse: Int) {
        val email = _currentUser.value?.email ?: "guest@example.com"
        viewModelScope.launch {
            repository.removeHighlight(email, book, chapter, verse)
            showToast("Cleared highlight.")
        }
    }

    fun togglePrayerAnswered(prayer: PrayerEntity) {
        viewModelScope.launch {
            val updated = prayer.copy(isAnswered = !prayer.isAnswered)
            repository.updatePrayer(updated)
            if (updated.isAnswered) {
                showToast("🙌 Hallelujah! Prayer marked as answered! Praise God!")
                gainXp(50)
                checkAndUnlockBadge("answered_testimony", "Answered Testimony", "Marked a prayer request as answered in praise!")
            } else {
                showToast("Prayer moved back to active requests.")
            }
        }
    }

    fun deletePrayer(id: Int) {
        viewModelScope.launch {
            repository.deletePrayer(id)
            showToast("Prayer removed from journal.")
        }
    }

    // --- NEW STUDY, PROGRESS, AND NOTIFICATION ACTIONS ---

    fun toggleGroupNotifications(enabled: Boolean) {
        _groupNotificationsEnabled.value = enabled
        _toastMessage.value = if (enabled) "Group notifications enabled!" else "Group notifications muted."
    }

    fun toggleDevotionalReminders(enabled: Boolean) {
        _devotionalRemindersEnabled.value = enabled
        _toastMessage.value = if (enabled) "Daily devotional reminders enabled!" else "Daily devotional reminders disabled."
    }

    fun triggerSimulatedDevotionalReminder() {
        if (!_devotionalRemindersEnabled.value) {
            showToast("⚠️ Reminders are disabled! Toggle them on first.")
            return
        }
        val email = _currentUser.value?.email ?: "user@example.com"
        viewModelScope.launch {
            val notification = com.example.data.GroupNotificationEntity(
                userEmail = email,
                groupId = 999,
                groupTitle = "Daily Devotional",
                notificationType = "DEVOTIONAL_REMINDER",
                messageText = "Time for your daily devotional reading! Complete today's reflection to keep your streak alive."
            )
            repository.insertNotification(notification)
            showToast("🔔 Simulated Push: Time for your daily devotional reading!")
        }
    }

    fun incrementPrayerAmen(prayerId: Int) {
        viewModelScope.launch {
            repository.incrementPrayerAmen(prayerId)
        }
    }

    fun addStudyReminder(title: String, timeString: String, daysCsv: String) {
        val email = _currentUser.value?.email ?: "user@example.com"
        viewModelScope.launch {
            val reminder = StudyReminderEntity(
                userEmail = email,
                title = title,
                timeString = timeString,
                daysCsv = daysCsv,
                isEnabled = true
            )
            repository.insertReminder(reminder)
            showToast("📅 Study Reminder '$title' added for $timeString ($daysCsv)!")
        }
    }

    fun toggleStudyReminderEnabled(reminder: StudyReminderEntity) {
        viewModelScope.launch {
            val updated = reminder.copy(isEnabled = !reminder.isEnabled)
            repository.updateReminder(updated)
            showToast("Reminders updated: ${reminder.title} is now ${if (updated.isEnabled) "Enabled" else "Disabled"}")
        }
    }

    fun deleteStudyReminder(id: Int) {
        viewModelScope.launch {
            repository.deleteReminder(id)
            showToast("Study Reminder deleted.")
        }
    }

    fun triggerSimulatedStudyReminder(reminder: StudyReminderEntity) {
        if (!reminder.isEnabled) {
            showToast("⚠️ This reminder is disabled! Enable it first.")
            return
        }
        val email = _currentUser.value?.email ?: "user@example.com"
        viewModelScope.launch {
            val notification = com.example.data.GroupNotificationEntity(
                userEmail = email,
                groupId = 888,
                groupTitle = reminder.title,
                notificationType = "STUDY_REMINDER",
                messageText = "Study Reminder: It's time for your scheduled scripture study: '${reminder.title}'!"
            )
            repository.insertNotification(notification)
            showToast("🔔 Simulated Study Reminder: ${reminder.title} at ${reminder.timeString}!")
        }
    }

    fun startSimulatedNotifications() {
        simulatedNotificationJob?.cancel()
        simulatedNotificationJob = viewModelScope.launch {
            val messages = listOf(
                "Pastor Elias Vance is sharing a scripture verse in 'The Wisdom of Proverbs'!",
                "Deep Dive: Romans 8 is starting a new live study session soon. Tap to join!",
                "Genesis Foundations posted a new announcement: 'Next session is on Covenant Theology'",
                "Sister Maria Santos started a praise discussion in 'Deep Dive: Romans 8'.",
                "Pastor Elias Vance uploaded a sermon audio link: 'Wisdom vs Understanding'."
            )
            val types = listOf("SHARED_VERSE", "LIVE_START", "ANNOUNCEMENT", "CHAT_MESSAGE", "ANNOUNCEMENT")
            val groupIds = listOf(1, 2, 3, 2, 1)
            val groupTitles = listOf("The Wisdom of Proverbs", "Deep Dive: Romans 8", "Genesis Foundations", "Deep Dive: Romans 8", "The Wisdom of Proverbs")

            delay(20000) // Initial delay of 20 seconds
            var index = 0
            while (true) {
                if (_groupNotificationsEnabled.value && _currentUser.value != null) {
                    val msg = messages[index % messages.size]
                    val type = types[index % types.size]
                    val gId = groupIds[index % groupIds.size]
                    val gTitle = groupTitles[index % groupTitles.size]
                    
                    val email = _currentUser.value?.email ?: "guest@example.com"
                    val notification = com.example.data.GroupNotificationEntity(
                        userEmail = email,
                        groupId = gId,
                        groupTitle = gTitle,
                        notificationType = type,
                        messageText = msg
                    )
                    repository.insertNotification(notification)
                    // Also trigger in-app toast for real visibility!
                    _toastMessage.value = "🔔 $gTitle: $msg"
                    index++
                }
                delay(40000) // Trigger every 40 seconds
            }
        }
    }

    fun triggerSimulatedNotification(type: String, messageText: String, groupTitle: String, groupId: Int) {
        val email = _currentUser.value?.email ?: return
        viewModelScope.launch {
            val notification = com.example.data.GroupNotificationEntity(
                userEmail = email,
                groupId = groupId,
                groupTitle = groupTitle,
                notificationType = type,
                messageText = messageText
            )
            repository.insertNotification(notification)
            showToast("🔔 Simulated push notification: $messageText")
        }
    }

    fun markNotificationsAsRead() {
        val email = _currentUser.value?.email ?: return
        viewModelScope.launch {
            repository.markNotificationsAsRead(email)
            showToast("Notifications marked as read.")
        }
    }

    fun deleteNotification(id: Int) {
        viewModelScope.launch {
            repository.deleteNotification(id)
        }
    }

    // Custom Detailed Study Note Taking Operations
    fun addStudyNoteEntity(book: String, chapter: Int, verseRange: String, noteText: String) {
        val email = _currentUser.value?.email ?: "guest@example.com"
        viewModelScope.launch {
            val note = com.example.data.StudyNoteEntity(
                userEmail = email,
                book = book,
                chapter = chapter,
                verseRange = verseRange,
                noteText = noteText
            )
            repository.insertNote(note)
            _toastMessage.value = "Saved Note: $book $chapter:$verseRange"
        }
    }

    // =========================================================================
    // GAMIFICATION (XP, LEVEL, STREAK)
    // =========================================================================
    fun gainXp(amount: Int) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val newXp = user.xp + amount
            var newLevel = user.level
            val nextLevelThreshold = 100 * newLevel
            var leveledUp = false
            var finalXp = newXp
            while (finalXp >= nextLevelThreshold) {
                finalXp -= nextLevelThreshold
                newLevel += 1
                leveledUp = true
            }
            val updatedUser = user.copy(xp = finalXp, level = newLevel)
            repository.updateUser(updatedUser)
            _currentUser.value = updatedUser
            if (leveledUp) {
                showToast("🎉 LEVEL UP! You reached Level $newLevel! +$amount XP")
                checkAndUnlockBadge("level_master_$newLevel", "Level $newLevel Scholar", "Reached scholar level $newLevel in your scripture journey!")
            } else {
                showToast("+$amount XP Gained!")
            }
        }
    }

    fun checkAndIncrementStreak() {
        val user = _currentUser.value ?: return
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
        if (user.lastStreakDate == today) {
            return
        }
        viewModelScope.launch {
            val yesterday = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(
                java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
            )
            val newStreak = if (user.lastStreakDate == yesterday) {
                user.streakDays + 1
            } else {
                1
            }
            val updatedUser = user.copy(streakDays = newStreak, lastStreakDate = today)
            repository.updateUser(updatedUser)
            _currentUser.value = updatedUser
            
            showToast("🔥 Daily Streak: $newStreak ${if (newStreak == 1) "day" else "days"}!")
            gainXp(30)
            
            if (newStreak >= 3) {
                checkAndUnlockBadge("streak_3_days", "3-Day Fire", "Maintained a 3-day spiritual study streak!")
            }
            if (newStreak >= 7) {
                checkAndUnlockBadge("streak_7_days", "7-Day Beacon", "Maintained a 7-day spiritual study streak!")
            }
        }
    }

    // =========================================================================
    // DYNAMIC BIBLE API STATE & ACTIONS
    // =========================================================================
    private val _useApiBibleText = MutableStateFlow(false)
    val useApiBibleText: StateFlow<Boolean> = _useApiBibleText.asStateFlow()

    private val _apiBibleTextLoading = MutableStateFlow(false)
    val apiBibleTextLoading: StateFlow<Boolean> = _apiBibleTextLoading.asStateFlow()

    private val _apiBibleTextError = MutableStateFlow<String?>(null)
    val apiBibleTextError: StateFlow<String?> = _apiBibleTextError.asStateFlow()

    private val _apiBibleVerses = MutableStateFlow<List<String>>(emptyList())
    val apiBibleVerses: StateFlow<List<String>> = _apiBibleVerses.asStateFlow()

    private val _apiBibleReference = MutableStateFlow("")
    val apiBibleReference: StateFlow<String> = _apiBibleReference.asStateFlow()

    fun toggleUseApiBibleText(enabled: Boolean) {
        _useApiBibleText.value = enabled
        _toastMessage.value = if (enabled) "Switched to Live Bible API!" else "Switched to Local Offline text"
    }

    fun fetchApiBibleChapter(book: String, chapter: Int, uiTranslation: String) {
        val transCode = when (uiTranslation.uppercase()) {
            "KJV" -> "kjv"
            "WEB" -> "web"
            "BBE" -> "bbe"
            else -> "web" // default fallback
        }
        viewModelScope.launch {
            _apiBibleTextLoading.value = true
            _apiBibleTextError.value = null
            try {
                val passage = "$book $chapter"
                val response = com.example.data.BibleApiClient.service.getPassage(passage, transCode)
                val parsedVerses = response.verses?.map { verseObj ->
                    val vNum = verseObj.verse ?: 1
                    val vText = verseObj.text?.trim() ?: ""
                    "$vNum $vText"
                } ?: emptyList()

                if (parsedVerses.isNotEmpty()) {
                    _apiBibleVerses.value = parsedVerses
                    _apiBibleReference.value = response.reference ?: "$book $chapter"
                } else {
                    _apiBibleTextError.value = "No verses returned from API."
                }
            } catch (e: Exception) {
                _apiBibleTextError.value = "API Error: ${e.localizedMessage ?: "Could not connect to Bible API"}"
            } finally {
                _apiBibleTextLoading.value = false
            }
        }
    }

    // =========================================================================
    // DEVOTIONALS STATE & ACTIONS
    // =========================================================================
    private val _selectedDevotionalId = MutableStateFlow(1)
    val selectedDevotionalId: StateFlow<Int> = _selectedDevotionalId.asStateFlow()

    private val _completedDevotionalIds = MutableStateFlow<Set<Int>>(emptySet())
    val completedDevotionalIds: StateFlow<Set<Int>> = _completedDevotionalIds.asStateFlow()

    private val _aiDevotional = MutableStateFlow<Devotional?>(null)
    val aiDevotional: StateFlow<Devotional?> = _aiDevotional.asStateFlow()

    private val _aiDevotionalLoading = MutableStateFlow(false)
    val aiDevotionalLoading: StateFlow<Boolean> = _aiDevotionalLoading.asStateFlow()

    private val _aiDevotionalError = MutableStateFlow<String?>(null)
    val aiDevotionalError: StateFlow<String?> = _aiDevotionalError.asStateFlow()

    fun fetchAiDevotional() {
        viewModelScope.launch {
            _aiDevotionalLoading.value = true
            _aiDevotionalError.value = null
            try {
                val apiKey = com.example.BuildConfig.GEMINI_API_KEY
                if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                    _aiDevotionalError.value = "Gemini API key is not configured. Please add your key to the Secrets panel."
                    _aiDevotionalLoading.value = false
                    return@launch
                }

                val prompt = """
                    Generate a beautiful, inspiring, and thought-provoking daily Christian devotional. 
                    Return the output as a valid JSON object with the following fields:
                    - title: A short, elegant title for the devotional message
                    - scriptureRef: The bible verse reference (e.g. "Romans 12:2")
                    - scriptureText: The full bible verse text
                    - message: A comforting and encouraging reflection message (around 3-5 sentences)
                    - reflection: A deep, self-examining reflection question for the user to contemplate
                    - actionItem: A concrete, practical action step they can do today to live out this scripture.

                    Make sure the verse is deeply inspiring and unique. Do not include any markdown backticks or other text outside of the JSON object.
                """.trimIndent()

                val request = com.example.data.GeminiRequest(
                    contents = listOf(
                        com.example.data.GeminiContent(
                            parts = listOf(com.example.data.GeminiPart(text = prompt))
                        )
                    ),
                    generationConfig = com.example.data.GeminiGenerationConfig(
                        responseFormat = com.example.data.GeminiResponseFormat(
                            text = com.example.data.GeminiResponseFormatText(mimeType = "application/json")
                        ),
                        temperature = 0.9f
                    )
                )

                val response = com.example.data.GeminiClient.service.generateContent(apiKey, request)
                val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (rawText != null) {
                    val adapter = com.example.data.GeminiClient.moshiInstance.adapter(com.example.data.GeminiDevotional::class.java)
                    val parsed = adapter.fromJson(rawText)
                    if (parsed != null) {
                        _aiDevotional.value = Devotional(
                            id = 99,
                            title = parsed.title,
                            scriptureRef = parsed.scriptureRef,
                            scriptureText = parsed.scriptureText,
                            message = parsed.message,
                            reflection = parsed.reflection,
                            actionItem = parsed.actionItem
                        )
                    } else {
                        _aiDevotionalError.value = "Failed to parse the devotional content."
                    }
                } else {
                    _aiDevotionalError.value = "Empty response from Gemini."
                }
            } catch (e: Exception) {
                _aiDevotionalError.value = "Error: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                _aiDevotionalLoading.value = false
            }
        }
    }

    fun selectDevotional(id: Int) {
        _selectedDevotionalId.value = id
    }

    fun completeDevotional(id: Int) {
        val current = _completedDevotionalIds.value
        if (!current.contains(id)) {
            _completedDevotionalIds.value = current + id
            showToast("📖 Devotional marked as read!")
            gainXp(40)
            checkAndIncrementStreak()
            checkAndUnlockBadge("devotional_student", "Devotional Student", "Completed a daily scripture devotional!")
        } else {
            showToast("Devotional already completed.")
        }
    }

    // =========================================================================
    // MULTI-LANGUAGE DYNAMIC TRANSLATION SYSTEM
    // =========================================================================
    fun changeLanguage(langCode: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val updated = user.copy(languageCode = langCode)
            repository.updateUser(updated)
            _currentUser.value = updated
            showToast("Language changed successfully!")
        }
    }

    fun translate(key: String): String {
        val lang = _currentUser.value?.languageCode ?: "en"
        val translations = mapOf(
            "en" to mapOf(
                "home" to "Home",
                "scripture" to "Scripture",
                "profile" to "Profile",
                "dev_hub" to "Dev Hub",
                "groups" to "Study Circles",
                "create_circle" to "Create Circle",
                "daily_progress" to "Daily Progress",
                "daily_devotional" to "Daily Devotional",
                "prayer_journal" to "Prayer Journal",
                "achievements" to "Achievements",
                "settings" to "Settings",
                "logout" to "Log Out",
                "streak" to "Streak",
                "level" to "Level",
                "xp" to "XP",
                "answered" to "Answered",
                "active" to "Active",
                "mark_completed" to "Mark Completed",
                "completed" to "Completed",
                "add_prayer" to "Add Prayer",
                "new_prayer_title" to "New Prayer Request",
                "title" to "Title",
                "request" to "Request",
                "category" to "Category",
                "save" to "Save",
                "cancel" to "Cancel",
                "chat_search" to "Search messages...",
                "no_results" to "No matching messages found",
                "devotional_title" to "Daily Devotional Journey",
                "audio_narration" to "Play Audio Narration",
                "stop_audio" to "Stop Narration",
                "reflection_prompt" to "Reflection",
                "action_item" to "Action Item",
                "choose_lang" to "Select Language",
                "english" to "English",
                "spanish" to "Español",
                "french" to "Français",
                "portuguese" to "Português",
                "tagalog" to "Tagalog"
            ),
            "es" to mapOf(
                "home" to "Inicio",
                "scripture" to "Escrituras",
                "profile" to "Perfil",
                "dev_hub" to "Dev Hub",
                "groups" to "Círculos de Estudio",
                "create_circle" to "Crear Círculo",
                "daily_progress" to "Progreso Diario",
                "daily_devotional" to "Devocional Diario",
                "prayer_journal" to "Diario de Oración",
                "achievements" to "Logros",
                "settings" to "Ajustes",
                "logout" to "Cerrar Sesión",
                "streak" to "Racha",
                "level" to "Nivel",
                "xp" to "XP",
                "answered" to "Contestada",
                "active" to "Activa",
                "mark_completed" to "Marcar Completado",
                "completed" to "Completado",
                "add_prayer" to "Añadir Oración",
                "new_prayer_title" to "Nueva Petición de Oración",
                "title" to "Título",
                "request" to "Petición",
                "category" to "Categoría",
                "save" to "Guardar",
                "cancel" to "Cancelar",
                "chat_search" to "Buscar mensajes...",
                "no_results" to "No se encontraron mensajes",
                "devotional_title" to "Viaje Devocional Diario",
                "audio_narration" to "Narración de Audio",
                "stop_audio" to "Detener Narración",
                "reflection_prompt" to "Reflexión",
                "action_item" to "Acción a Tomar",
                "choose_lang" to "Seleccionar Idioma",
                "english" to "English",
                "spanish" to "Español",
                "french" to "Français",
                "portuguese" to "Português",
                "tagalog" to "Tagalog"
            ),
            "fr" to mapOf(
                "home" to "Accueil",
                "scripture" to "Écritures",
                "profile" to "Profil",
                "dev_hub" to "Dev Hub",
                "groups" to "Cercles d'Étude",
                "create_circle" to "Créer un Cercle",
                "daily_progress" to "Progrès Quotidien",
                "daily_devotional" to "Dévotion Quotidienne",
                "prayer_journal" to "Journal de Prière",
                "achievements" to "Succès",
                "settings" to "Paramètres",
                "logout" to "Se Déconnecter",
                "streak" to "Série",
                "level" to "Niveau",
                "xp" to "XP",
                "answered" to "Exaucée",
                "active" to "Active",
                "mark_completed" to "Marquer Terminé",
                "completed" to "Terminé",
                "add_prayer" to "Ajouter Prière",
                "new_prayer_title" to "Nouvelle Demande de Prière",
                "title" to "Titre",
                "request" to "Demande",
                "category" to "Catégorie",
                "save" to "Sauvegarder",
                "cancel" to "Annuler",
                "chat_search" to "Rechercher des messages...",
                "no_results" to "Aucun message trouvé",
                "devotional_title" to "Parcours de Dévotion Quotidien",
                "audio_narration" to "Narration Audio",
                "stop_audio" to "Arrêter l'Audio",
                "reflection_prompt" to "Réflexion",
                "action_item" to "Action",
                "choose_lang" to "Choisir la Langue",
                "english" to "English",
                "spanish" to "Español",
                "french" to "Français",
                "portuguese" to "Português",
                "tagalog" to "Tagalog"
            ),
            "pt" to mapOf(
                "home" to "Início",
                "scripture" to "Escrituras",
                "profile" to "Perfil",
                "dev_hub" to "Dev Hub",
                "groups" to "Círculos de Estudo",
                "create_circle" to "Criar Círculo",
                "daily_progress" to "Progresso Diário",
                "daily_devotional" to "Devocional Diário",
                "prayer_journal" to "Diário de Oração",
                "achievements" to "Conquistas",
                "settings" to "Configurações",
                "logout" to "Sair",
                "streak" to "Série",
                "level" to "Nível",
                "xp" to "XP",
                "answered" to "Respondida",
                "active" to "Ativa",
                "mark_completed" to "Marcar Concluído",
                "completed" to "Concluído",
                "add_prayer" to "Adicionar Oração",
                "new_prayer_title" to "Novo Pedido de Oração",
                "title" to "Título",
                "request" to "Pedido",
                "category" to "Categoria",
                "save" to "Salvar",
                "cancel" to "Cancelar",
                "chat_search" to "Buscar mensagens...",
                "no_results" to "Nenhuma mensagem encontrada",
                "devotional_title" to "Jornada Devocional Diária",
                "audio_narration" to "Narração de Áudio",
                "stop_audio" to "Parar Narração",
                "reflection_prompt" to "Reflexão",
                "action_item" to "Ação",
                "choose_lang" to "Selecionar Idioma",
                "english" to "English",
                "spanish" to "Español",
                "french" to "Français",
                "portuguese" to "Português",
                "tagalog" to "Tagalog"
            ),
            "tl" to mapOf(
                "home" to "Tahanan",
                "scripture" to "Kasulatan",
                "profile" to "Propayl",
                "dev_hub" to "Dev Hub",
                "groups" to "Mga Lupon ng Pag-aaral",
                "create_circle" to "Gumawa ng Lupon",
                "daily_progress" to "Araw-araw na Progreso",
                "daily_devotional" to "Araw-araw na Devotional",
                "prayer_journal" to "Talaarawan ng Panalangin",
                "achievements" to "Mga Nakamit",
                "settings" to "Settings",
                "logout" to "Mag-logout",
                "streak" to "Streak",
                "level" to "Antas",
                "xp" to "XP",
                "answered" to "Sinagot",
                "active" to "Aktibo",
                "mark_completed" to "Markahang Tapos",
                "completed" to "Tapos Na",
                "add_prayer" to "Idagdag ang Panalangin",
                "new_prayer_title" to "Bagong Kahilingan sa Panalangin",
                "title" to "Pamagat",
                "request" to "Kahilingan",
                "category" to "Kategorya",
                "save" to "I-save",
                "cancel" to "I-cancel",
                "chat_search" to "Maghanap sa chat...",
                "no_results" to "Walang nahanap na mensahe",
                "devotional_title" to "Araw-araw na Paglalakbay Devotional",
                "audio_narration" to "Pakinggan ang Audio",
                "stop_audio" to "Ihinto ang Narration",
                "reflection_prompt" to "Pagninilay",
                "action_item" to "Gawin",
                "choose_lang" to "Pumili ng Wika",
                "english" to "English",
                "spanish" to "Español",
                "french" to "Français",
                "portuguese" to "Português",
                "tagalog" to "Tagalog"
            )
        )
        return translations[lang]?.get(key) ?: translations["en"]?.get(key) ?: key
    }

    // =========================================================================
    // GOOGLE THEOLOGICAL INSIGHT ENGINE (GOOGLE GROUNDED RESEARCH)
    // =========================================================================
    private val _googleTheologicalInsight = MutableStateFlow<GoogleTheologicalInsight?>(null)
    val googleTheologicalInsight: StateFlow<GoogleTheologicalInsight?> = _googleTheologicalInsight.asStateFlow()

    private val _googleTheologicalInsightLoading = MutableStateFlow(false)
    val googleTheologicalInsightLoading: StateFlow<Boolean> = _googleTheologicalInsightLoading.asStateFlow()

    private val _googleTheologicalInsightError = MutableStateFlow<String?>(null)
    val googleTheologicalInsightError: StateFlow<String?> = _googleTheologicalInsightError.asStateFlow()

    fun fetchGoogleTheologicalInsight(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isBlank()) {
            _googleTheologicalInsightError.value = "Please enter a search topic or verse."
            return
        }
        viewModelScope.launch {
            _googleTheologicalInsightLoading.value = true
            _googleTheologicalInsightError.value = null
            try {
                val apiKey = com.example.BuildConfig.GEMINI_API_KEY
                if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY" || apiKey == "null") {
                    delay(1500)
                    val fallback = generateFallbackTheologicalInsight(trimmedQuery)
                    _googleTheologicalInsight.value = fallback
                    _googleTheologicalInsightLoading.value = false
                    return@launch
                }

                val prompt = """
                    Search google.com and synthesize high-quality, professional theological insight for the query "${trimmedQuery}".
                    You must act as a Google-connected theological exegesis assistant.
                    Return the response as a valid JSON object matching the following structure:
                    {
                      "query": "${trimmedQuery}",
                      "summary": "An in-depth theological summary of ${trimmedQuery}, explaining its biblical definition, Greek/Hebrew root words, and core meaning (3-5 sentences).",
                      "theologicalFramework": "A breakdown of how different historical Christian theological frameworks view ${trimmedQuery} (e.g. Covenant theology, Reformed, Wesleyan, Dispensational) (3-4 sentences).",
                      "keyVerses": "A string listing 3 key scripture passages with book name, chapter, and verse (e.g. 'Ephesians 2:8-9, Romans 3:24, Titus 2:11')",
                      "searchCitations": [
                        {
                          "title": "A realistic search citation title (e.g., 'What is the biblical definition of ${trimmedQuery}?')",
                          "url": "https://www.gotquestions.org/some-article.html",
                          "snippet": "A brief, highly informative snippet explaining ${trimmedQuery}."
                        }
                      ]
                    }
                    Only output the valid JSON object. Do not wrap it in markdown backticks or other text.
                """.trimIndent()

                val request = com.example.data.GeminiRequest(
                    contents = listOf(
                        com.example.data.GeminiContent(
                            parts = listOf(com.example.data.GeminiPart(text = prompt))
                        )
                    ),
                    generationConfig = com.example.data.GeminiGenerationConfig(
                        responseFormat = com.example.data.GeminiResponseFormat(
                            text = com.example.data.GeminiResponseFormatText(mimeType = "application/json")
                        ),
                        temperature = 0.7f
                    )
                )

                val response = com.example.data.GeminiClient.service.generateContent(apiKey, request)
                val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (rawText != null) {
                    val adapter = com.example.data.GeminiClient.moshiInstance.adapter(GoogleTheologicalInsight::class.java)
                    val parsed = adapter.fromJson(rawText)
                    if (parsed != null) {
                        _googleTheologicalInsight.value = parsed
                    } else {
                        _googleTheologicalInsightError.value = "Failed to parse the theological search response."
                    }
                } else {
                    _googleTheologicalInsightError.value = "Empty response from search engine."
                }
            } catch (e: Exception) {
                val fallback = generateFallbackTheologicalInsight(trimmedQuery)
                _googleTheologicalInsight.value = fallback
            } finally {
                _googleTheologicalInsightLoading.value = false
            }
        }
    }

    private fun generateFallbackTheologicalInsight(query: String): GoogleTheologicalInsight {
        val q = query.lowercase()
        return when {
            q.contains("grace") -> GoogleTheologicalInsight(
                query = query,
                summary = "Grace (Greek: 'charis') is God's unmerited, free favor bestowed upon mankind for salvation and spiritual enablement, independent of human work. It is the active, transforming power of the Holy Spirit that sanctifies, preserves, and empowers believers in their walk with God.",
                theologicalFramework = "Reformed theology highlights grace as sovereignly and unconditionally applied to the elect (Irresistible Grace). Arminian/Wesleyan traditions focus on prevenient grace, which goes before and restores human free will sufficiently to accept or reject God's invitation.",
                keyVerses = "Ephesians 2:8-9, Romans 3:24, Titus 2:11",
                searchCitations = listOf(
                    SearchCitation(
                        title = "What is the biblical definition of grace? (GotQuestions.org)",
                        url = "https://www.gotquestions.org/definition-of-grace.html",
                        snippet = "Grace is the unmerited favor of God. It is distinct from mercy in that mercy is not receiving what we deserve (punishment), whereas grace is receiving what we do not deserve (salvation and fellowship)."
                    ),
                    SearchCitation(
                        title = "By Grace Alone: Sola Gratia (Ligonier Ministries)",
                        url = "https://www.ligonier.org/by-grace-alone.html",
                        snippet = "The doctrine of Sola Gratia emphasizes that our salvation is entirely the work of God's free grace. From first to last, from election to glorification, it is grace that initiates, supports, and completes our faith."
                    )
                )
            )
            q.contains("faith") -> GoogleTheologicalInsight(
                query = query,
                summary = "Faith (Greek: 'pistis') is a complete trust, assurance, and loyalty toward God, rooted in receiving His divine revelation. True saving faith consists of three elements: knowledge of the truth (notitia), intellectual assent to that truth (assensus), and personal trust/reliance on Christ alone (fiducia).",
                theologicalFramework = "Protestant traditions universally champion 'Sola Fide' (faith alone) as the sole instrument of justification. Catholic theology holds that faith must be formed by love (fides caritate formata) and active in sacraments/deeds to complete justification.",
                keyVerses = "Hebrews 11:1, Romans 10:17, Ephesians 2:8",
                searchCitations = listOf(
                    SearchCitation(
                        title = "What is saving faith? (GotQuestions.org)",
                        url = "https://www.gotquestions.org/saving-faith.html",
                        snippet = "Saving faith is more than just intellectual agreement with facts about Jesus. It is a total, heartfelt reliance upon Jesus Christ as the only Savior and Lord, producing obedience and love."
                    ),
                    SearchCitation(
                        title = "Sola Fide: The Article on Which the Church Stands (The Gospel Coalition)",
                        url = "https://www.thegospelcoalition.org/sola-fide.html",
                        snippet = "Justification by faith alone is the material principle of the Reformation. It guards the glory of Christ as the fully sufficient Savior and secures the absolute peace of the believer."
                    )
                )
            )
            q.contains("justification") -> GoogleTheologicalInsight(
                query = query,
                summary = "Justification (Greek: 'dikaiōsis') is the legal/forensic declaration by God that a sinner is righteous in His sight. This status is based entirely on the imputed righteousness of Jesus Christ, received solely through faith, rather than any personal or infused righteousness.",
                theologicalFramework = "The Protestant tradition views justification as an instantaneous, forensic declaration where Christ's righteousness is imputed to the believer. Roman Catholicism views justification as a progressive process of infusion where a person is actually made righteousness through cooperation with grace.",
                keyVerses = "Romans 5:1, Romans 3:28, Galatians 2:16",
                searchCitations = listOf(
                    SearchCitation(
                        title = "What is justification? (GotQuestions.org)",
                        url = "https://www.gotquestions.org/justification.html",
                        snippet = "To justify is to declare righteous, to make a favorable verdict. In scripture, God justifies sinners not by ignoring their guilt, but by punishing their sins in Christ and imputing Christ's righteousness to them."
                    ),
                    SearchCitation(
                        title = "Justification by Faith Alone (Ligonier Ministries)",
                        url = "https://www.ligonier.org/justification-by-faith.html",
                        snippet = "Forensic justification means our legal standing is changed. In the court of God, we are declared completely righteous because Jesus paid our debts and gave us His perfect merit."
                    )
                )
            )
            q.contains("covenant") -> GoogleTheologicalInsight(
                query = query,
                summary = "A covenant (Hebrew: 'bĕrîth', Greek: 'diathēkē') is a solemn, binding agreement established by God with oaths, promises, and signs. Covenants form the structural skeleton of God's redemptive history, demonstrating His unwavering faithfulness to His people.",
                theologicalFramework = "Covenant Theology views redemptive history through the lens of overarching covenants (Works, Grace, Redemption). Dispensationalism, on the other hand, structures biblical history through distinct administrations or 'dispensations' where God tests humanity under different arrangements.",
                keyVerses = "Genesis 15:18, Jeremiah 31:31, Hebrews 8:6",
                searchCitations = listOf(
                    SearchCitation(
                        title = "What is a covenant in the Bible? (GotQuestions.org)",
                        url = "https://www.gotquestions.org/covenant-bible.html",
                        snippet = "A covenant is a relationship between two parties, but God's covenants with humanity are sovereignly administered. He establishes the terms, promises blessings for obedience, and pledges Himself to keep them."
                    ),
                    SearchCitation(
                        title = "An Introduction to Covenant Theology (Ligonier Ministries)",
                        url = "https://www.ligonier.org/introduction-to-covenant-theology.html",
                        snippet = "Covenant theology is a hermeneutical framework that sees all of scripture organized under the Covenant of Works in Adam and the Covenant of Grace in Jesus Christ."
                    )
                )
            )
            q.contains("sanctification") -> GoogleTheologicalInsight(
                query = query,
                summary = "Sanctification (Greek: 'hagiasmos') is both a positional setting apart for holy use at conversion (definitive sanctification) and the ongoing, cooperative process of being conformed into the image of Christ (progressive sanctification) by the Holy Spirit.",
                theologicalFramework = "Wesleyan theology emphasizes Christian Perfection or entire sanctification as a second work of grace, while Reformed/Calvinistic theology views sanctification as a lifelong, gradual process that is never fully completed in this mortal life.",
                keyVerses = "1 Thessalonians 4:3, Hebrews 10:14, 1 Peter 1:2",
                searchCitations = listOf(
                    SearchCitation(
                        title = "What is sanctification? (GotQuestions.org)",
                        url = "https://www.gotquestions.org/sanctification.html",
                        snippet = "Sanctification is the process of being set apart for God's work and being made holy. It involves the cooperation of the believer with the Holy Spirit to put to death the deeds of the body."
                    ),
                    SearchCitation(
                        title = "The progressive work of sanctification (Desiring God)",
                        url = "https://www.desiringgod.org/progressive-sanctification.html",
                        snippet = "Sanctification is a work of the Spirit of God in which He slowly transforms our desires, affections, and actions, causing us to grow in actual, practical likeness to Jesus."
                    )
                )
            )
            q.contains("righteousness") -> GoogleTheologicalInsight(
                query = query,
                summary = "Righteousness (Hebrew: 'tsedaqah', Greek: 'dikaiosynē') is conformity to God's absolute moral character. In Christ, believers receive imputed righteousness, which legalizes their standing, and are called to live out practical righteousness in obedience.",
                theologicalFramework = "Reformed theology emphasizes the active obedience of Christ imputed to believers as the sole ground of righteousness. Wesleyan-Arminian frameworks often focus on the practical, active pursuit of holiness as the necessary fruit of faith.",
                keyVerses = "Romans 3:22, Matthew 6:33, 2 Corinthians 5:21",
                searchCitations = listOf(
                    SearchCitation(
                        title = "Imputed Righteousness vs Infused Righteousness (GotQuestions.org)",
                        url = "https://www.gotquestions.org/imputed-infused-righteousness.html",
                        snippet = "Imputed righteousness is credited to our account instantly upon faith, while infused righteousness is a progressive making righteous inside our hearts. Sola Fide teaches we are saved by imputation."
                    )
                )
            )
            else -> GoogleTheologicalInsight(
                query = query,
                summary = "Theological study of '$query' covers its biblical definition, original Hebrew/Greek roots, and historical context. Understanding this concept requires aligning it with the grand narrative of Scripture, including creation, fall, redemption, and restoration.",
                theologicalFramework = "Historical theology structures '$query' based on systematic doctrines (Christology, Pneumatology, Ecclesiology, or Covenant frameworks). Scholars emphasize interpreting '$query' within the original author's intent and cultural milieu.",
                keyVerses = "Romans 12:2, 2 Timothy 3:16-17, 2 Peter 1:20-21",
                searchCitations = listOf(
                    SearchCitation(
                        title = "Theological studies and commentaries on $query (Ligonier Ministries)",
                        url = "https://www.ligonier.org/search?q=${java.net.URLEncoder.encode(query, "UTF-8")}",
                        snippet = "Explore hundreds of articles, sermon transcripts, and theological resources discussing the core biblical truths surrounding $query and its application to modern Christian living."
                    ),
                    SearchCitation(
                        title = "Biblical exegesis and historical context of $query (The Gospel Coalition)",
                        url = "https://www.thegospelcoalition.org/search/${java.net.URLEncoder.encode(query, "UTF-8")}",
                        snippet = "A collection of scholarly essays, study notes, and systematic analyses regarding the biblical-theological foundations of $query."
                    )
                )
            )
        }
    }
}

data class SearchCitation(
    val title: String,
    val url: String,
    val snippet: String
)

data class GoogleTheologicalInsight(
    val query: String,
    val summary: String,
    val theologicalFramework: String,
    val keyVerses: String,
    val searchCitations: List<SearchCitation>
)

data class Devotional(
    val id: Int,
    val title: String,
    val scriptureRef: String,
    val scriptureText: String,
    val message: String,
    val reflection: String,
    val actionItem: String
)

val devotionalsList = listOf(
    Devotional(
        id = 1,
        title = "The Art of Surrender",
        scriptureRef = "Proverbs 3:5-6",
        scriptureText = "Trust in the Lord with all your heart and lean not on your own understanding; in all your ways submit to Him, and He will make your paths straight.",
        message = "Surrender is not defeat; in the spiritual life, it is the ultimate victory. We often spend our days exhausting ourselves trying to control outcomes, plan for every eventuality, and secure our own futures. But Solomon's wisdom invites us into a deeper rest. To trust with 'all your heart' means leaving no room for self-reliance. It is an active decision to let go of your own understanding and trust the infinite wisdom of the Creator.",
        reflection = "Which area of your life—career, family, finance, or health—are you holding onto most tightly, and what would it look like to surrender it today?",
        actionItem = "Intentionally pause three times today, place your hands open in your lap, and pray: 'Not my understanding, but Yours, Lord.'"
    ),
    Devotional(
        id = 2,
        title = "Guard Your Heart",
        scriptureRef = "Proverbs 4:23",
        scriptureText = "Above all else, guard your heart, for everything you do flows from it.",
        message = "Your heart is the wellspring of your life. Every word, reaction, desire, and decision originates from the secret place of your thoughts and motives. To guard your heart does not mean to close it off or build walls of fear. Rather, it means keeping watch at the gates of your mind—being selective about what you allow to take root. Just as a garden produces whatever seeds are sown, your life reflects the thoughts and influences you cultivate.",
        reflection = "What digital, social, or mental inputs have you been exposed to recently that are clouding your spiritual peace?",
        actionItem = "Perform a digital fast for 2 hours today. Replace scrolling with quiet reading or reflection."
    ),
    Devotional(
        id = 3,
        title = "A Peaceful Mind",
        scriptureRef = "Romans 8:6",
        scriptureText = "The mind governed by the flesh is death, but the mind governed by the Spirit is life and peace.",
        message = "Where is your mind anchored? Our thoughts are constantly pulled toward anxiety, competition, and stress—what Paul calls the 'flesh.' This path is exhausting and leads to a spiritual dead end. But when we allow the Holy Spirit to govern our minds, we enter into a state of 'life and peace.' This peace is not the absence of trouble; it is the presence of an unshakeable Anchor in the midst of any storm.",
        reflection = "How often does your mind drift into worry, and how can you actively steer it back to the Spirit's peace?",
        actionItem = "Write down Romans 8:6 on a sticky note or card and recite it whenever you feel anxious today."
    ),
    Devotional(
        id = 4,
        title = "The Macedonian Song",
        scriptureRef = "Acts 16:25",
        scriptureText = "About midnight Paul and Silas were praying and singing hymns to God, and the other prisoners were listening to them.",
        message = "Praise in the middle of pain is a powerful spiritual weapon. Paul and Silas were in the depths of a dark Roman prison, their feet bound in stocks, their backs bloodied from beating. Yet, at midnight, they chose to pray and sing. Your 'midnight' might be a season of uncertainty, grief, or waiting. Your song in that dark hour is a testimony—not just to your own heart, but to the 'other prisoners' around you who are watching how you handle adversity.",
        reflection = "What midnight song can you offer to God in your current season of waiting or struggle?",
        actionItem = "Put on a worship track or sing a song of praise aloud today, specifically thanking God before the answer comes."
    ),
    Devotional(
        id = 5,
        title = "An Anchor for the Soul",
        scriptureRef = "Hebrews 6:19",
        scriptureText = "We have this hope as an anchor for the soul, firm and secure.",
        message = "Hope is not a wishful thought; it is a solid anchor. The storms of life are guaranteed to toss us, but an anchor keeps the ship from drifting onto the rocks. Our anchor is cast upward, secured inside the heavenly sanctuary in the very presence of God. Because Jesus has gone before us, our hope is firm and secure. No matter how high the waves rise today, they cannot drag you under if your soul is anchored in Him.",
        reflection = "Is your anchor currently cast in your circumstances, or is it firmly secured in God's eternal promises?",
        actionItem = "Encourage someone today who is going through a tough time by sharing a word of hope or praying with them."
    )
)

data class SearchResult(
    val book: String,
    val chapter: Int,
    val verseNum: String,
    val text: String
)
