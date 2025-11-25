package com.github.zsoltk.pokedex.domain.usecase

import androidx.paging.PagingData
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.domain.repository.SearchPokemonRepository
import kotlinx.coroutines.flow.Flow

class SearchPokemonUseCase(private val repository: SearchPokemonRepository) {
    operator fun invoke(query: String?): Flow<PagingData<PokemonCatalog>> =
        repository.searchPaged(query)
}
