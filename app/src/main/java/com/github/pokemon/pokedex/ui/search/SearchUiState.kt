package com.github.pokemon.pokedex.ui.search

data class SearchUiState (
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
)
