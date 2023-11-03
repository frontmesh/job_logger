package com.frontmatic.sendwrite.di

import android.content.Context
import com.kedia.ogparser.OpenGraphCacheProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OpenGraphModule {

    @Provides
    @Singleton
    fun providesOpenGraphCacheProvider(
        @ApplicationContext context: Context
    ): OpenGraphCacheProvider {
        return OpenGraphCacheProvider(context)
    }
}