package com.github.zsoltk.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.github.zsoltk.pokedex.domain.model.pokemons
import com.github.zsoltk.pokedex.ui.AppState
import com.github.zsoltk.pokedex.ui.favorite.FavoriteRoute
import com.github.zsoltk.pokedex.ui.favorite.navigation.FavoriteRoute
import com.github.zsoltk.pokedex.ui.fullsearch.SearchDialogRoute
import com.github.zsoltk.pokedex.ui.fullsearch.navigation.SearchDialogRoute
import com.github.zsoltk.pokedex.ui.fullsearch.navigation.navigateToSearchDialog
import com.github.zsoltk.pokedex.ui.home.HomeRoute
import com.github.zsoltk.pokedex.ui.home.navigation.HomeRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetailRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetailScreen
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.DetailRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.OldPokemonDetailRoute
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.navigateToOldPokemonDetail
import com.github.zsoltk.pokedex.ui.pokemondetail.navigation.navigateToPokemonDetail
import com.github.zsoltk.pokedex.ui.pokemonlist.PokemonListRoute
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.ListRoute
import com.github.zsoltk.pokedex.ui.pokemonlist.navigation.navigateToPokemonList
import com.github.zsoltk.pokedex.ui.searchresult.SearchResultRoute
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SEARCH_RESULT_KEY
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SearchResultRoute
import com.github.zsoltk.pokedex.ui.searchresult.navigation.navigateToSearchResult

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {

        composable<HomeRoute> {
            HomeRoute(
                onBackClick = navController::popBackStack,
                onPokemonListClick = navController::navigateToPokemonList,
                onSearchClick = {
                    navController.navigateToSearchDialog(navigateToSearch = true)
                },
            )
        }

        composable<ListRoute> {
            PokemonListRoute(
                onBackClick = navController::popBackStack,
                onPokemonDetailClick = { pokemonName ->
                    navController.navigateToOldPokemonDetail(pokemonName)
                }
            )
        }

        composable<OldPokemonDetailRoute> { backStackEntry ->
            val detailRoute: OldPokemonDetailRoute = backStackEntry.toRoute()
            val pokemon = pokemons.find { it.name == detailRoute.pokemonName }
                ?: throw IllegalArgumentException("Pokemon Name ${detailRoute.pokemonName} not found")
            PokemonDetailScreen(pokemon = pokemon)
        }

        composable<DetailRoute> { backStackEntry ->
            val detailRoute: DetailRoute = backStackEntry.toRoute()
            PokemonDetailRoute(
                onBackClick = navController::popBackStack,
                pokemonId = detailRoute.pokemonId,
            )
        }

        composable<SearchResultRoute>{ backStackEntry ->
            val searchRoute: SearchResultRoute = backStackEntry.toRoute()
            SearchResultRoute(
                appState = appState,
                initialQuery = searchRoute.query,
                onBackClick = navController::popBackStack,
                onDetailClick = { pokemonId ->
                    navController.navigateToPokemonDetail(pokemonId)
                },
                onSearchClick = { q ->
                    navController.navigateToSearchDialog(query = q)
                },
            )
        }

        dialog<SearchDialogRoute>(
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) { backStackEntry ->
            val searchRoute: SearchDialogRoute = backStackEntry.toRoute()
            SearchDialogRoute(
                navigateToResult = searchRoute.navigateToSearch,
                initialQuery = searchRoute.query,
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

        composable<FavoriteRoute> {
            FavoriteRoute(
                onBackClick = navController::popBackStack,
                onDetailClick = { pokemonId ->
                    navController.navigateToPokemonDetail(pokemonId)
                },
            )
        }
    }
}
