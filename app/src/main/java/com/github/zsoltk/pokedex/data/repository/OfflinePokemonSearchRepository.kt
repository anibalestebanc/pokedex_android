package com.github.zsoltk.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.zsoltk.pokedex.core.database.PokemonDatabase
import com.github.zsoltk.pokedex.data.mapper.toDomain
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.domain.repository.PokemonSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflinePokemonSearchRepository(
    private val database: PokemonDatabase
) : PokemonSearchRepository {

    override fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                val dao = database.pokemonCatalogDao()
                if (query.isNullOrBlank()) dao.pagingSourceAll()
                else dao.pagingSourceByQuery(query.lowercase())
            }
        ).flow.map { it.map { entity -> entity.toDomain() } }
}
