package com.github.pokemon.pokedex.ui.home

sealed interface HomeEffect {
    data object NavigateToPokedex : HomeEffect
    data object NavigateToSearch : HomeEffect
}
