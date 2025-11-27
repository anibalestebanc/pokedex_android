package com.github.zsoltk.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.github.zsoltk.pokedex.domain.model.pokemons
import com.github.zsoltk.pokedex.ui.AppState
import com.github.zsoltk.pokedex.ui.favorite.FavoriteRoute
import com.github.zsoltk.pokedex.ui.fullsearch.SearchDialogRoute
import com.github.zsoltk.pokedex.ui.fullsearch.navigation.SEARCH_DIALOG_NAVIGATION_ARG
import com.github.zsoltk.pokedex.ui.fullsearch.navigation.SEARCH_DIALOG_QUERY_ARG
import com.github.zsoltk.pokedex.ui.fullsearch.navigation.navigateToSearchDialog
import com.github.zsoltk.pokedex.ui.home.HomeRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetailRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetailScreen
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.POKEMON_ID_ARG
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.POKEMON_NAME_ARG
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.navigateToOldPokemonDetail
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.navigateToPokemonDetail
import com.github.zsoltk.pokedex.ui.pokemonlist.PokemonListRoute
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.navigateToPokemonList
import com.github.zsoltk.pokedex.ui.searchresult.SearchResultRoute
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SEARCH_QUERY_ARG
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SEARCH_RESULT_KEY
import com.github.zsoltk.pokedex.ui.searchresult.navigation.navigateToSearchResult

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
                onSearchClick = {
                    navController.navigateToSearchDialog(navigateToResult = true)
                }
            )
        }

        composable(Route.PokemonList.route) {
            PokemonListRoute(
                onBackClick = navController::popBackStack,
                onPokemonDetailClick = { pokemonName ->
                    navController.navigateToOldPokemonDetail(pokemonName)
                }
            )
        }

        composable(
            route = Route.OldPokemonDetail.route,
            arguments = listOf(navArgument(POKEMON_NAME_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val pokemonName: String = backStackEntry.arguments?.getString(POKEMON_NAME_ARG) ?: ""
            val pokemon = pokemons.find { it.name == pokemonName }
                ?: throw IllegalArgumentException("Pokemon Name $pokemonName not found")
            PokemonDetailScreen(pokemon = pokemon)
        }

        composable(
            route = Route.PokemonDetail.route,
            arguments = listOf(navArgument(POKEMON_ID_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val pokemonId: String = backStackEntry.arguments?.getString(POKEMON_ID_ARG) ?: ""
            PokemonDetailRoute(
                onBackClick = navController::popBackStack,
                pokemonId = pokemonId,
            )
        }

        composable(
            route = Route.SearchResult.route,
            arguments = listOf(navArgument(SEARCH_QUERY_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val initialQuery = backStackEntry.arguments?.getString(SEARCH_QUERY_ARG).orEmpty()
            SearchResultRoute(
                appState = appState,
                initialQuery = initialQuery,
                onBackClick = navController::popBackStack,
                onDetailClick = { pokemonId ->
                    navController.navigateToPokemonDetail(pokemonId)
                },
                onSearchClick = { q ->
                    navController.navigateToSearchDialog(query = q, navigateToResult = false)
                },
            )
        }

        dialog(
            route = Route.SearchDialog.route,
            arguments = listOf(
                navArgument(SEARCH_DIALOG_QUERY_ARG) { type = NavType.StringType },
                navArgument(SEARCH_DIALOG_NAVIGATION_ARG) { type = NavType.BoolType },
            ),
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) { backStackEntry ->
            val initialQuery = backStackEntry.arguments?.getString(SEARCH_DIALOG_QUERY_ARG).orEmpty()
            val navigateToResult = backStackEntry.arguments?.getBoolean(SEARCH_DIALOG_NAVIGATION_ARG) ?: false
            SearchDialogRoute(
                navigateToResult = navigateToResult,
                initialQuery = initialQuery,
                onBackClick = navController::popBackStack,
                onBackAndSaveStateHandle = { query ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(SEARCH_RESULT_KEY, query)
                    navController.popBackStack()
                },
                onSearchResult = { query ->
                    navController.navigateToSearchResult(query)
                },
            )
        }

        composable(Route.Favorite.route) {
            FavoriteRoute(
                onBackClick = navController::popBackStack,
            )
        }
    }
}
