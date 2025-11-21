package com.github.zsoltk.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.zsoltk.pokedex.domain.model.pokemons
import com.github.zsoltk.pokedex.ui.home.HomeScreen
import com.github.zsoltk.pokedex.ui.home.MenuItem
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetails
import com.github.zsoltk.pokedex.ui.pokemonlist.PokemonListScreen

@Composable
fun AppNavHost(appState: AppState) {

    val navController = appState.navController

    NavHost(navController, startDestination = Route.Home.route) {

        composable(Route.Home.route) {
            HomeScreen(
                onMenuItemSelected = { menuItem ->
                    when (menuItem) {
                        MenuItem.Pokedex -> navController.navigate(Route.PokemonList.route)
                        else -> {}
                    }
                },
            )
        }
        composable(Route.PokemonList.route) {
            PokemonListScreen(
                onPokemonSelected = { pokemon ->
                    val pokemonName: String =
                        pokemon.name ?: throw IllegalArgumentException("Pokemon name cannot be null")
                    navController.navigate(Route.PokemonDetails.create(pokemonName))
                },
            )
        }
        composable(
            route = Route.PokemonDetails.route,
            arguments = listOf(navArgument(Args.PokemonName.key) { type = NavType.StringType }),
        ) { backStackEntry ->
            val pokemonName: String = backStackEntry.arguments?.getString(Args.PokemonName.key)
                ?: throw IllegalArgumentException("Pokemon name cannot be null")

            val pokemon = pokemons.find { it.name == pokemonName }
                ?: throw IllegalArgumentException("Pokemon Name $pokemonName not found")
            PokemonDetails(pokemon)
        }
    }
}
