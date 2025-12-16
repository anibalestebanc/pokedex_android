package com.github.pokemon.pokedex.ui.favorite


sealed interface FavoriteAction {
    data object OnStart : FavoriteAction
}
