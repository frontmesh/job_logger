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

class GetAllArchivedJobsUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getAllArchivedJobs()
}

class GetJobByIdUseCase(
    private val repository: Repository
) {
    operator fun invoke(id: Long) = repository.getJobById(id)
}

class DeleteJobUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(job: Job) {
        return repository.deleteJob(job)
    }
}

data class UseCases(
    val saveJobUseCase: SaveJobUseCase,
    val deleteJob : DeleteJobUseCase,
    val getAllJobs: GetAllJobsUseCase,
    val getAllArchivedJobs: GetAllArchivedJobsUseCase,
    val getJobById: GetJobByIdUseCase,
)