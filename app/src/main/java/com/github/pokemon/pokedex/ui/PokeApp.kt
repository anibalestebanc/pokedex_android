package com.github.pokemon.pokedex.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.pokemon.pokedex.navigation.NavigationHost
import com.github.pokemon.pokedex.navigation.Route
import com.github.pokemon.pokedex.navigation.rememberNavigationState
import com.github.pokemon.pokedex.navigation.rememberNavigator
import com.github.pokemon.pokedex.theme.HighContrastDarkColorScheme
import com.github.pokemon.pokedex.theme.HighContrastLightColorScheme
import com.github.pokemon.pokedex.ui.components.model.TOP_LEVEL_DESTINATION
import com.github.pokemon.pokedex.ui.components.bottombar.PokeBottomBar
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun PokeApp(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) HighContrastDarkColorScheme else HighContrastLightColorScheme

    val snackBarHostState = remember { SnackbarHostState() }

    val navigationState = rememberNavigationState(
        startDestination = Route.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATION.keys.toImmutableSet(),
    )
    val navigator = rememberNavigator(navigationState)

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            snackbarHost = { SnackbarHost(snackBarHostState) },
            bottomBar = {
                if (navigationState.currentRoute in TOP_LEVEL_DESTINATION.keys) {
                    PokeBottomBar(
                        selected = navigationState.topLevelRoute,
                        destinations = TOP_LEVEL_DESTINATION.toImmutableMap(),
                        onClick = { route -> navigator.navigate(route) },
                    )
                }
            },
            contentWindowInsets = WindowInsets.systemBars,
        ) { innerPadding ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                NavigationHost(
                    navigationState = navigationState,
                    navigator = navigator,
                )
            }
        }
    }
}
