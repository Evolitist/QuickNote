package com.evolitist.quicknote.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evolitist.quicknote.data.NoteStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class ListViewModel @Inject constructor(
    private val noteStorage: NoteStorage,
) : ViewModel() {

    private val deletedNoteIdFlow = MutableStateFlow<String?>(null)
    val notes = noteStorage.getNotes()
        .combine(deletedNoteIdFlow) { list, deletedId ->
            list.filterNot { it.id == deletedId }
        }

    fun markNoteDeleted(id: String?) {
        deletedNoteIdFlow.value = id
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            noteStorage.deleteNote(id)
            deletedNoteIdFlow.value = null
        }
    }
}
