package com.frontmatic.sendwrite.di

import android.content.Context
import com.frontmatic.sendwrite.repository.NotificationRepository
import com.frontmatic.sendwrite.repository.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationRepository(
        @ApplicationContext context: Context
    ): NotificationRepository {
        return NotificationRepositoryImpl(
            appContext = context
        )
    }
}