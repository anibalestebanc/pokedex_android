package com.github.zsoltk.pokedex.ui.favorite

import androidx.annotation.StringRes
import com.github.zsoltk.pokedex.domain.model.PokemonDetail

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favorites: List<PokemonDetail> = emptyList(),
    @StringRes val error: Int? = null
)
