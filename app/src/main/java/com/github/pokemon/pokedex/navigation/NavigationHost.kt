package com.github.pokemon.pokedex.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
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

    val entryProvider: (NavKey) -> NavEntry<NavKey> =
        entryProvider {
            entry<Route.Home> {
                HomeRoute(
                    onSearch = {
                        navigator.navigate(Route.Search)
                    },
                )
            }

            entry<Route.Detail> { detail ->
                DetailRoute(
                    onBack = { navigator.goBack() },
                    id = detail.id,
                )
            }

            entry<Route.Search> {
                SearchDialogRoute(
                    onBackClick = { navigator.goBack() },
                    onSearchList = {
                        navigator.goBack()
                        navigator.navigate(Route.SearchList)
                    },
                )
            }

            entry<Route.SearchList> {
                SearchListRoute(
                    onDetail = { pokemonId ->
                        navigator.navigate(Route.Detail(pokemonId))
                    },
                    onSearch = {
                        navigator.navigate(Route.Search)
                    },
                )
            }

            entry<Route.Favorite> {
                FavoriteRoute(
                    onDetail = { id ->
                        navigator.navigate(Route.Detail(id))
                    },
                )
            }
        }

    NavDisplay(
        modifier = modifier.fillMaxSize(),
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
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
