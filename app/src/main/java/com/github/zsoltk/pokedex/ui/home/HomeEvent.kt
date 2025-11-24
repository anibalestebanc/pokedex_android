package com.github.zsoltk.pokedex.ui.home

sealed interface HomeEvent {
    object OnStart : HomeEvent
    object OpenPokedex : HomeEvent
}
