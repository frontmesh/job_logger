package com.frontmatic.sendwrite.di

import android.content.Context
import androidx.room.Room
import com.frontmatic.sendwrite.local.LocalDataSource
import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.source.impl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SendWriteDatabase::class.java,
        "sendwrite_database"
    ).build()

    @Provides
    @Singleton
    fun providesLocalDataSource(
        database: SendWriteDatabase
    ) : LocalDataSource {
        return LocalDataSourceImpl(database)
    }
}