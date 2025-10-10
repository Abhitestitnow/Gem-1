package com.example.flashcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.Flashcard
import com.example.flashcards.data.FlashcardDatabase
import com.example.flashcards.data.FlashcardGroup
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = FlashcardDatabase.getDatabase(application).flashcardDao()

    val groups: StateFlow<List<FlashcardGroup>> = dao.getAllGroups()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getFlashcardsForGroup(groupId: Int): StateFlow<List<Flashcard>> =
        dao.getFlashcardsByGroup(groupId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addGroup(name: String) = viewModelScope.launch {
        dao.insertGroup(FlashcardGroup(name = name))
    }

    fun addFlashcard(question: String, answer: String, groupId: Int) = viewModelScope.launch {
        dao.insertFlashcard(Flashcard(question = question, answer = answer, groupId = groupId))
    }

    fun deleteFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        dao.deleteFlashcard(flashcard)
    }
}
