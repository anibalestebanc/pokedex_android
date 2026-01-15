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
class Navigator(val state: NavigationState) {
    fun navigate(key: NavKey) {
        if (key in state.backStacks.keys){
            state.topLevelRoute = key
        } else {
            state.backStacks[state.topLevelRoute]?.add(key)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute] ?: error("Stack for ${state.topLevelRoute} not found")
        val currentRoute = currentStack.last()

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startDestination
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
