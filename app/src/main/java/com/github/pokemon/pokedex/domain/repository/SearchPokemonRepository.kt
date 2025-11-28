package com.github.pokemon.pokedex.domain.repository

import androidx.paging.PagingData
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import kotlinx.coroutines.flow.Flow

interface SearchPokemonRepository {
    fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>>
}
