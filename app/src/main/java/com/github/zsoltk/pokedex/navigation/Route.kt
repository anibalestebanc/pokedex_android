package com.github.zsoltk.pokedex.navigation

import com.github.zsoltk.pokedex.ui.home.navigation.HOME_ROUTE
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.OLD_POKEMON_DETAIL_ROUTE
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.POKEMON_DETAIL_ROUTE
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.POKEMON_LIST_ROUTE
import com.github.zsoltk.pokedex.ui.search.navigation.SEARCH_ROUTE

sealed class Route(val route: String) {

    data object Splash : Route("splash")
    data object Home : Route(HOME_ROUTE)
    data object PokemonList : Route(POKEMON_LIST_ROUTE)
    data object OldPokemonDetail : Route(OLD_POKEMON_DETAIL_ROUTE)
    data object PokemonDetail : Route(POKEMON_DETAIL_ROUTE)
    data object Search : Route(SEARCH_ROUTE)
}
