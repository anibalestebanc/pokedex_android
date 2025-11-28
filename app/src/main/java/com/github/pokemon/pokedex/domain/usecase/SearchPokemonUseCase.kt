package com.github.pokemon.pokedex.domain.usecase

import androidx.paging.PagingData
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.repository.SearchPokemonRepository
import kotlinx.coroutines.flow.Flow

class SearchPokemonUseCase(private val repository: SearchPokemonRepository) {
    operator fun invoke(query: String?): Flow<PagingData<PokemonCatalog>> =
        repository.searchPaged(query)
}
