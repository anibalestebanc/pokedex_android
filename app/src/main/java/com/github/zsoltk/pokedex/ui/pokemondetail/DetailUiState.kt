package com.github.zsoltk.pokedex.ui.pokemondetail

import androidx.annotation.StringRes
import com.github.zsoltk.pokedex.domain.model.PokemonFullDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val data: PokemonFullDetail? = null,
    @StringRes val error: Int? = null
)
