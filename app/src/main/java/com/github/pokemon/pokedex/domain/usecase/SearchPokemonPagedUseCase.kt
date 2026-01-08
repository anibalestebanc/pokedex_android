package com.github.pokemon.pokedex.domain.usecase

import androidx.paging.PagingData
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.model.SearchQuery
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow

class SearchPokemonPagedUseCase(private val repository: CatalogRepository) {
    operator fun invoke(query: String?): Flow<PagingData<PokemonCatalog>> {
        val normalizedQuery = query?.trim()?.lowercase()?.takeIf { it.isNotEmpty() }

        val searchQuery = if (normalizedQuery != null)
            SearchQuery.ByName(normalizedQuery) else SearchQuery.All

        return repository.searchPokemonPaged(searchQuery)
    }
}
