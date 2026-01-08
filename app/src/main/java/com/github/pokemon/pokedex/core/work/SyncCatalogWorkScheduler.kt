package com.github.pokemon.pokedex.core.work

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SyncCatalogWorkScheduler(private val context: Context) {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    private val workManager: WorkManager
        get() = WorkManager.getInstance(context)

    fun enqueueImmediateSync() {
        val request = OneTimeWorkRequestBuilder<SyncCatalogWorker>()
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, BACKOFF_DELAY, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            UNIQUE_IMMEDIATE_SYNC,
            ExistingWorkPolicy.REPLACE,
            request,
        )
    }

    fun enqueueDailySync() {
        val request = PeriodicWorkRequestBuilder<SyncCatalogWorker>(INTERVAL, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, BACKOFF_DELAY, TimeUnit.SECONDS)
            .addTag(UNIQUE_DAILY_SYNC)
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_DAILY_SYNC,
            ExistingPeriodicWorkPolicy.UPDATE,
            request,
        )
    }

    private companion object Companion {
        const val BACKOFF_DELAY = 30L
        const val INTERVAL = 24L
        const val UNIQUE_IMMEDIATE_SYNC = "catalog_sync_immediate"
        const val UNIQUE_DAILY_SYNC = "catalog_sync_daily"
    }
}
