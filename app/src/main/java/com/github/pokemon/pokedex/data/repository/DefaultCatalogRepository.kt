package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.cache.PokemonCatalogCacheDataSource
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.data.datasource.remote.PokemonCatalogRemoteDataSource
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultCatalogRepository(
    private val remoteDataSource: PokemonCatalogRemoteDataSource,
    private val cacheDataSource: PokemonCatalogCacheDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CatalogRepository {

    override suspend fun syncCatalog(): Result<Int> = withContext(ioDispatcher) {
        return@withContext try {
            val items = remoteDataSource.fetchFullCatalog().map { it.toEntity() }
            cacheDataSource.clearAndInsertAllCatalog(items)
            Result.success(items.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
