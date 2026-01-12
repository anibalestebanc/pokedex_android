package com.github.pokemon.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey

@Composable
fun rememberNavigator(
    state: NavigationState
): Navigator = remember(state) {
    Navigator(state)
}
class Navigator(private val state: NavigationState) {
    fun navigate(key: NavKey) = when {
        state.isTopLevel(key) -> navigateToTopLevel(key)
        else -> navigateTo(key)
    }

    fun goBack() {
        if (state.backStack.size > 1) {
            state.backStack.removeLastOrNull()
        }
    }

    private fun navigateToTopLevel(key: NavKey) {
        state.backStack.clear()
        state.backStack.add(state.startDestination)

        if (key != state.startDestination) {
            state.backStack.add(key)
        }
    }

    private fun navigateTo(key: NavKey) {
        if (state.currentKey != key) {
            state.backStack.add(key)
        }
    }
}
