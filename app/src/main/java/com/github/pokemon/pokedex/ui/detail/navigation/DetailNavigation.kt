package com.github.pokemon.pokedex.ui.detail.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(val pokemonId: String)

fun NavController.navigateToDetail(pokemonId: String) {
    navigate(route = DetailRoute(pokemonId)) {
        launchSingleTop = true
    }
}
