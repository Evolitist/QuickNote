package com.evolitist.quicknote.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.evolitist.quicknote.presentation.list.ListScreen
import com.evolitist.quicknote.presentation.newnote.NewNoteScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun QuickNoteNavigation() {
    val backStack = rememberNavBackStack(Route.List)

    SharedTransitionScope {
        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            modifier = it.background(MaterialTheme.colorScheme.background),
            entryProvider = entryProvider {
                entry<Route.List> {
                    ListScreen(
                        onAddClick = { backStack.add(Route.NewNote) },
                    )
                }

                entry<Route.NewNote> {
                    NewNoteScreen(
                        onBackClick = { backStack.removeAt(backStack.lastIndex) },
                    )
                }
            },
        )
    }
}
