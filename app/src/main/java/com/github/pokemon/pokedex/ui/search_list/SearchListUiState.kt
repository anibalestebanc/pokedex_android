package com.github.pokemon.pokedex.ui.search_list

import com.github.pokemon.pokedex.domain.model.PokemonDetail

data class SearchListUiState(
    val isLoading: Boolean = false,
    val detail: PokemonDetail? = null,
    val error: String? = null,
)
