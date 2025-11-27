package com.github.zsoltk.pokedex.ui.searchresult.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

const val SEARCH_RESULT_KEY = "search_query_result"

@Serializable
data class SearchResultRoute(val query: String = "")

fun NavController.navigateToSearchResult(query: String = "") {
    navigate(SearchResultRoute(query)) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
