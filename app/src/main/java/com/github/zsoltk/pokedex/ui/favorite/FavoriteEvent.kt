package com.github.zsoltk.pokedex.ui.favorite


sealed interface FavoriteEvent {
    data object OnStart : FavoriteEvent
}
