package com.github.zsoltk.pokedex.data.datasource.local

interface PokemonCatalogLocalDataSource {
    suspend fun getLastSyncAt(): Long
    suspend fun setLastSyncAt(value: Long)
}
