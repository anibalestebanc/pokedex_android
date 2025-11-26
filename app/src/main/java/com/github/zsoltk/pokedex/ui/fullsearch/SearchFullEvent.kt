package com.github.zsoltk.pokedex.ui.fullsearch


sealed interface SearchFullEvent {
    object OnStart : SearchFullEvent
    data class SetInitialQuery(val query: String) : SearchFullEvent
    data class QueryChanged(val query: String) : SearchFullEvent
    data class SelectSuggestion(val query: String) : SearchFullEvent
    object RemoveAllHistory : SearchFullEvent
    object SearchSubmit : SearchFullEvent
}
