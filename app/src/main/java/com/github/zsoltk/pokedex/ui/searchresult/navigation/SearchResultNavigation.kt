package com.github.zsoltk.pokedex.ui.searchresult.navigation

import android.net.Uri
import androidx.navigation.NavController

const val SEARCH_RESULT = "search_result"
const val SEARCH_QUERY_ARG = "q"
const val SEARCH_RESULT_ROUTE = "$SEARCH_RESULT/{$SEARCH_QUERY_ARG}"

fun NavController.navigateToSearchResult(initialQuery: String) {
    val encoded = Uri.encode(initialQuery)
    navigate("$SEARCH_RESULT/$encoded") {
        launchSingleTop = true
    }
}
