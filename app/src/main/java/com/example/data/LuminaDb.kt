package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val passwordHash: String,
    val displayName: String,
    val bio: String,
    val location: String,
    val preferredTranslation: String,
    val avatarColor: Int,
    val joinedGroupsCsv: String = "",
    val dateOfBirth: String = "",
    val stateOfOrigin: String = "",
    val country: String = "",
    val phoneNumber: String = "",
    val phoneCountryCode: String = "",
    val xp: Int = 0,
    val level: Int = 1,
    val streakDays: Int = 1,
    val lastStreakDate: String = "",
    val languageCode: String = "en"
)

@Entity(tableName = "prayers")
data class PrayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val requestText: String,
    val category: String, // e.g., "Faith", "Health", "Family", "Wisdom", "Other"
    val isAnswered: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val isPublic: Boolean = false,
    val authorName: String = "Anonymous",
    val amensCount: Int = 0
)

@Dao
interface PrayerDao {
    @Query("SELECT * FROM prayers ORDER BY timestamp DESC")
    fun getAllPrayersFlow(): Flow<List<PrayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayer(prayer: PrayerEntity): Long

    @Update
    suspend fun updatePrayer(prayer: PrayerEntity)

    @Query("DELETE FROM prayers WHERE id = :id")
    suspend fun deletePrayerById(id: Int)

    @Query("SELECT COUNT(*) FROM prayers")
    suspend fun getCount(): Int

    @Query("UPDATE prayers SET amensCount = amensCount + 1 WHERE id = :id")
    suspend fun incrementAmenCount(id: Int)
}

@Entity(tableName = "study_groups")
data class StudyGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val topic: String,
    val hostName: String,
    val scheduleText: String,
    val isPrivate: Boolean,
    val isLive: Boolean,
    val participantCount: Int
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val groupId: Int,
    val senderName: String,
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false,
    val isReported: Boolean = false,
    val isSystem: Boolean = false
)

@Entity(tableName = "blocked_users")
data class BlockedUserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val blockerEmail: String,
    val blockedUsername: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsersFlow(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)
}

@Dao
interface StudyGroupDao {
    @Query("SELECT * FROM study_groups ORDER BY isLive DESC, id DESC")
    fun getAllGroupsFlow(): Flow<List<StudyGroupEntity>>

    @Query("SELECT * FROM study_groups WHERE id = :id LIMIT 1")
    suspend fun getGroupById(id: Int): StudyGroupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: StudyGroupEntity): Long

    @Update
    suspend fun updateGroup(group: StudyGroupEntity)
}

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages WHERE groupId = :groupId ORDER BY timestamp ASC")
    fun getMessagesForGroupFlow(groupId: Int): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Update
    suspend fun updateMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages WHERE id = :id")
    suspend fun deleteMessageById(id: Int)

    @Query("DELETE FROM chat_messages WHERE groupId = :groupId")
    suspend fun clearMessagesForGroup(groupId: Int)
}

@Dao
interface BlockedUserDao {
    @Query("SELECT * FROM blocked_users WHERE blockerEmail = :blockerEmail")
    fun getBlockedUsersFlow(blockerEmail: String): Flow<List<BlockedUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun blockUser(blocked: BlockedUserEntity)

    @Query("DELETE FROM blocked_users WHERE blockerEmail = :blockerEmail AND blockedUsername = :blockedUsername")
    suspend fun unblockUser(blockerEmail: String, blockedUsername: String)
}

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "unlocked_badges")
data class UnlockedBadgeEntity(
    @PrimaryKey val id: String, // e.g. "first_spark"
    val title: String,
    val description: String,
    val unlockTime: Long = System.currentTimeMillis()
)

// --- NEW ENTITIES FOR ADDITIONAL FEATURES ---

@Entity(tableName = "study_notes")
data class StudyNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val book: String,
    val chapter: Int,
    val verseRange: String, // e.g. "5-6" or "General"
    val noteText: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_progress")
data class StudyProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val book: String,
    val chapter: Int,
    val isCompleted: Boolean = true,
    val dateCompleted: String, // e.g., "2026-07-15"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "verse_highlights")
data class VerseHighlightEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val book: String,
    val chapter: Int,
    val verse: Int,
    val colorHex: String, // e.g., "#E5A93B", "#4A90E2"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "group_notifications")
data class GroupNotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val groupId: Int,
    val groupTitle: String,
    val notificationType: String, // "LIVE_START", "NEW_MESSAGE", "ANNOUNCEMENT"
    val messageText: String,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_reminders")
data class StudyReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val title: String,
    val timeString: String, // e.g. "08:00 AM" or "20:30"
    val daysCsv: String,    // e.g. "Mon, Wed, Fri" or "Daily"
    val isEnabled: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface StudyReminderDao {
    @Query("SELECT * FROM study_reminders WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getRemindersForUserFlow(email: String): Flow<List<StudyReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: StudyReminderEntity): Long

    @Update
    suspend fun updateReminder(reminder: StudyReminderEntity)

    @Query("DELETE FROM study_reminders WHERE id = :id")
    suspend fun deleteReminderById(id: Int)
}

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 20")
    fun getRecentSearchHistoryFlow(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE query = :query")
    suspend fun deleteSearchByQuery(query: String)

    @Query("DELETE FROM search_history")
    suspend fun clearHistory()
}

@Dao
interface UnlockedBadgeDao {
    @Query("SELECT * FROM unlocked_badges")
    fun getUnlockedBadgesFlow(): Flow<List<UnlockedBadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockBadge(badge: UnlockedBadgeEntity)

    @Query("DELETE FROM unlocked_badges")
    suspend fun clearBadges()
}

@Dao
interface StudyNoteDao {
    @Query("SELECT * FROM study_notes WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getNotesForUserFlow(email: String): Flow<List<StudyNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: StudyNoteEntity): Long

    @Query("DELETE FROM study_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Int)
}

@Dao
interface StudyProgressDao {
    @Query("SELECT * FROM study_progress WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getProgressForUserFlow(email: String): Flow<List<StudyProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: StudyProgressEntity): Long

    @Query("DELETE FROM study_progress WHERE userEmail = :email AND book = :book AND chapter = :chapter")
    suspend fun removeProgress(email: String, book: String, chapter: Int)
}

@Dao
interface VerseHighlightDao {
    @Query("SELECT * FROM verse_highlights WHERE userEmail = :email")
    fun getHighlightsForUserFlow(email: String): Flow<List<VerseHighlightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: VerseHighlightEntity): Long

    @Query("DELETE FROM verse_highlights WHERE userEmail = :email AND book = :book AND chapter = :chapter AND verse = :verse")
    suspend fun removeHighlight(email: String, book: String, chapter: Int, verse: Int)
}

@Dao
interface GroupNotificationDao {
    @Query("SELECT * FROM group_notifications WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getNotificationsForUserFlow(email: String): Flow<List<GroupNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: GroupNotificationEntity): Long

    @Query("UPDATE group_notifications SET isRead = 1 WHERE userEmail = :email")
    suspend fun markAllAsRead(email: String)

    @Query("DELETE FROM group_notifications WHERE id = :id")
    suspend fun deleteNotificationById(id: Int)
}

@Database(
    entities = [
        UserEntity::class, 
        StudyGroupEntity::class, 
        ChatMessageEntity::class, 
        BlockedUserEntity::class,
        SearchHistoryEntity::class,
        UnlockedBadgeEntity::class,
        PrayerEntity::class,
        StudyNoteEntity::class,
        StudyProgressEntity::class,
        VerseHighlightEntity::class,
        GroupNotificationEntity::class,
        StudyReminderEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun studyGroupDao(): StudyGroupDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun blockedUserDao(): BlockedUserDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun unlockedBadgeDao(): UnlockedBadgeDao
    abstract fun prayerDao(): PrayerDao
    abstract fun studyNoteDao(): StudyNoteDao
    abstract fun studyProgressDao(): StudyProgressDao
    abstract fun verseHighlightDao(): VerseHighlightDao
    abstract fun groupNotificationDao(): GroupNotificationDao
    abstract fun studyReminderDao(): StudyReminderDao
}
