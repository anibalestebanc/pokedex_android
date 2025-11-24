package com.github.zsoltk.pokedex.navigation

import com.github.zsoltk.pokedex.ui.home.navigation.HOME_ROUTE
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.POKEMON_DETAIL_ROUTE
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.POKEMON_LIST_ROUTE

sealed class Route(val route: String) {

    data object Splash : Route("splash")
    data object Home : Route(HOME_ROUTE)
    data object PokemonList : Route(POKEMON_LIST_ROUTE)
    data object PokemonDetail : Route(POKEMON_DETAIL_ROUTE)
}
