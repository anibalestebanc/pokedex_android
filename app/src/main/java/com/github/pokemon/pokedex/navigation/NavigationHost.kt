package com.github.pokemon.pokedex.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.github.pokemon.pokedex.ui.AppState
import com.github.pokemon.pokedex.ui.detail.DetailRoute
import com.github.pokemon.pokedex.ui.favorite.FavoriteRoute
import com.github.pokemon.pokedex.ui.home.HomeRoute
import com.github.pokemon.pokedex.ui.search.SearchDialogRoute
import com.github.pokemon.pokedex.ui.searchlist.SearchListRoute

@Composable
fun NavigationHost(
    appState: AppState,
    backStack: NavBackStack<NavKey>,
    onNavigate: NavigationActions,
    modifier: Modifier = Modifier,
) {

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NavigationRoute.Home> {
                HomeRoute(
                    onSearch = {
                       // backStack.add(NavigationRoute.Search(navigateToSearch = true))
                    },
                )
            }

            entry<NavigationRoute.Detail> { detail ->
                DetailRoute(
                    onBack = { backStack.removeLastOrNull() },
                    pokemonId = detail.id,
                )
            }

            entry<NavigationRoute.Search> { search ->
                SearchDialogRoute(
                    navigateToResult = search.navigateToSearch,
                    initialQuery = search.query,
                    onBackClick = onNavigate.onBack,
                    onBackAndSaveStateHandle = { query ->
                       /* navController.previousBackStackEntry?.savedStateHandle?.set(SEARCH_LIST_KEY, query)
                        navController.popBackStack()*/
                    },
                    onSearchResult = { query ->
                        //backStack.goToTopDestination()
                    },
                )
            }

            entry<NavigationRoute.SearchList> { searchList ->
                SearchListRoute(
                    appState = appState,
                    initialQuery = searchList.query,
                    onDetail = { pokemonId ->
                        backStack.add(NavigationRoute.Detail(pokemonId))
                    },
                    onSearch = { query ->
                        //backStack.add(NavigationRoute.Search(query = query, navigateToSearch = true))
                    },
                )
            }

            entry<NavigationRoute.Favorite> {
                FavoriteRoute(
                    onDetail = { pokemonId ->
                        backStack.add(NavigationRoute.Detail(pokemonId))
                    },
                )
            }
        },
        transitionSpec = {
            ContentTransform(
                slideInHorizontally(initialOffsetX = { it }),
                slideOutHorizontally(targetOffsetX = { -it })
            )
        },
        popTransitionSpec = {
            ContentTransform(
                slideInHorizontally(initialOffsetX = { -it }),
                slideOutHorizontally(targetOffsetX = { it })
            )
        },
    )

/*    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier,
    ) {

        dialog<Search>(
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) { backStackEntry ->
            val searchRoute: Search = backStackEntry.toRoute()
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

    }*/
}
