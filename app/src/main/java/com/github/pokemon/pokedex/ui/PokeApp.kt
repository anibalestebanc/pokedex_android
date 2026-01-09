package com.github.pokemon.pokedex.ui

import android.util.Log
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
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.pokemon.pokedex.navigation.NavigationHost
import com.github.pokemon.pokedex.navigation.NavigationRoute
import com.github.pokemon.pokedex.navigation.rememberNavigationActions
import com.github.pokemon.pokedex.theme.HighContrastDarkColorScheme
import com.github.pokemon.pokedex.theme.HighContrastLightColorScheme
import com.github.pokemon.pokedex.ui.components.bottombar.PokeBottomBar

@Composable
fun PokeApp(appState: AppState) {

    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) HighContrastDarkColorScheme else HighContrastLightColorScheme

    val snackbarHostState = remember { SnackbarHostState() }

    val backStack = rememberNavBackStack(NavigationRoute.Home)

    val navigationActions = rememberNavigationActions(backStack)

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (shouldShowBottomBar(current = backStack.lastOrNull()))
               PokeBottomBar(
                    current = backStack.lastOrNull(),
                    onClick = { destination ->
                        navigationActions.onTopLevelSelected(destination)
                    },
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                NavigationHost(
                    appState = appState,
                    backStack = backStack,
                    onNavigate = navigationActions,
                )
            }
        }
    }
}

private fun shouldShowBottomBar(current: NavKey?): Boolean {
    Log.d("Navigation3", "shouldShowBottomBar()-> current: ${current?.javaClass?.simpleName}")
    return when (current) {
        is NavigationRoute.Home, is NavigationRoute.SearchList, is NavigationRoute.Favorite -> true
        else -> false
    }
}
