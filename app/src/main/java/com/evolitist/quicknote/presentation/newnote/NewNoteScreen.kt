package com.evolitist.quicknote.presentation.newnote

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.evolitist.quicknote.R
import com.evolitist.quicknote.presentation.theme.QuickNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewNoteViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.noteCreatedEvent.collect {
            onBackClick()
        }
    }

    var text by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        content = { Icon(painterResource(R.drawable.ic_close), contentDescription = null) },
                    )
                },
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddNoteClick(text) },
                content = { Icon(painterResource(R.drawable.ic_add), contentDescription = null) },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = modifier,
    ) { innerPadding ->
        TextField(
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            shape = RectangleShape,
            placeholder = { Text("What do you want to note?") },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview(device = Devices.PIXEL_7_PRO, showSystemUi = true)
@Composable
private fun NewNoteScreen_Preview() {
    QuickNoteTheme {
        NewNoteScreen(
            onBackClick = {},
        )
    }
}
