package com.github.pokemon.pokedex.ui.favorite.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object FavoriteRoute

fun NavController.navigateToFavorite() {
    navigate(route =  FavoriteRoute) {
        launchSingleTop = true
        restoreState = true
    }
}
