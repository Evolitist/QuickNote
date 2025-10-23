package com.evolitist.quicknote.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evolitist.quicknote.R
import com.evolitist.quicknote.domain.model.Note
import com.evolitist.quicknote.extensions.plus
import com.evolitist.quicknote.presentation.theme.QuickNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
) {
    val textList by viewModel.notes.collectAsStateWithLifecycle(emptyList())
    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()

    Scaffold(
        topBar = {
            BoxWithConstraints {
                TopSearchBar(
                    state = searchBarState,
                    inputField = {
                        SearchBarDefaults.InputField(
                            textFieldState = textFieldState,
                            searchBarState = searchBarState,
                            onSearch = {},
                            modifier = Modifier.sizeIn(
                                minWidth = this.maxWidth - 16.dp.times(2),
                            ),
                        )
                    },
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                content = { Icon(painterResource(R.drawable.ic_add), contentDescription = null) },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier,
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            contentPadding = innerPadding + PaddingValues(16.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = textList,
                key = { it.id },
            ) {
                NoteItem(
                    note = it,
                    onDismiss = { viewModel.deleteNote(it.id) },
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            Box(
                contentAlignment = when (state.dismissDirection) {
                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                    SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                    SwipeToDismissBoxValue.Settled -> Alignment.Center
                },
                modifier = Modifier.fillMaxSize(),
            ) {
                Icon(painterResource(R.drawable.ic_delete), contentDescription = null)
            }
        },
        onDismiss = { onDismiss() },
        modifier = modifier,
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = note.text,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(device = Devices.PIXEL_7_PRO, showSystemUi = true)
@Composable
private fun ListScreen_Preview() {
    QuickNoteTheme {
        ListScreen(
            onAddClick = {},
        )
    }
}
