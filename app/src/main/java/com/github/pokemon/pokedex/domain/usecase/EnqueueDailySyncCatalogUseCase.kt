package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.core.work.SyncCatalogWorkScheduler
import com.github.pokemon.pokedex.data.datasource.local.SyncKeys
import com.github.pokemon.pokedex.data.datasource.local.SyncMetaStore
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import java.util.concurrent.TimeUnit

class EnqueueDailySyncCatalogUseCase(
    private val syncMetaStore: SyncMetaStore,
    private val scheduler: SyncCatalogWorkScheduler,
    private val pokeTimeUtil: PokeTimeUtil
) {
    suspend operator fun invoke(minInterval: Long = ONE_DAY_MILLIS) {
        val lastSync = syncMetaStore.getLastSyncAt(SyncKeys.LAST_SYNC_CATALOG)
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
