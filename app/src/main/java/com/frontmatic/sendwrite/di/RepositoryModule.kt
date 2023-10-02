package com.frontmatic.sendwrite.di

import com.frontmatic.sendwrite.local.DeleteJobUseCase
import com.frontmatic.sendwrite.local.GetAllArchivedJobsUseCase
import com.frontmatic.sendwrite.local.GetAllJobsUseCase
import com.frontmatic.sendwrite.local.GetJobByIdUseCase
import com.frontmatic.sendwrite.local.Repository
import com.frontmatic.sendwrite.local.SaveJobUseCase
import com.frontmatic.sendwrite.local.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesUseCases(repository: Repository): UseCases {
        return UseCases(
            saveJobUseCase = SaveJobUseCase(repository),
            getAllJobs = GetAllJobsUseCase(repository),
            getJobById = GetJobByIdUseCase(repository),
            deleteJob = DeleteJobUseCase(repository),
            getAllArchivedJobs = GetAllArchivedJobsUseCase(repository)
        )
    }
}