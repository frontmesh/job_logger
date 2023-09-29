package com.frontmatic.sendwrite.local

import com.frontmatic.sendwrite.local.model.Job

class SaveJobUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(job: Job) {
        return repository.saveJob(job)
    }
}

class GetAllJobsUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getAllJobs()
}


class GetJobByIdUseCase(
    private val repository: Repository
) {
    operator fun invoke(id: Long) = repository.getJobById(id)
}

data class UseCases(
    val saveJobUseCase: SaveJobUseCase,
    val getAllJobs: GetAllJobsUseCase,
    val getJobById: GetJobByIdUseCase,
)