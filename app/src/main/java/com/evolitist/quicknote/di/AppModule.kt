package com.evolitist.quicknote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}
