package com.github.pokemon.pokedex.core.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.github.pokemon.pokedex.core.common.error.WorkerException
import com.github.pokemon.pokedex.core.common.loggin.DefaultLoggerError
import com.github.pokemon.pokedex.domain.repository.PokemonCatalogRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

class SyncPokemonCatalogWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val repository: PokemonCatalogRepository by inject()

    override suspend fun doWork(): Result {
        return repository.syncPokemonCatalog().fold(
            onSuccess = {
                Log.d("SyncPokemonCatalogWorker", "Success sync pokemon catalog with $it items")
                val now = System.currentTimeMillis()
                repository.setLastSyncAt(now)
                return Result.success(workDataOf("synced" to true))
            },
            onFailure = { error ->
                DefaultLoggerError().logError(
                    message = "Error sync pokemon catalog",
                    error = WorkerException(cause = error.cause),
                )
                val isTransient = error is IOException || error is HttpException
                if (isTransient) Result.retry() else Result.failure()
            },
        )
    }
}
