package com.github.pokemon.pokedex.ui.searchresult

sealed interface SearchResultEvent {
    data class SetInitialQuery(val text: String) : SearchResultEvent
    data class QueryChanged(val text: String) : SearchResultEvent
    object SubmitSearch : SearchResultEvent
}
