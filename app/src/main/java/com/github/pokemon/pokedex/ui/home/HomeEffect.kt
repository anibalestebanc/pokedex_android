package com.github.pokemon.pokedex.ui.home

sealed class HomeEffect {
    object NavigateToPokemonList : HomeEffect()
    data class NavigateToSearch(val query: String) : HomeEffect()
}
