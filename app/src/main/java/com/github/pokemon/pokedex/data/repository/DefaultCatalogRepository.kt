package com.github.pokemon.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.pokemon.pokedex.data.datasource.cache.CatalogCacheDataSource
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.data.datasource.remote.CatalogRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.model.SearchQuery
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultCatalogRepository(
    private val remoteDataSource: CatalogRemoteDataSource,
    private val cacheDataSource: CatalogCacheDataSource,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CatalogRepository {

    override suspend fun syncCatalog(): Result<Int> =
        withContext(coroutineDispatcher) {
            runCatching {
                val catalogListDto = remoteDataSource.fetchFullCatalog()
                val catalog = catalogListDto.map { it.toEntity() }
                cacheDataSource.clearAndInsertAllCatalog(catalog)
                catalog.size
            }
        }

    override fun searchPokemonPaged(searchQuery: SearchQuery): Flow<PagingData<PokemonCatalog>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PREFETCH_DISTANCE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                when (searchQuery) {
                    SearchQuery.All -> cacheDataSource.searchAllPaged()
                    is SearchQuery.ByName -> cacheDataSource.searchByNamePaged(searchQuery.query)
                }
            },
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomain() }
        }.flowOn(coroutineDispatcher)
    }

    private companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_PREFETCH_DISTANCE = 10
    }

}
