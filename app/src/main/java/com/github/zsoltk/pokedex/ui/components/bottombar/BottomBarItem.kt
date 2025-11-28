package com.github.zsoltk.pokedex.ui.components.bottombar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.ui.favorite.navigation.FavoriteRoute
import com.github.zsoltk.pokedex.ui.home.navigation.HomeRoute
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SearchResultRoute

data class BottomBarItem(
    val id: String,
    @StringRes val label: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val qualifiedName: String,
   // val route: KClass<*>,
)

val TopBottomBarDestinations = listOf(
    BottomBarItem(
        id = "home",
        label = R.string.bottom_bar_home,
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Outlined.Home,
        qualifiedName = HomeRoute::class.qualifiedName!!,
      //  route = HomeRoute::class,
    ),
    BottomBarItem(
        id = "search",
        label = R.string.bottom_bar_search,
        selectedIcon = Icons.Outlined.Search,
        unselectedIcon = Icons.Outlined.Search,
        qualifiedName = SearchResultRoute::class.qualifiedName!!,
    //    route = SearchResultRoute::class,
    ),
    BottomBarItem(
        id = "favorite",
        label = R.string.bottom_bar_favorite,
        selectedIcon = Icons.Outlined.FavoriteBorder,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        qualifiedName = FavoriteRoute::class.qualifiedName!!,
      //  route = FavoriteRoute::class,
    ),
)
