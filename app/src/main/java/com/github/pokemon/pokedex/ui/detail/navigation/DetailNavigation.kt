package com.github.pokemon.pokedex.ui.detail.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.ScreenRoute.DetailScreenRoute

fun NavController.navigateToDetail(pokemonId: String) {
    navigate(route = DetailScreenRoute(pokemonId)) {
        launchSingleTop = true
    }
}
