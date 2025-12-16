package com.github.pokemon.pokedex.ui.search_list

data class SearchResultUiState(
    val query: String = "",
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
