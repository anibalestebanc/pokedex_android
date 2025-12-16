package com.github.pokemon.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.github.pokemon.pokedex.ui.AppState
import com.github.pokemon.pokedex.ui.favorite.FavoriteRoute
import com.github.pokemon.pokedex.ui.favorite.navigation.FavoriteRoute
import com.github.pokemon.pokedex.ui.search.SearchDialogRoute
import com.github.pokemon.pokedex.ui.search.navigation.SearchDialogRoute
import com.github.pokemon.pokedex.ui.search.navigation.navigateToSearchDialog
import com.github.pokemon.pokedex.ui.home.HomeRoute
import com.github.pokemon.pokedex.ui.home.navigation.HomeRoute
import com.github.pokemon.pokedex.ui.detail.DetailRoute
import com.github.pokemon.pokedex.ui.detail.navigation.DetailRoute
import com.github.pokemon.pokedex.ui.detail.navigation.navigateToDetail
import com.github.pokemon.pokedex.ui.searchresult.SearchResultRoute
import com.github.pokemon.pokedex.ui.searchresult.navigation.SEARCH_RESULT_KEY
import com.github.pokemon.pokedex.ui.searchresult.navigation.SearchResultRoute
import com.github.pokemon.pokedex.ui.searchresult.navigation.navigateToSearchResult

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
                onSearchClick = {
                    navController.navigateToSearchDialog(navigateToSearch = true)
                },
            )
        }

        composable<DetailRoute> { backStackEntry ->
            val detailRoute: DetailRoute = backStackEntry.toRoute()
            DetailRoute(
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
                    navController.navigateToDetail(pokemonId)
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
                    navController.navigateToDetail(pokemonId)
                },
            )
        }
    }
}
