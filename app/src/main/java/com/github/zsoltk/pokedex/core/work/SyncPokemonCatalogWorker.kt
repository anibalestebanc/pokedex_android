package com.github.zsoltk.pokedex.core.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.github.zsoltk.pokedex.core.common.error.WorkerException
import com.github.zsoltk.pokedex.core.common.loggin.LoggerError
import com.github.zsoltk.pokedex.domain.repository.PokemonCatalogRepository
import com.github.zsoltk.pokedex.utils.PokeTimeUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncPokemonCatalogWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val repository: PokemonCatalogRepository by inject()

    override suspend fun doWork(): Result {
        repository.syncPokemonCatalog().fold(
            onSuccess = {
                Log.d("SyncPokemonCatalogWorker", "Success sync pokemon catalog with $it items")
                repository.setLastSyncAt(PokeTimeUtils.getNow())
                return Result.success(workDataOf("synced" to true))
            },
            onFailure = {
                LoggerError.logError(
                    message = "Error sync pokemon catalog",
                    error = WorkerException(cause = it.cause)
                )
                return Result.failure(workDataOf("synced" to false))
            },
        )
    }
}
