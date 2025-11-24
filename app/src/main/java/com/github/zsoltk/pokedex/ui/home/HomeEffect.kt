package com.github.zsoltk.pokedex.ui.home

sealed class HomeEffect {
    object NavigateToPokemonList : HomeEffect()
    data class NavigateToSearch(val query: String) : HomeEffect()
}
