package com.github.zsoltk.pokedex.ui.pokemonlist.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

fun NavController.navigateToPokemonList() {
    navigate(route = ListRoute) {
        launchSingleTop = true
        restoreState = true
    }
}
