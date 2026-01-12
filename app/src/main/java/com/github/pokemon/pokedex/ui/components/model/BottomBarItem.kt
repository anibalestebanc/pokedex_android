package com.github.pokemon.pokedex.ui.components.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.navigation.NavigationRoute

data class BottomBarItem(
    val label: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: NavKey,
)

val Destinations = mapOf(
    "home" to BottomBarItem(
        label = R.string.bottom_bar_home,
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = NavigationRoute.Home,
    ),
    "search" to BottomBarItem(
        label = R.string.bottom_bar_search,
        selectedIcon = Icons.Outlined.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = NavigationRoute.SearchList,
    ),
    "favorite" to BottomBarItem(
        label = R.string.bottom_bar_favorite,
        selectedIcon = Icons.Outlined.FavoriteBorder,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        route = NavigationRoute.Favorite,
    ),
)
