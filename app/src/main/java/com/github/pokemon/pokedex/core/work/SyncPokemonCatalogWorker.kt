package com.github.pokemon.pokedex.core.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.github.pokemon.pokedex.utils.DefaultLoggerError
import com.github.pokemon.pokedex.domain.exception.PokeException.WorkException
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import com.github.pokemon.pokedex.domain.repository.SyncCatalogRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

class SyncPokemonCatalogWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val catalogRepository: CatalogRepository by inject()
    private val syncCatalogRepository: SyncCatalogRepository by inject()


    override suspend fun doWork(): Result {
        return catalogRepository.syncCatalog().fold(
            onSuccess = {
                Log.d("SyncPokemonCatalogWorker", "Success sync pokemon catalog with $it items")
                val now = System.currentTimeMillis()
                syncCatalogRepository.setLastSyncAt(now)
                return Result.success(workDataOf("synced" to true))
            },
            onFailure = { error ->
                DefaultLoggerError().logError(
                    message = "Error sync pokemon catalog",
                    error = WorkException(cause = error.cause),
                )
                val isTransient = error is IOException || error is HttpException
                if (isTransient) Result.retry() else Result.failure()
            },
        )
    }
}
