package com.github.pokemon.pokedex.ui.components.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.navigation.Route

data class BottomBarItem(
    val title: Int,
    val icon: ImageVector,
)

val TOP_LEVEL_DESTINATION: Map<NavKey, BottomBarItem> = mapOf(
    Route.Home to BottomBarItem(
        title = R.string.bottom_bar_home,
        icon = Icons.Outlined.Home,
    ),
    Route.SearchList to BottomBarItem(
        title = R.string.bottom_bar_search,
        icon = Icons.Outlined.Search,
    ),
    Route.Favorite to BottomBarItem(
        title = R.string.bottom_bar_favorite,
        icon = Icons.Outlined.FavoriteBorder,
    ),
)
