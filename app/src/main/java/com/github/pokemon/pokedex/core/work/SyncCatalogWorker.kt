package com.github.pokemon.pokedex.core.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.github.pokemon.pokedex.domain.exception.PokeException.WorkException
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import com.github.pokemon.pokedex.domain.repository.SyncCatalogRepository
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import retrofit2.HttpException
import java.io.IOException

class SyncCatalogWorker(
    appContext: Context,
    params: WorkerParameters,
    private val catalogRepository: CatalogRepository,
    private val syncCatalogRepository: SyncCatalogRepository,
    private val pokeTimeUtil: PokeTimeUtil,
    private val loggerError: LoggerError,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return catalogRepository.syncCatalog().fold(
            onSuccess = {
                Log.d("SyncPokemonCatalogWorker", "Success sync pokemon catalog with $it items")
                syncCatalogRepository.setLastSyncAt(pokeTimeUtil.now())
                return Result.success(workDataOf("synced" to true))
            },
            onFailure = { error ->
                loggerError(message = "Error sync pokemon catalog", error = WorkException(cause = error.cause))
                val isTransient = error is IOException || error is HttpException
                if (isTransient) Result.retry() else Result.failure()
            },
        )
    }
}
