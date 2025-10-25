package com.evolitist.quicknote.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evolitist.quicknote.data.NotesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.text.split

@HiltViewModel
class ListViewModel @Inject constructor(
    private val notesDataSource: NotesDataSource,
) : ViewModel() {

    companion object {
        private const val NoteSeparator = "|"
    }

    val notesFlow = notesDataSource.notes
        .map { list ->
            list.map {
                val parts = it.split(NoteSeparator, limit = 2)
                Note(parts.first(), parts.last())
            }
        }

    fun addNote(text: String) {
        viewModelScope.launch {
            val content = System.currentTimeMillis().toString() + NoteSeparator + text
            notesDataSource.addNote(content)
        }
    }
}
