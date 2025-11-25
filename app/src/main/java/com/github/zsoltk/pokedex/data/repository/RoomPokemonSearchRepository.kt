package com.github.zsoltk.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.zsoltk.pokedex.core.database.PokemonDatabase
import com.github.zsoltk.pokedex.data.mapper.toDomain
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.domain.repository.SearchPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomPokemonSearchRepository(
    private val database: PokemonDatabase,
) : SearchPokemonRepository {

    override fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PREFETCH_DISTANCE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                val dao = database.pokemonCatalogDao()
                if (query.isNullOrBlank()) {
                    dao.pagingSourceAll()
                } else {
                    val processedQuery = query.lowercase()
                    dao.pagingSourceByQuery(processedQuery)
                }
            },
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }
    }

    private companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_PREFETCH_DISTANCE = 10
    }
}
