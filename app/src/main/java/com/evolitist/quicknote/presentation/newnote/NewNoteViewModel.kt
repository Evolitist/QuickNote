package com.evolitist.quicknote.presentation.newnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evolitist.quicknote.data.NoteStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val noteStorage: NoteStorage,
) : ViewModel() {

    private val _noteCreatedEvent = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val noteCreatedEvent = _noteCreatedEvent.asSharedFlow()

    fun onAddNoteClick(text: String) {
        viewModelScope.launch {
            noteStorage.addNote(text)
            _noteCreatedEvent.emit(Unit)
        }
    }
}
