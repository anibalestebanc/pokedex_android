package com.github.pokemon.pokedex.ui.components.bottombar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.favorite.navigation.FavoriteRoute
import com.github.pokemon.pokedex.ui.home.navigation.HomeRoute
import com.github.pokemon.pokedex.ui.search_list.navigation.SearchRoute

data class BottomBarItem(
    val id: String,
    @StringRes val label: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val qualifiedName: String,
)

val TopBottomBarDestinations = listOf(
    BottomBarItem(
        id = "home",
        label = R.string.bottom_bar_home,
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Outlined.Home,
        qualifiedName = HomeRoute::class.qualifiedName!!,
    ),
    BottomBarItem(
        id = "search",
        label = R.string.bottom_bar_search,
        selectedIcon = Icons.Outlined.Search,
        unselectedIcon = Icons.Outlined.Search,
        qualifiedName = SearchRoute::class.qualifiedName!!,
    ),
    BottomBarItem(
        id = "favorite",
        label = R.string.bottom_bar_favorite,
        selectedIcon = Icons.Outlined.FavoriteBorder,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        qualifiedName = FavoriteRoute::class.qualifiedName!!,
    ),
)
