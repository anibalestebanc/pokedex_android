package com.github.pokemon.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import kotlin.reflect.KClass

//TODO check why this code crash
/*fun navigationStateSaver(startDestination: NavKey): Saver<NavigationState, Any> = listSaver(
    save = { state -> state.backStack.toList() },
    restore = { saved ->
        NavigationState(startDestination).apply {
            backStack.clear()
            backStack.addAll(saved)
        }
    }
)*/

@Composable
fun rememberNavigationState(
    startDestination: NavKey = NavigationRoute.Home,
): NavigationState = remember(startDestination) { //rememberSaveable(saver = navigationStateSaver(startDestination)) {
    NavigationState(startDestination)
}

class NavigationState(val startDestination: NavKey) {

    private val topLevelDestinations: Set<KClass<out NavigationRoute>> = setOf(
        NavigationRoute.Home::class,
        NavigationRoute.SearchList::class,
        NavigationRoute.Favorite::class
    )
    val backStack = mutableStateListOf(startDestination)

    val currentKey: NavKey get() = backStack.last()

    val currentTopLevel: NavKey get() = backStack.lastOrNull { isTopLevel(it) } ?: startDestination

    fun isTopLevel(key: NavKey): Boolean {
        return key::class in topLevelDestinations
    }

}
