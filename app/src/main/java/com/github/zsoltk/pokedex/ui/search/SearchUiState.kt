package com.github.zsoltk.pokedex.ui.search

data class SearchUiState(
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
