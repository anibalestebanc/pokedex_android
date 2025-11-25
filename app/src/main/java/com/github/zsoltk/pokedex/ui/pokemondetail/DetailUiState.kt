package com.github.zsoltk.pokedex.ui.pokemondetail

import com.github.zsoltk.pokedex.domain.model.PokemonFullDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val data: PokemonFullDetail? = null,
    val error: String? = null
)
