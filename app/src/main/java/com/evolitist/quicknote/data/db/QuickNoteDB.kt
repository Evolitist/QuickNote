package com.evolitist.quicknote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class],
    version = 1,
)
abstract class QuickNoteDB : RoomDatabase() {

    abstract fun dao(): NotesDAO
}
