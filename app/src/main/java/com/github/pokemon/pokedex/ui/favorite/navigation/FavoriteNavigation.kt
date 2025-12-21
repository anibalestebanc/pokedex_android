package com.github.pokemon.pokedex.ui.favorite.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.Screen


fun NavController.navigateToFavorite() {
    navigate(route = Screen.FavoriteScreen) {
        launchSingleTop = true
        restoreState = true
    }
}
