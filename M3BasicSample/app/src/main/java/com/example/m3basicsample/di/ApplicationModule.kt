package com.example.m3basicsample.di

import android.app.LocaleManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApplicationModule {
    @Singleton
    @Provides
    fun provideMyLocaleManager(@ApplicationContext context: Context): LocaleManager {
        return context.getSystemService(LocaleManager::class.java)
    }
}