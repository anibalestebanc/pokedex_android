package com.github.zsoltk.pokedex.ui.home

import com.github.zsoltk.pokedex.navigation.Route

sealed class HomeEffect {
    data class NavigateTo(val route: Route) : HomeEffect()
}
