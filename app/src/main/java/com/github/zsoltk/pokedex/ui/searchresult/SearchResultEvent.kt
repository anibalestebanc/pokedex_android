package com.github.zsoltk.pokedex.ui.searchresult

sealed interface SearchResultEvent {
    object OnStart : SearchResultEvent
    data class SetInitialQuery(val text: String) : SearchResultEvent
    data class QueryChanged(val text: String) : SearchResultEvent
    object SearchSubmit : SearchResultEvent
    data class SelectSuggestion(val text: String) : SearchResultEvent
    data class RemoveSuggestion(val text: String) : SearchResultEvent
}
