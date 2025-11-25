package com.github.zsoltk.pokedex.ui.search

data class SearchUiState(
    val query: String = "",
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
