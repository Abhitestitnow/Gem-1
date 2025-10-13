package com.example.flashcards.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM flashcard_groups")
    fun getAllGroups(): Flow<List<FlashcardGroup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: FlashcardGroup)

    @Query("SELECT * FROM flashcards WHERE groupId = :groupId")
    fun getFlashcardsByGroup(groupId: Int): Flow<List<Flashcard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)
}
