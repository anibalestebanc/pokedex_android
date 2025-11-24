package com.github.zsoltk.pokedex.ui.home

sealed interface HomeEvent {
    object OpenPokedex : HomeEvent
}
