package com.github.pokemon.pokedex.domain.repository

interface PokemonCatalogRepository {
    suspend fun syncPokemonCatalog(): Result<Int>
    suspend fun getLastSyncAt(): Long
    suspend fun setLastSyncAt(lastSyncTime: Long)
}
