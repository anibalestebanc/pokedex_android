package com.github.pokemon.pokedex.ui.searchlist.navigation

import androidx.navigation.NavController
import com.github.pokemon.pokedex.navigation.Screen.SearchListScreen

const val SEARCH_LIST_KEY = "search_query_list"

fun NavController.navigateToSearchList(query: String = "") {
    navigate(SearchListScreen(query)) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
