package com.evolitist.quicknote.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.evolitist.quicknote.presentation.theme.QuickNoteTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val PrefsDataKey = stringSetPreferencesKey("data")

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val prefs = remember {
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("prefs")
        }
    }
    val textList by prefs.data
        .map { it[PrefsDataKey]?.toList() ?: emptyList() }
        .collectAsState(emptyList())
    var text by remember { mutableStateOf("") }

    fun saveValue() {
        val keyedText = "" + System.currentTimeMillis() + "|$text"
        scope.launch {
            prefs.updateData {
                val prefs = it.toMutablePreferences()
                prefs[PrefsDataKey] = (textList + keyedText).toSet()
                prefs.toPreferences()
            }
        }
        text = ""
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                keyboardActions = KeyboardActions {
                    saveValue()
                },
                trailingIcon = {
                    Button(
                        onClick = {
                            saveValue()
                        },
                        content = { Text("Save") },
                        colors = ButtonDefaults.textButtonColors()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
            LazyColumn {
                items(
                    items = textList,
                    key = { it.split("|", limit = 2).first() },
                ) {
                    ListItem(
                        headlineContent = {
                            Text(it.split("|", limit = 2).last())
                        },
                        trailingContent = {
                            Button(
                                onClick = {},
                                content = { Text("Delete") },
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_7_PRO, showSystemUi = true)
@Composable
private fun ListScreen_Preview() {
    QuickNoteTheme {
        ListScreen()
    }
}
