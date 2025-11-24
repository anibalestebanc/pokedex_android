package com.github.zsoltk.pokedex.ui.pokemondetail.navigation

import androidx.navigation.NavController

const val POKEMON_DETAIL = "pokemon_detail"
const val POKEMON_NAME_ARG = "pokemonName"

const val POKEMON_DETAIL_ROUTE = "$POKEMON_DETAIL/{$POKEMON_NAME_ARG}"

fun NavController.navigateToPokemonDetail(pokemonName: String) {
    navigate("$POKEMON_DETAIL/$pokemonName") {
        launchSingleTop = true
    }
}
