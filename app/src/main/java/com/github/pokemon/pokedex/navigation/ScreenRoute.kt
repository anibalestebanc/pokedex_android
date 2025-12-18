package com.github.pokemon.pokedex.navigation

import com.github.pokemon.pokedex.utils.emptyString
import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    @Serializable
    data object HomeScreenRoute : ScreenRoute

    @Serializable
    data class DetailScreenRoute(
        val pokemonId: String,
    ) : ScreenRoute

    @Serializable
    data class SearchListScreenRoute(
        val query: String = emptyString(),
    ) : ScreenRoute

    @Serializable
    data class SearchScreenRoute(
        val navigateToSearch: Boolean = false,
        val query: String = emptyString(),
    ) : ScreenRoute

    @Serializable
    object FavoriteScreenRoute : ScreenRoute
}
