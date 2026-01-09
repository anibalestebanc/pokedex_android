package com.github.pokemon.pokedex.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.navigation.NavigationRoute
import com.github.pokemon.pokedex.ui.components.bottombar.BottomBarItem
import com.github.pokemon.pokedex.ui.components.bottombar.TopBottomBarDestinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    val backStack = rememberNavBackStack(NavigationRoute.Home)
    return remember(networkMonitor, coroutineScope) {
        AppState(coroutineScope, networkMonitor)
    }
}

@Stable
class AppState(
    val coroutineScope: CoroutineScope,
    val networkMonitor: NetworkMonitor,
){
    val topDestinations : List<BottomBarItem> = TopBottomBarDestinations



    @Suppress("MagicNumber")
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

/*    @Composable
    fun currentDestination(): NavDestination? {
        val backStackEntry by navController.currentBackStackEntryAsState()
        return backStackEntry?.destination
    }*/

/*    @Composable
    fun shouldShowBottomBar(): Boolean {
        val d = currentDestination() ?: return false
        val roots = topDestinations.map { it.qualifiedName }.toSet()
        return d.hierarchy.any { dest -> roots.any { root -> dest.route?.startsWith(root) == true } }
    }*/

/*    @Composable
    fun currentTopLevelDestinationId(): String? {
        val d = currentDestination() ?: return null
        return topDestinations.firstOrNull { t ->
            d.hierarchy.any { dest -> dest.route?.startsWith(t.qualifiedName) == true }
        }?.id
    }*/

    //Use this metho if want to show in other screen like search dialog, detail etc.
/*
    @Composable
    fun currentTopLevelDestinationIdV2(): String? {
        val d = currentDestination() ?: return null
        val isHome = d.hierarchy.any { it.route?.startsWith(Home::class.qualifiedName!!) == true }
        val isFav = d.hierarchy.any { it.route?.startsWith(Favorite::class.qualifiedName!!) == true }
        val isSearch = d.hierarchy.any { it.route?.startsWith(SearchList::class.qualifiedName!!) == true }
        return when {
            isHome -> "home"
            isFav -> "favorite"
            isSearch -> "search"
            else -> null
        }
    }
*/

/*    fun navigateToTopLevelDestination(destId: String) {
        val dest = topDestinations.first { it.id == destId }
        when (dest.qualifiedName) {
            Home::class.qualifiedName -> {
                navController.navigate(Home) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            SearchList::class.qualifiedName -> {
                navController.navigate(SearchList(query = "")) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            Favorite::class.qualifiedName -> {
                navController.navigate(Favorite) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }*/
}
