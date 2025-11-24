package com.github.zsoltk.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.zsoltk.pokedex.domain.model.pokemons
import com.github.zsoltk.pokedex.ui.home.HomeRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetailRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.POKEMON_NAME_ARG
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.navigateToPokemonDetail
import com.github.zsoltk.pokedex.ui.pokemonlist.PokemonListRoute
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.navigateToPokemonList

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {

    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = modifier,
    ) {

        composable(Route.Home.route) {
            HomeRoute(
                onBackClick = navController::popBackStack,
                onPokemonListClick = navController::navigateToPokemonList,
            )
        }

        composable(Route.PokemonList.route) {
            PokemonListRoute(
                onBackClick = navController::popBackStack,
                onPokemonDetailClick = { pokemonName ->
                    navController.navigateToPokemonDetail(pokemonName)
                }
            )
        }

        composable(
            route = Route.PokemonDetail.route,
            arguments = listOf(navArgument(POKEMON_NAME_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName: String = backStackEntry.arguments?.getString(POKEMON_NAME_ARG) ?: ""
            //TODO Get pokemon by Local
            val pokemon = pokemons.find { it.name == pokemonName }
                ?: throw IllegalArgumentException("Pokemon Name $pokemonName not found")

            PokemonDetailRoute(
                onBackClick = navController::popBackStack,
                pokemon = pokemon,
            )
        }
    }
}
