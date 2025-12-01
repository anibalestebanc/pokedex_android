package com.github.pokemon.pokedex.data.datasource.cache

import androidx.paging.PagingData
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import kotlinx.coroutines.flow.Flow

interface SearchCacheDatasource {
    fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>>
}
