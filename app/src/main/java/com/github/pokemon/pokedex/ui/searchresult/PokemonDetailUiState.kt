package com.github.pokemon.pokedex.ui.searchresult

import com.github.pokemon.pokedex.domain.model.PokemonDetail

data class PokemonDetailUiState(
    val isLoading: Boolean = false,
    val detail: PokemonDetail? = null,
    val error: String? = null,
)
