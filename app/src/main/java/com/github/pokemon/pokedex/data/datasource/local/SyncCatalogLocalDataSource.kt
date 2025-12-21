package com.github.pokemon.pokedex.data.datasource.local

interface SyncCatalogLocalDataSource {
    suspend fun getLastSyncAt(): Long
    suspend fun setLastSyncAt(value: Long)
}
