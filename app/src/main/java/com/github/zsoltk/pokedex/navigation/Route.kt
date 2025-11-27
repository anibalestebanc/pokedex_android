package com.github.zsoltk.pokedex.navigation

import com.github.zsoltk.pokedex.ui.favorite.navigation.POKEMON_FAVORITE_ROUTE
import com.github.zsoltk.pokedex.ui.fullsearch.navigation.SEARCH_DIALOG_ROUTE
import com.github.zsoltk.pokedex.ui.home.navigation.HOME_ROUTE
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.OLD_POKEMON_DETAIL_ROUTE
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.POKEMON_DETAIL_ROUTE
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.POKEMON_LIST_ROUTE
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SEARCH_RESULT_ROUTE

sealed class Route(val route: String) {

    data object Splash : Route("splash")
    data object Home : Route(HOME_ROUTE)
    data object PokemonList : Route(POKEMON_LIST_ROUTE)
    data object OldPokemonDetail : Route(OLD_POKEMON_DETAIL_ROUTE)
    data object PokemonDetail : Route(POKEMON_DETAIL_ROUTE)
    data object SearchResult : Route(SEARCH_RESULT_ROUTE)
    data object SearchDialog : Route(SEARCH_DIALOG_ROUTE)

    data object Favorite : Route(POKEMON_FAVORITE_ROUTE)
}
