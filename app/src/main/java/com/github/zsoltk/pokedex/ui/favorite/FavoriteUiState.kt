package com.github.zsoltk.pokedex.ui.favorite

import com.github.zsoltk.pokedex.domain.model.PokemonDetail

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favorites: List<PokemonDetail> = emptyList(),
    val error: String? = null
)
