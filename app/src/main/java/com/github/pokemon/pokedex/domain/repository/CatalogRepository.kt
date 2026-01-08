package com.github.pokemon.pokedex.domain.repository

import androidx.paging.PagingData
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.model.SearchQuery
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    suspend fun syncCatalog(): Result<Int>

    fun searchPokemonPaged(searchQuery: SearchQuery): Flow<PagingData<PokemonCatalog>>
}
