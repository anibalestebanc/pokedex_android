package com.github.zsoltk.pokedex.ui.fullsearch

data class SearchFullUiState (
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
)
