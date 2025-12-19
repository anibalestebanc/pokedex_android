package com.github.pokemon.pokedex.ui.searchlist

data class SearchListUiState(
    val query: String = "",
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
