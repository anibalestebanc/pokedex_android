package com.github.zsoltk.pokedex.ui.search

import com.github.zsoltk.pokedex.domain.model.PokemonDetail

data class PokemonDetailUiState(
    val isLoading: Boolean = false,
    val detail: PokemonDetail? = null,
    val error: String? = null,
)
