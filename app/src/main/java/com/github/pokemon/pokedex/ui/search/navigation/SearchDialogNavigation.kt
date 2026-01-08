package com.github.pokemon.pokedex.ui.search.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.Screen.SearchScreen
import com.github.pokemon.pokedex.utils.emptyString


fun NavController.navigateToSearchDialog(navigateToSearch: Boolean = false, query: String = emptyString) {
    navigate(route = SearchScreen(navigateToSearch, query)) {
        launchSingleTop = true
    }
}
