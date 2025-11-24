package com.github.zsoltk.pokedex.ui.search.navigation

import android.net.Uri
import androidx.navigation.NavController

const val SEARCH = "search"
const val SEARCH_QUERY_ARG = "q"
const val SEARCH_ROUTE = "$SEARCH/{$SEARCH_QUERY_ARG}"

fun NavController.navigateToSearch(initialQuery: String) {
    val encoded = Uri.encode(initialQuery)
    navigate("$SEARCH/$encoded") {
        launchSingleTop = true
    }
}
