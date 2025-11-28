package com.github.zsoltk.pokedex.domain.usecase

import com.github.zsoltk.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import com.github.zsoltk.pokedex.domain.repository.PokemonCatalogRepository
import com.github.zsoltk.pokedex.utils.PokeTimeUtils
import java.util.concurrent.TimeUnit

val ONE_DAY_MILLIS: Long = TimeUnit.DAYS.toMillis(1)
class EnqueueDailySyncCatalogUseCase(
    private val repository: PokemonCatalogRepository,
    private val scheduler: SyncPokemonCatalogWorkScheduler,
) {
    suspend operator fun invoke(minIntervalMillis: Long = ONE_DAY_MILLIS) {
        val lastSync = repository.getLastSyncAt()
        val now = PokeTimeUtils.getNow()
        if (now - lastSync >= minIntervalMillis) {
            scheduler.enqueueDailySyncPokemonCatalog()
        }
    }
}
