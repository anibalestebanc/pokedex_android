package com.github.pokemon.pokedex.navigation

import androidx.navigation3.runtime.NavKey
import com.github.pokemon.pokedex.utils.emptyString
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute : NavKey {

    @Serializable
    data object Home : NavigationRoute()

    @Serializable
    data class Detail(val id: String) : NavigationRoute()

    @Serializable
    data class SearchList(val query: String = emptyString) : NavigationRoute()

    @Serializable
    data class Search(val query: String = emptyString) : NavigationRoute()

    @Serializable
    data object Favorite : NavigationRoute()
}
