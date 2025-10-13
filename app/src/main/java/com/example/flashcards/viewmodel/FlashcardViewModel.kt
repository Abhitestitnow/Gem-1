package com.example.flashcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.Flashcard
import com.example.flashcards.data.FlashcardGroup
import com.example.flashcards.data.FlashcardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class FlashcardViewModel(
    private val repository: FlashcardRepository = FlashcardRepository()
) : ViewModel() {

    // Expose groups as a StateFlow to UI, started eagerly
    val groups: StateFlow<List<FlashcardGroup>> = repository.getAllGroups()
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Insert a group
    fun addGroup(groupName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGroup(FlashcardGroup(groupName = groupName))
        }
    }

    // Get flashcards for a given group as Flow
    fun getFlashcardsForGroup(groupId: Int) = 
        repository.getFlashcardsByGroup(groupId)
            .flowOn(Dispatchers.IO)

    // Insert a flashcard
    fun addFlashcard(question: String, answer: String, groupId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFlashcard(Flashcard(question = question, answer = answer, groupId = groupId))
        }
    }

    // Delete a flashcard
    fun deleteFlashcard(flashcard: Flashcard) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFlashcard(flashcard)
        }
    }
}
