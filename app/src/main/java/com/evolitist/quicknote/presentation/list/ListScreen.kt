package com.evolitist.quicknote.presentation.list

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import com.evolitist.quicknote.presentation.theme.QuickNoteTheme

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences(
            "prefs",
            Context.MODE_PRIVATE,
        )
    }
    val textList = remember {
        val prefSet = prefs.getStringSet("data", emptySet<String>())!!.toList()
        SnapshotStateList(prefSet.size) { prefSet[it] }
    }
    var text by remember { mutableStateOf("") }

    fun saveValue() {
        val keyedText = "" + System.currentTimeMillis() + "|$text"
        textList.add(keyedText)
        prefs.edit {
            val prefSet = textList.toSet()
            putStringSet(
                "data",
                prefSet,
            )
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
