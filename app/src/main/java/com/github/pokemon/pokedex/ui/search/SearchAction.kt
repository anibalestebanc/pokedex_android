package com.github.pokemon.pokedex.ui.search


sealed interface SearchAction {
    data object OnStart : SearchAction
    data class SetInitialQuery(val query: String) : SearchAction
    data class QueryChanged(val query: String) : SearchAction
    data class SelectSuggestion(val query: String) : SearchAction
    data object RemoveAllHistory : SearchAction
    data object SearchSubmit : SearchAction
}
