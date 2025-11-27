package com.github.zsoltk.pokedex.ui.favorite.navigation

import androidx.navigation.NavController

const val POKEMON_FAVORITE_ROUTE = "pokemon_favorite"

fun NavController.navigateToFavorite() {
    navigate(POKEMON_FAVORITE_ROUTE) {
        launchSingleTop = true
        restoreState = true
    }
}
