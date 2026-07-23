package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LuminaRepository(
    private val userDao: UserDao,
    private val studyGroupDao: StudyGroupDao,
    private val chatMessageDao: ChatMessageDao,
    private val blockedUserDao: BlockedUserDao,
    private val searchHistoryDao: SearchHistoryDao,
    private val unlockedBadgeDao: UnlockedBadgeDao,
    private val prayerDao: PrayerDao,
    private val studyNoteDao: StudyNoteDao,
    private val studyProgressDao: StudyProgressDao,
    private val verseHighlightDao: VerseHighlightDao,
    private val groupNotificationDao: GroupNotificationDao,
    private val studyReminderDao: StudyReminderDao
) {
    // Study Notes
    fun getNotesForUser(email: String): Flow<List<StudyNoteEntity>> {
        return studyNoteDao.getNotesForUserFlow(email)
    }

    suspend fun insertNote(note: StudyNoteEntity): Long = withContext(Dispatchers.IO) {
        studyNoteDao.insertNote(note)
    }

    suspend fun deleteNote(id: Int) = withContext(Dispatchers.IO) {
        studyNoteDao.deleteNoteById(id)
    }

    // Study Progress
    fun getProgressForUser(email: String): Flow<List<StudyProgressEntity>> {
        return studyProgressDao.getProgressForUserFlow(email)
    }

    suspend fun insertProgress(progress: StudyProgressEntity): Long = withContext(Dispatchers.IO) {
        studyProgressDao.insertProgress(progress)
    }

    suspend fun removeProgress(email: String, book: String, chapter: Int) = withContext(Dispatchers.IO) {
        studyProgressDao.removeProgress(email, book, chapter)
    }

    // Verse Highlights
    fun getHighlightsForUser(email: String): Flow<List<VerseHighlightEntity>> {
        return verseHighlightDao.getHighlightsForUserFlow(email)
    }

    suspend fun insertHighlight(highlight: VerseHighlightEntity): Long = withContext(Dispatchers.IO) {
        verseHighlightDao.insertHighlight(highlight)
    }

    suspend fun removeHighlight(email: String, book: String, chapter: Int, verse: Int) = withContext(Dispatchers.IO) {
        verseHighlightDao.removeHighlight(email, book, chapter, verse)
    }

    // Group Notifications
    fun getNotificationsForUser(email: String): Flow<List<GroupNotificationEntity>> {
        return groupNotificationDao.getNotificationsForUserFlow(email)
    }

    suspend fun insertNotification(notification: GroupNotificationEntity): Long = withContext(Dispatchers.IO) {
        groupNotificationDao.insertNotification(notification)
    }

    suspend fun markNotificationsAsRead(email: String) = withContext(Dispatchers.IO) {
        groupNotificationDao.markAllAsRead(email)
    }

    suspend fun deleteNotification(id: Int) = withContext(Dispatchers.IO) {
        groupNotificationDao.deleteNotificationById(id)
    }

    // Prayer operations
    fun getAllPrayers(): Flow<List<PrayerEntity>> {
        return prayerDao.getAllPrayersFlow()
    }

    suspend fun insertPrayer(prayer: PrayerEntity): Long = withContext(Dispatchers.IO) {
        prayerDao.insertPrayer(prayer)
    }

    suspend fun updatePrayer(prayer: PrayerEntity) = withContext(Dispatchers.IO) {
        prayerDao.updatePrayer(prayer)
    }

    suspend fun deletePrayer(id: Int) = withContext(Dispatchers.IO) {
        prayerDao.deletePrayerById(id)
    }

    suspend fun incrementPrayerAmen(id: Int) = withContext(Dispatchers.IO) {
        prayerDao.incrementAmenCount(id)
    }

    // Study Reminder operations
    fun getRemindersForUser(email: String): Flow<List<StudyReminderEntity>> {
        return studyReminderDao.getRemindersForUserFlow(email)
    }

    suspend fun insertReminder(reminder: StudyReminderEntity): Long = withContext(Dispatchers.IO) {
        studyReminderDao.insertReminder(reminder)
    }

    suspend fun updateReminder(reminder: StudyReminderEntity) = withContext(Dispatchers.IO) {
        studyReminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(id: Int) = withContext(Dispatchers.IO) {
        studyReminderDao.deleteReminderById(id)
    }
    // Search history operations
    fun getRecentSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.getRecentSearchHistoryFlow()
    }

    suspend fun insertSearchQuery(query: String) = withContext(Dispatchers.IO) {
        if (query.isNotBlank()) {
            searchHistoryDao.insertSearch(SearchHistoryEntity(query = query))
        }
    }

    suspend fun deleteSearchQuery(query: String) = withContext(Dispatchers.IO) {
        searchHistoryDao.deleteSearchByQuery(query)
    }

    suspend fun clearSearchHistory() = withContext(Dispatchers.IO) {
        searchHistoryDao.clearHistory()
    }

    // Badge operations
    fun getUnlockedBadges(): Flow<List<UnlockedBadgeEntity>> {
        return unlockedBadgeDao.getUnlockedBadgesFlow()
    }

    suspend fun unlockBadge(id: String, title: String, description: String) = withContext(Dispatchers.IO) {
        unlockedBadgeDao.unlockBadge(UnlockedBadgeEntity(id = id, title = title, description = description))
    }

    suspend fun clearAllBadges() = withContext(Dispatchers.IO) {
        unlockedBadgeDao.clearBadges()
    }
    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsersFlow()
    }

    suspend fun getUserByEmail(email: String): UserEntity? = withContext(Dispatchers.IO) {
        userDao.getUserByEmail(email)
    }

    suspend fun registerUser(user: UserEntity): Boolean = withContext(Dispatchers.IO) {
        val existing = userDao.getUserByEmail(user.email)
        if (existing != null) {
            false
        } else {
            userDao.registerUser(user)
            true
        }
    }

    suspend fun loginUser(email: String, passwordPlain: String): UserEntity? = withContext(Dispatchers.IO) {
        val user = userDao.getUserByEmail(email)
        // In a real production app, use BCrypt or PBKDF2. For this boiler-plate/production hybrid,
        // we check matching strings directly or standard hash comparison.
        if (user != null && user.passwordHash == passwordPlain) {
            user
        } else {
            null
        }
    }

    suspend fun updateUser(user: UserEntity) = withContext(Dispatchers.IO) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(email: String) = withContext(Dispatchers.IO) {
        userDao.deleteUserByEmail(email)
    }

    fun getAllGroups(): Flow<List<StudyGroupEntity>> {
        return studyGroupDao.getAllGroupsFlow()
    }

    suspend fun getGroupById(id: Int): StudyGroupEntity? = withContext(Dispatchers.IO) {
        studyGroupDao.getGroupById(id)
    }

    suspend fun insertGroup(group: StudyGroupEntity) = withContext(Dispatchers.IO) {
        studyGroupDao.insertGroup(group)
    }

    fun getMessagesForGroup(groupId: Int): Flow<List<ChatMessageEntity>> {
        return chatMessageDao.getMessagesForGroupFlow(groupId)
    }

    suspend fun insertMessage(message: ChatMessageEntity) = withContext(Dispatchers.IO) {
        chatMessageDao.insertMessage(message)
    }

    suspend fun pinMessage(message: ChatMessageEntity) = withContext(Dispatchers.IO) {
        chatMessageDao.updateMessage(message.copy(isPinned = true))
    }

    suspend fun reportMessage(message: ChatMessageEntity) = withContext(Dispatchers.IO) {
        chatMessageDao.updateMessage(message.copy(isReported = true))
    }

    suspend fun deleteMessage(id: Int) = withContext(Dispatchers.IO) {
        chatMessageDao.deleteMessageById(id)
    }

    suspend fun blockUser(blockerEmail: String, blockedUsername: String) = withContext(Dispatchers.IO) {
        blockedUserDao.blockUser(BlockedUserEntity(blockerEmail = blockerEmail, blockedUsername = blockedUsername))
    }

    fun getBlockedUsers(blockerEmail: String): Flow<List<BlockedUserEntity>> {
        return blockedUserDao.getBlockedUsersFlow(blockerEmail)
    }

    suspend fun prepopulateIfEmpty() = withContext(Dispatchers.IO) {
        // We check groups. If empty, seed database
        val testGroup = studyGroupDao.getGroupById(1)
        if (testGroup == null) {
            val initialGroups = listOf(
                StudyGroupEntity(
                    id = 1,
                    title = "The Wisdom of Proverbs",
                    topic = "Chapter 3: Trusting the Divine Path with Pastor Elias Vance",
                    hostName = "Pastor Elias Vance",
                    scheduleText = "Live Now",
                    isPrivate = false,
                    isLive = true,
                    participantCount = 14
                ),
                StudyGroupEntity(
                    id = 2,
                    title = "Deep Dive: Romans 8",
                    topic = "Life in the Spirit and Future Glory",
                    hostName = "Sister Maria Santos",
                    scheduleText = "Tomorrow • 7:00 PM",
                    isPrivate = false,
                    isLive = false,
                    participantCount = 32
                ),
                StudyGroupEntity(
                    id = 3,
                    title = "Genesis Foundations",
                    topic = "Creation, Covenants, and Divine Sovereignty",
                    hostName = "Dr. Michael Chen",
                    scheduleText = "Wednesday • 6:00 PM",
                    isPrivate = false,
                    isLive = false,
                    participantCount = 18
                ),
                StudyGroupEntity(
                    id = 4,
                    title = "Acts of the Apostles",
                    topic = "Acts 16: The Macedonian Call and Prison Songs",
                    hostName = "Brother Timothy Miller",
                    scheduleText = "Friday • 8:30 PM",
                    isPrivate = false,
                    isLive = false,
                    participantCount = 8
                )
            )
            for (g in initialGroups) {
                studyGroupDao.insertGroup(g)
            }

            // Seed some messages for Group 1 (Wisdom of Proverbs)
            val initialMessages = listOf(
                ChatMessageEntity(
                    groupId = 1,
                    senderName = "Pastor Elias Vance",
                    messageText = "Welcome to our live study on Proverbs 3! Let's focus on verses 5-6: 'Trust in the Lord with all your heart...'",
                    isPinned = true,
                    isSystem = false
                ),
                ChatMessageEntity(
                    groupId = 1,
                    senderName = "Sister Sarah",
                    messageText = "Amen! It is so challenging but rewarding to lean not on our own understanding.",
                    isSystem = false
                ),
                ChatMessageEntity(
                    groupId = 1,
                    senderName = "Brother John",
                    messageText = "Truly. In all our ways we should acknowledge Him.",
                    isSystem = false
                ),
                ChatMessageEntity(
                    groupId = 1,
                    senderName = "Lumina Bot",
                    messageText = "Pastor Elias has shared the verse: Proverbs 3:5-6.",
                    isSystem = true
                )
            )
            for (m in initialMessages) {
                chatMessageDao.insertMessage(m)
            }
        }

        // Seed initial public prayers if database is empty
        if (prayerDao.getCount() == 0) {
            val initialPrayers = listOf(
                PrayerEntity(
                    title = "Healing for My Mother",
                    requestText = "Please pray for my mother who is undergoing surgery this Wednesday. Praying for peace and successful results.",
                    category = "Health",
                    isPublic = true,
                    authorName = "Sister Sarah",
                    amensCount = 14
                ),
                PrayerEntity(
                    title = "Wisdom in Final Exams",
                    requestText = "Asking for prayers for focus and wisdom as I study for my upcoming medical board exams.",
                    category = "Wisdom",
                    isPublic = true,
                    authorName = "Brother John",
                    amensCount = 8
                ),
                PrayerEntity(
                    title = "Family Restoration",
                    requestText = "Praying for reconciliation in my family. There has been a lot of hurt, but I know God can restore all things.",
                    category = "Family",
                    isPublic = true,
                    authorName = "Mary Lopez",
                    amensCount = 21
                ),
                PrayerEntity(
                    title = "Direction in Ministry",
                    requestText = "Seeking God's guidance on whether to start a new youth group in our local fellowship area.",
                    category = "Wisdom",
                    isPublic = true,
                    authorName = "Pastor Elias Vance",
                    amensCount = 19
                )
            )
            for (p in initialPrayers) {
                prayerDao.insertPrayer(p)
            }
        }
    }
}
