package com.github.pokemon.pokedex.ui.favorite.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.ScreenRoute


fun NavController.navigateToFavorite() {
    navigate(route = ScreenRoute.FavoriteScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}
