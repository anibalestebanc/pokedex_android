package com.github.zsoltk.pokedex.ui.fullsearch.navigation

import android.net.Uri
import androidx.navigation.NavController


const val SEARCH_DIALOG = "search_dialog"
const val SEARCH_DIALOG_QUERY_ARG = "q"

const val SEARCH_DIALOG_NAVIGATION_ARG = "navigate_to_result"
const val SEARCH_DIALOG_ROUTE = "$SEARCH_DIALOG/{$SEARCH_DIALOG_NAVIGATION_ARG}/{$SEARCH_DIALOG_QUERY_ARG}"

fun NavController.navigateToSearchDialog(
    navigateToResult: Boolean = false,
    query: String = "",
) {
    val encoded = Uri.encode(query)
    navigate("$SEARCH_DIALOG/$navigateToResult/$encoded") {
        launchSingleTop = true
    }
}
