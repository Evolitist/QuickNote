package com.evolitist.quicknote.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.evolitist.quicknote.presentation.list.ListScreen
import com.evolitist.quicknote.presentation.theme.QuickNoteTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickNoteTheme {
                ListScreen()
            }
        }
    }
}
