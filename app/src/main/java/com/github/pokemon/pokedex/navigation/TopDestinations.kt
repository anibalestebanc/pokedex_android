package com.github.pokemon.pokedex.navigation


enum class TopDestination {
    HOME,
    SEARCH_LIST,
    FAVORITES
}

val mapDestinationRoutes = mapOf(
    TopDestination.HOME to NavigationRoute.Home,
    TopDestination.SEARCH_LIST to NavigationRoute.SearchList(),
    TopDestination.FAVORITES to NavigationRoute.Favorite
)




