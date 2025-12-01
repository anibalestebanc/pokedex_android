package com.github.pokemon.pokedex.data.datasource.cache

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.pokemon.pokedex.core.database.dao.PokemonCatalogDao
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomSearchCacheDatasource(private val pokemonCatalogDao: PokemonCatalogDao) : SearchCacheDatasource {

    override fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>> = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            prefetchDistance = DEFAULT_PREFETCH_DISTANCE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            if (query.isNullOrBlank()) {
                pokemonCatalogDao.pagingSourceAll()
            } else {
                val processedQuery = query.lowercase()
                pokemonCatalogDao.pagingSourceByQuery(processedQuery)
            }
        },
    ).flow.map { pagingData ->
        pagingData.map { entity ->
            entity.toDomain()
        }
    }

    private companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_PREFETCH_DISTANCE = 10
    }
}
