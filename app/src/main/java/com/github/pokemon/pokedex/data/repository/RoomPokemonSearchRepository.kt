package com.github.pokemon.pokedex.data.repository

import androidx.paging.PagingData
import com.github.pokemon.pokedex.data.datasource.cache.SearchCacheDatasource
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.repository.SearchPokemonRepository
import kotlinx.coroutines.flow.Flow

class RoomPokemonSearchRepository(
    private val searchCacheDatasource: SearchCacheDatasource,
) : SearchPokemonRepository {

    override fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>> =
        searchCacheDatasource.searchPaged(query)
}
