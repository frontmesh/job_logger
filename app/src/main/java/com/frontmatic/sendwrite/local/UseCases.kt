package com.frontmatic.sendwrite.local

import com.frontmatic.sendwrite.local.model.Job

class SaveJobUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(job: Job) {
        repository.saveJob(job)
    }
}

class GetAllJobsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getAllJobs()
}

data class UseCases(
    val saveJobUseCase: SaveJobUseCase,
    val getAllJobs: GetAllJobsUseCase,
)