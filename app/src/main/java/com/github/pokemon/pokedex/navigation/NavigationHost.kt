package com.github.pokemon.pokedex.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.github.pokemon.pokedex.ui.detail.DetailRoute
import com.github.pokemon.pokedex.ui.favorite.FavoriteRoute
import com.github.pokemon.pokedex.ui.home.HomeRoute
import com.github.pokemon.pokedex.ui.search.SearchDialogRoute
import com.github.pokemon.pokedex.ui.searchlist.SearchListRoute

@Composable
fun NavigationHost(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = navigationState.backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<NavigationRoute.Home> {
                HomeRoute(
                    onSearch = {
                        navigator.navigate(NavigationRoute.Search)
                    },
                )
            }

            entry<NavigationRoute.Detail> { detail ->
                DetailRoute(
                    onBack = { navigator.goBack() },
                    id = detail.id,
                )
            }

            entry<NavigationRoute.Search> {
                SearchDialogRoute(
                    onBackClick = { navigator.goBack() },
                    onSearchList = {
                        navigator.goBack()
                        navigator.navigate(NavigationRoute.SearchList)
                    },
                )
            }

            entry<NavigationRoute.SearchList> {
                SearchListRoute(
                    onDetail = { pokemonId ->
                        navigator.navigate(NavigationRoute.Detail(pokemonId))
                    },
                    onSearch = {
                        navigator.navigate(NavigationRoute.Search)
                    },
                )
            }

            entry<NavigationRoute.Favorite> {
                FavoriteRoute(
                    onDetail = { id ->
                        navigator.navigate(NavigationRoute.Detail(id))
                    },
                )
            }
        },
        transitionSpec = {
            ContentTransform(
                slideInHorizontally(initialOffsetX = { it }),
                slideOutHorizontally(targetOffsetX = { -it }),
            )
        },
        popTransitionSpec = {
            ContentTransform(
                slideInHorizontally(initialOffsetX = { -it }),
                slideOutHorizontally(targetOffsetX = { it }),
            )
        },
    )
}
