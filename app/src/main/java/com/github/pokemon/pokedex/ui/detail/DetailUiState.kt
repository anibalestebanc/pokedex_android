package com.github.pokemon.pokedex.ui.detail

import com.github.pokemon.pokedex.domain.model.PokemonFullDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val data: PokemonFullDetail? = null,
    val errorMessage: String? = null
)
