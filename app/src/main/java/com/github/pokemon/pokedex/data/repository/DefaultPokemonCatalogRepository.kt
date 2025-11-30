package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.data.datasource.cache.PokemonCatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.local.PokemonCatalogLocalDataSource
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.data.datasource.remote.PokemonCatalogRemoteDataSource
import com.github.pokemon.pokedex.domain.repository.PokemonCatalogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultPokemonCatalogRepository(
    private val remoteDataSource: PokemonCatalogRemoteDataSource,
    private val localDataSource: PokemonCatalogLocalDataSource,
    private val cacheDataSource: PokemonCatalogCacheDataSource,
    private val loggerError: LoggerError,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokemonCatalogRepository {

    override suspend fun syncPokemonCatalog(): Result<Int> = withContext(dispatcher) {
        return@withContext try {
            val items = remoteDataSource.fetchFullCatalog().map { it.toEntity() }
            cacheDataSource.clearAndInsertAllCatalog(items)
            Result.success(items.size)
        } catch (e: Exception) {
            loggerError.logError("Error sync pokemon catalog", error = e)
            Result.failure(e)
        }
    }

    override suspend fun getLastSyncAt(): Long = localDataSource.getLastSyncAt()


    override suspend fun setLastSyncAt(lastSyncTime: Long) = localDataSource.setLastSyncAt(lastSyncTime)
}
