package com.github.pokemon.pokedex.ui.search.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class SearchDialogRoute(val navigateToSearch: Boolean = false, val query: String = "")

fun NavController.navigateToSearchDialog(navigateToSearch: Boolean = false, query: String = "") {
    navigate(route = SearchDialogRoute(navigateToSearch, query)) {
        launchSingleTop = true
    }
}
