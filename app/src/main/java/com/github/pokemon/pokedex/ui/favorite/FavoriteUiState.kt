package com.github.pokemon.pokedex.ui.favorite

import com.github.pokemon.pokedex.domain.model.PokemonDetail

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favorites: List<PokemonDetail> = emptyList(),
    val errorMessage: String? = null
)
