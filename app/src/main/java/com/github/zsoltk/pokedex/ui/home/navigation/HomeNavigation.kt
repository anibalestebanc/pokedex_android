package com.github.zsoltk.pokedex.ui.home.navigation

import androidx.navigation.NavController

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(popUpToStart: Boolean = false) {
    navigate(HOME_ROUTE) {
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
