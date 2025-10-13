package com.example.flashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_groups")
data class FlashcardGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val groupName: String
)
