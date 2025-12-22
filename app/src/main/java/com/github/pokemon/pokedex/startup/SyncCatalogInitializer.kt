package com.github.pokemon.pokedex.startup

import com.github.pokemon.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase

class SyncCatalogInitializer(
    val enqueueSyncCatalogUseCase: EnqueueDailySyncCatalogUseCase,
) : AsyncAppInitializer {

    override suspend fun invoke() {
        enqueueSyncCatalogUseCase()
    }
}
