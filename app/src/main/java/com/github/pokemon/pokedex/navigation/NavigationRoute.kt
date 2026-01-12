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
    data object SearchList : NavigationRoute()

    @Serializable
    data object Search : NavigationRoute()

    @Serializable
    data object Favorite : NavigationRoute()
}
