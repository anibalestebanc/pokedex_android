package com.github.zsoltk.pokedex.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.zsoltk.pokedex.navigation.AppNavHost
import com.github.zsoltk.pokedex.ui.components.bottombar.PokeBottomBar

@Composable
fun PokeApp(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            if (appState.shouldShowBottomBar()) {
                PokeBottomBar(
                    destinations = appState.topDestinations,
                    selectedId = appState.currentTopLevelDestinationId(),
                    onSelect = { id -> appState.navigateToTopLevelDestination(id) }
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            AppNavHost(appState)
        }
    }
}
