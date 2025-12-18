package com.github.pokemon.pokedex.ui.searchlist.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.ScreenRoute.SearchListScreenRoute

const val SEARCH_LIST_KEY = "search_query_list"

fun NavController.navigateToSearchList(query: String = "") {
    navigate(SearchListScreenRoute(query)) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
