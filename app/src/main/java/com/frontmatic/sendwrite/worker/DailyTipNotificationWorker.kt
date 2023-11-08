package com.frontmatic.sendwrite.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.frontmatic.sendwrite.repository.NotificationRepository
import com.frontmatic.sendwrite.repository.NotificationRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Duration


@HiltWorker
class DailyTipNotificationWorker  @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val notificationRepository: NotificationRepositoryImpl,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val uniqueWorkName = DailyTipNotificationWorker::class.java.simpleName

        @RequiresApi(Build.VERSION_CODES.O)
        fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = PeriodicWorkRequestBuilder<DailyTipNotificationWorker>(
                repeatInterval = Duration.ofDays(1)
            )

            var workPolicy = ExistingPeriodicWorkPolicy.KEEP

            // Replace any enqueued work and expedite the request
            if (force) {
                workPolicy = ExistingPeriodicWorkPolicy.UPDATE
            }

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.build()
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            notificationRepository.showDailyTipNotification()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 10) {
                // Exponential backoff strategy will avoid the request to repeat
                // too fast in case of failures.
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}