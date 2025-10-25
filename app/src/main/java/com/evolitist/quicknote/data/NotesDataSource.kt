package com.evolitist.quicknote.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class NotesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    companion object {
        private val PrefsDataKey = stringSetPreferencesKey("data")
    }

    val notes = dataStore.data
        .map { it[PrefsDataKey]?.toList() ?: emptyList() }

    suspend fun addNote(content: String) {
        dataStore.edit {
            val currentList = it[PrefsDataKey] ?: emptySet()
            it[PrefsDataKey] = currentList + content
        }
    }
}
