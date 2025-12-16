package com.github.pokemon.pokedex.ui.detail

import androidx.annotation.StringRes
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val data: PokemonFullDetail? = null,
    @StringRes val error: Int? = null
)
