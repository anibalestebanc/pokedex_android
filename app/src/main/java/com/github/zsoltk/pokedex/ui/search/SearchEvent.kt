package com.github.zsoltk.pokedex.ui.search

sealed interface SearchEvent {
    object OnStart : SearchEvent
    data class SetInitialQuery(val text: String) : SearchEvent
    data class QueryChanged(val text: String) : SearchEvent
    object SearchSubmit : SearchEvent
    data class SelectSuggestion(val text: String) : SearchEvent
    data class RemoveSuggestion(val text: String) : SearchEvent
}
