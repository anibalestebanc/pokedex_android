package com.github.pokemon.pokedex.ui.searchresult

data class SearchResultUiState(
    val query: String = "",
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
