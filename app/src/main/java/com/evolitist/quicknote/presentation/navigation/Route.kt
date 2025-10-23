package com.evolitist.quicknote.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Route : NavKey {

    @Serializable
    data object List : Route()

    @Serializable
    data object NewNote : Route()
}
