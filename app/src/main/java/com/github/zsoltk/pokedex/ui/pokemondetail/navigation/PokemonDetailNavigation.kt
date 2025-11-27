package com.github.zsoltk.pokedex.ui.pokemondetail.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(val pokemonId: String)

fun NavController.navigateToPokemonDetail(pokemonId: String) {
    navigate(route = DetailRoute(pokemonId)) {
        launchSingleTop = true
    }
}
@Serializable
data class OldPokemonDetailRoute(val pokemonName: String)

fun NavController.navigateToOldPokemonDetail(pokemonName: String) {
    navigate(route = OldPokemonDetailRoute(pokemonName)) {
        launchSingleTop = true
    }
}
