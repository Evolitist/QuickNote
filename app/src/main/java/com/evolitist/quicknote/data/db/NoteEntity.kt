package com.evolitist.quicknote.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String = Uuid.random().toString(),
    val title: String,
    val text: String,
    //val tags: List<String>,
    val dateCreated: Long,
)
