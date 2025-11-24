package com.github.zsoltk.pokedex.core.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class SyncPokemonCatalogWorkScheduler(private val context: Context) {
    fun enqueueDailySyncPokemonCatalog() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SyncPokemonCatalogWorker>()
            .setConstraints(constraints)
            .addTag(UNIQUE_ONE_TIME_LIGHT)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            UNIQUE_ONE_TIME_LIGHT,
            ExistingWorkPolicy.KEEP,
            request,
        )
    }

    companion object {
        const val UNIQUE_ONE_TIME_LIGHT = "one_time_light_sync"
    }
}
