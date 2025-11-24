package com.github.zsoltk.pokedex.ui.pokemonlist.navigation

import androidx.navigation.NavController

const val POKEMON_LIST_ROUTE = "pokemon_list"

fun NavController.navigateToPokemonList() {
    navigate(POKEMON_LIST_ROUTE) {
        launchSingleTop = true
        restoreState = true
    }
}
