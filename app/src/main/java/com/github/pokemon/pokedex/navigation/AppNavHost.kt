package com.github.pokemon.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.github.pokemon.pokedex.navigation.Screen.DetailScreen
import com.github.pokemon.pokedex.navigation.Screen.FavoriteScreen
import com.github.pokemon.pokedex.navigation.Screen.HomeScreen
import com.github.pokemon.pokedex.navigation.Screen.SearchListScreen
import com.github.pokemon.pokedex.navigation.Screen.SearchScreen
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
        startDestination = HomeScreen,
        modifier = modifier,
    ) {

        composable<HomeScreen> {
            HomeRoute(
                onSearchClick = {
                    navController.navigateToSearchDialog(navigateToSearch = true)
                },
            )
        }

        composable<DetailScreen> { backStackEntry ->
            val detailRoute: DetailScreen = backStackEntry.toRoute()
            DetailRoute(
                onBackClick = navController::popBackStack,
                pokemonId = detailRoute.pokemonId,
            )
        }

        composable<SearchListScreen>{ backStackEntry ->
            val searchRoute: SearchListScreen = backStackEntry.toRoute()
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

        dialog<SearchScreen>(
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) { backStackEntry ->
            val searchRoute: SearchScreen = backStackEntry.toRoute()
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

        composable<FavoriteScreen> {
            FavoriteRoute(
                onDetailClick = { pokemonId ->
                    navController.navigateToDetail(pokemonId)
                },
            )
        }
    }
}
