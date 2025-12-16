package com.github.pokemon.pokedex.ui.home

sealed interface HomeAction {
    data object OpenPokedex : HomeAction
    data object OnSearchClick : HomeAction
}
