package com.github.pokemon.pokedex.ui.search.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.ScreenRoute.SearchScreenRoute
import com.github.pokemon.pokedex.utils.emptyString


fun NavController.navigateToSearchDialog(navigateToSearch: Boolean = false, query: String = emptyString()) {
    navigate(route = SearchScreenRoute(navigateToSearch, query)) {
        launchSingleTop = true
    }
}
