package com.github.pokemon.pokedex.domain.repository

interface SyncCatalogRepository {
    suspend fun getLastSyncAt(): Long
    suspend fun setLastSyncAt(lastSyncTime: Long)
}
