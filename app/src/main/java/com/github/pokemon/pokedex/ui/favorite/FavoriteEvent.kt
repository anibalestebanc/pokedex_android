package com.github.pokemon.pokedex.ui.favorite


sealed interface FavoriteEvent {
    data object OnStart : FavoriteEvent
}
