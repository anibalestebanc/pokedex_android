package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import com.github.pokemon.pokedex.domain.repository.PokemonCatalogRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import java.util.concurrent.TimeUnit

val ONE_DAY_MILLIS: Long = TimeUnit.DAYS.toMillis(1)

class EnqueueDailySyncCatalogUseCase(
    private val repository: PokemonCatalogRepository,
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
}
