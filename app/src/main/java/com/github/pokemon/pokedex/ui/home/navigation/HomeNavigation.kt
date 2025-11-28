package com.github.pokemon.pokedex.ui.home.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavController.navigateToHome(popUpToStart: Boolean = false) {
    navigate(route = HomeRoute) {
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
