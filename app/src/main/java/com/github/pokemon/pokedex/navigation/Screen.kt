package com.github.pokemon.pokedex.navigation

import com.github.pokemon.pokedex.utils.emptyString
import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object HomeScreen : Screen

    @Serializable
    data class DetailScreen(
        val pokemonId: String,
    ) : Screen

    @Serializable
    data class SearchListScreen(
        val query: String = emptyString,
    ) : Screen

    @Serializable
    data class SearchScreen(
        val navigateToSearch: Boolean = false,
        val query: String = emptyString,
    ) : Screen

    @Serializable
    object FavoriteScreen : Screen
}
