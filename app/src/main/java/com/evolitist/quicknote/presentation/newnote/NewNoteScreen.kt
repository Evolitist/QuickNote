package com.evolitist.quicknote.presentation.newnote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
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

    val state = rememberTextFieldState()
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
                actions = {
                    IconButton(
                        onClick = { viewModel.onAddNoteClick(state.text.toString()) },
                        content = { Icon(painterResource(R.drawable.ic_check), contentDescription = null) },
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = modifier,
    ) { innerPadding ->
        TextField(
            state = state,
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
