package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.local.SyncCatalogLocalDataSource
import com.github.pokemon.pokedex.domain.repository.SyncCatalogRepository

class LocalSyncCatalogRepository(private val localDataSource: SyncCatalogLocalDataSource) : SyncCatalogRepository {

    override suspend fun getLastSyncAt(): Long = localDataSource.getLastSyncAt()

    override suspend fun setLastSyncAt(lastSyncTime: Long) = localDataSource.setLastSyncAt(lastSyncTime)
}
