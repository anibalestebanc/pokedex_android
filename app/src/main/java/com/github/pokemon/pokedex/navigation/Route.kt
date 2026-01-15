package com.github.pokemon.pokedex.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Route : NavKey {

    @Serializable
    data object Home : Route()

    @Serializable
    data class Detail(val id: String) : Route()

    @Serializable
    data object SearchList : Route()

    @Serializable
    data object Search : Route()

    @Serializable
    data object Favorite : Route()
}
