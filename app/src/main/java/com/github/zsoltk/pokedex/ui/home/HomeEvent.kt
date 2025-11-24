package com.github.zsoltk.pokedex.ui.home

sealed interface HomeEvent {
    object OnStart : HomeEvent
    object OpenPokedex : HomeEvent

    data object SearchSubmitted : HomeEvent

    data class SearchQueryChanged(val text: String) : HomeEvent
}
