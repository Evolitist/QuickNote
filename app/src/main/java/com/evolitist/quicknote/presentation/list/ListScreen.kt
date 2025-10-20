package com.evolitist.quicknote.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.evolitist.quicknote.presentation.theme.QuickNoteTheme

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val textList = remember { mutableStateListOf<String>() }
    var text by remember { mutableStateOf("") }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Row {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                )
                Button(
                    onClick = {
                        textList.add(text)
                        text = ""
                    },
                    content = { Text("Save") },
                )
            }
            LazyColumn {
                items(textList) {
                    ListItem(
                        headlineContent = { Text(it) },
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
