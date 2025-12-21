package com.github.pokemon.pokedex.ui.home.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.Screen.HomeScreen


fun NavController.navigateToHome(popUpToStart: Boolean = false) {
    navigate(route = HomeScreen) {
        launchSingleTop = true
        restoreState = true
        if (popUpToStart) {
            popUpTo(graph.startDestinationId) {
                inclusive = true
                saveState = true
            }
        }
    }
}
