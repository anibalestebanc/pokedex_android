package com.github.pokemon.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Stable
class NavigationActions(
    val onBack: () -> Unit,
    val onTopLevelSelected: (TopDestination) -> Unit,
)

fun NavBackStack<NavKey>.goToTopDestination(destination: TopDestination) {
    val root : NavKey = NavigationRoute.Home
    val target = mapDestinationRoutes.getOrDefault(destination, root)

    clear()
    add(root)

    if (target != root) {
        add(target)
    }
}

@Composable
fun rememberNavigationActions(
    backStack: NavBackStack<NavKey>
): NavigationActions = remember(backStack) {
    NavigationActions(
        onBack = {
             backStack.removeLastOrNull()
        },
        onTopLevelSelected = { destination ->
            backStack.goToTopDestination(destination)
        },
    )
}
