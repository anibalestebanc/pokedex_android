package com.github.pokemon.pokedex.ui.detail.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.Screen.DetailScreen

fun NavController.navigateToDetail(pokemonId: String) {
    navigate(route = DetailScreen(pokemonId)) {
        launchSingleTop = true
    }
}
