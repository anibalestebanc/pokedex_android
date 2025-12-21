package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import com.github.pokemon.pokedex.domain.repository.SyncCatalogRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import java.util.concurrent.TimeUnit

class EnqueueDailySyncCatalogUseCase(
    private val repository: SyncCatalogRepository,
    private val scheduler: SyncPokemonCatalogWorkScheduler,
    private val pokeTimeUtil: PokeTimeUtil
) {
    suspend operator fun invoke(minInterval: Long = ONE_DAY_MILLIS) {
        val lastSync = repository.getLastSyncAt()
        val now = pokeTimeUtil.now()
        when {
            lastSync == 0L -> scheduler.enqueueImmediateSync()
            now - lastSync >= minInterval -> scheduler.enqueueDailySync()
            else -> Unit
        }
    }

    private companion object {
        val ONE_DAY_MILLIS: Long = TimeUnit.DAYS.toMillis(1)
    }
}
