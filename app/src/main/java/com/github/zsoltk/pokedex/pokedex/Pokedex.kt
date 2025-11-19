package com.github.zsoltk.pokedex.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.zsoltk.pokedex.entity.Pokemon

interface Pokedex {

    sealed class Routing(val route: String) {
        object PokemonList : Routing("pokemon_list")
        object PokemonDetails : Routing("pokemon_details")
    }

    companion object {
        @Composable
        fun Content() {
            val navController = rememberNavController()
            NavHost(navController, startDestination = Routing.PokemonList.route) {
                composable(Routing.PokemonList.route) {
                    PokemonList.Content(
                        onPokemonSelected = { navController.navigate(Routing.PokemonDetails.route) }
                    )
                }

                composable(Routing.PokemonDetails.route) {
                    // A placeholder for PokemonDetails. We'll need to figure out how to pass the Pokemon object.
                    // For now, let's just navigate to a composable.
                }
            }
        }
    }
}
