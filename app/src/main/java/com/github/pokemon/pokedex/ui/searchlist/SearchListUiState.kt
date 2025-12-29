package com.github.pokemon.pokedex.ui.searchlist

import com.github.pokemon.pokedex.utils.emptyString

data class SearchListUiState(
    val query: String = emptyString(),
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
