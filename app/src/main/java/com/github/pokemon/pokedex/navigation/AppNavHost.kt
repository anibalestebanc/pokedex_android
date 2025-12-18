package com.github.pokemon.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.github.pokemon.pokedex.navigation.ScreenRoute.DetailScreenRoute
import com.github.pokemon.pokedex.navigation.ScreenRoute.FavoriteScreenRoute
import com.github.pokemon.pokedex.navigation.ScreenRoute.HomeScreenRoute
import com.github.pokemon.pokedex.navigation.ScreenRoute.SearchListScreenRoute
import com.github.pokemon.pokedex.navigation.ScreenRoute.SearchScreenRoute
import com.github.pokemon.pokedex.ui.AppState
import com.github.pokemon.pokedex.ui.detail.DetailRoute
import com.github.pokemon.pokedex.ui.detail.navigation.navigateToDetail
import com.github.pokemon.pokedex.ui.favorite.FavoriteRoute
import com.github.pokemon.pokedex.ui.home.HomeRoute
import com.github.pokemon.pokedex.ui.search.SearchDialogRoute
import com.github.pokemon.pokedex.ui.search.navigation.navigateToSearchDialog
import com.github.pokemon.pokedex.ui.searchlist.SearchListRoute
import com.github.pokemon.pokedex.ui.searchlist.navigation.SEARCH_LIST_KEY
import com.github.pokemon.pokedex.ui.searchlist.navigation.navigateToSearchList

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = modifier,
    ) {

        composable<HomeScreenRoute> {
            HomeRoute(
                onSearchClick = {
                    navController.navigateToSearchDialog(navigateToSearch = true)
                },
            )
        }

        composable<DetailScreenRoute> { backStackEntry ->
            val detailRoute: DetailScreenRoute = backStackEntry.toRoute()
            DetailRoute(
                onBackClick = navController::popBackStack,
                pokemonId = detailRoute.pokemonId,
            )
        }

        composable<SearchListScreenRoute>{ backStackEntry ->
            val searchRoute: SearchListScreenRoute = backStackEntry.toRoute()
            SearchListRoute(
                appState = appState,
                initialQuery = searchRoute.query,
                onDetailClick = { pokemonId ->
                    navController.navigateToDetail(pokemonId)
                },
                onSearchClick = { q ->
                    navController.navigateToSearchDialog(query = q)
                },
            )
        }

        dialog<SearchScreenRoute>(
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) { backStackEntry ->
            val searchRoute: SearchScreenRoute = backStackEntry.toRoute()
            SearchDialogRoute(
                navigateToResult = searchRoute.navigateToSearch,
                initialQuery = searchRoute.query,
                onBackClick = navController::popBackStack,
                onBackAndSaveStateHandle = { query ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(SEARCH_LIST_KEY, query)
                    navController.popBackStack()
                },
                onSearchResult = { query ->
                    navController.navigateToSearchList(query)
                },
            )
        }

        composable<FavoriteScreenRoute> {
            FavoriteRoute(
                onBackClick = navController::popBackStack,
                onDetailClick = { pokemonId ->
                    navController.navigateToDetail(pokemonId)
                },
            )
        }
    }
}
