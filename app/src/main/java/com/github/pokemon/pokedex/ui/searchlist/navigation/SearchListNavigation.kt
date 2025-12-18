package com.github.pokemon.pokedex.ui.searchlist.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

const val SEARCH_LIST_KEY = "search_query_list"

@Serializable
data class SearchRoute(val query: String = "")

fun NavController.navigateToSearchList(query: String = "") {
    navigate(SearchRoute(query)) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
