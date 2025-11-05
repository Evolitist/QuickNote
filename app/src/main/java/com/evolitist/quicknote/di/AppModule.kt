package com.evolitist.quicknote.di

import android.content.Context
import androidx.room.Room
import com.evolitist.quicknote.data.db.QuickNoteDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideJson(): Json {
        return Json {
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): QuickNoteDB {
        return Room.databaseBuilder(
            context,
            QuickNoteDB::class.java,
            "notes_db",
        ).build()
    }
}
