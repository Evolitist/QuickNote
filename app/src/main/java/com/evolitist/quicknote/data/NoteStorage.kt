package com.evolitist.quicknote.data

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.evolitist.quicknote.domain.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

@Singleton
class NoteStorage @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val json: Json,
) {

    companion object {
        private val PrefsDataKey = stringSetPreferencesKey("data")
    }

    private val notesList by lazy {
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("prefs")
        }
    }

    fun getNotes(): Flow<List<Note>> {
        return notesList.data.map { prefs ->
            prefs[PrefsDataKey]?.map { json.decodeFromString(it) } ?: emptyList()
        }
    }

    suspend fun addNote(text: String) {
        notesList.edit {
            val currentData = it[PrefsDataKey] ?: emptySet()
            val newNote = Note(text = text)
            it[PrefsDataKey] = currentData + json.encodeToString(newNote)
        }
    }

    suspend fun deleteNote(id: String) {
        notesList.edit { prefs ->
            val currentData = prefs[PrefsDataKey] ?: emptySet()
            prefs[PrefsDataKey] = currentData
                .map { json.decodeFromString<Note>(it) }
                .filterNot { it.id == id }
                .map { json.encodeToString(it) }
                .toSet()
        }
    }
}
