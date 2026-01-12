package com.github.pokemon.pokedex.ui.search


sealed interface SearchAction {
    data object OnStart : SearchAction
    data class SelectSuggestion(val query: String) : SearchAction
    data object RemoveAllHistory : SearchAction
    data class SearchSubmit(val query: String) : SearchAction
}
