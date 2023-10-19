package com.frontmatic.sendwrite.di

import android.content.Context
import com.frontmatic.sendwrite.local.source.DataStoreSource
import com.frontmatic.sendwrite.local.source.impl.DataStoreSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StoreModule {

    @Provides
    @Singleton
    fun provideDataStoreSource(
        @ApplicationContext context: Context
    ): DataStoreSource {
        return DataStoreSourceImpl(
            context = context
        )
    }
}