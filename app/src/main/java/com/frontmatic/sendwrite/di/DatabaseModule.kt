package com.frontmatic.sendwrite.di

import android.content.Context
import androidx.room.Room
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.local.source.DataStoreSource
import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.dao.read.JobEventReadDao
import com.frontmatic.sendwrite.local.source.JobEventReadDataSource
import com.frontmatic.sendwrite.local.source.JobReadDataSource
import com.frontmatic.sendwrite.local.source.impl.JobLocalDataSourceImpl
import com.frontmatic.sendwrite.local.source.impl.JobEventLocalDataSourceImpl
import com.frontmatic.sendwrite.local.source.impl.DataStoreSourceImpl
import com.frontmatic.sendwrite.local.source.impl.JobEventReadDataSourceImpl
import com.frontmatic.sendwrite.local.source.impl.JobReadDataSourceImpl
import com.frontmatic.sendwrite.util.DB_NAME
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
        DB_NAME
    ).build()

    @Provides
    @Singleton
    fun providesJobLocalDataSource(
        database: SendWriteDatabase
    ) : JobLocalDataSource {
        return JobLocalDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun providesJobEventLocalDataSource(
        database: SendWriteDatabase
    ) : JobEventLocalDataSource {
        return JobEventLocalDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun providesJobReadDataSource(
        database: SendWriteDatabase
    ) : JobReadDataSource {
        return JobReadDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun providesJobEventReadDataSource(
        database: SendWriteDatabase
    ) : JobEventReadDataSource {
        return JobEventReadDataSourceImpl(database)
    }

}