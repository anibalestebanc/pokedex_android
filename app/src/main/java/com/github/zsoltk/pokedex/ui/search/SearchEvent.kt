package com.github.zsoltk.pokedex.ui.search

sealed interface SearchEvent {
    data class SetInitialQuery(val text: String): SearchEvent
    data class QueryChanged(val text: String): SearchEvent
    data object SearchSubmit: SearchEvent
}
