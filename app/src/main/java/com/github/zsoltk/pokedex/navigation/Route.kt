package com.github.zsoltk.pokedex.navigation

sealed class Route(val route: String) {

    data object Splash : Route("splash")
    data object Home : Route("home")
    data object PokemonList : Route("pokemon_list")
    data object PokemonDetails : Route("pokemon_detail/{${Args.PokemonName.key}}") {
        fun create(pokemonName: String) = "pokemon_detail/$pokemonName"
    }
}

sealed class Args(val key: String) {
    data object PokemonName : Args("pokemonName")
}
