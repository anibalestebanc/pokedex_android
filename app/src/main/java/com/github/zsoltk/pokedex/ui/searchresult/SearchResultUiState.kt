package com.github.zsoltk.pokedex.ui.searchresult

data class SearchResultUiState(
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
    val isLoadingIndex: Boolean = false,
    val error: String? = null
)
