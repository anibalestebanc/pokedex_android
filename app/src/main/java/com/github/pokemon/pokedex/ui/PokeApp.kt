package com.github.pokemon.pokedex.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.navigation.AppNavHost
import com.github.pokemon.pokedex.ui.components.bottombar.PokeBottomBar

@Composable
fun PokeApp(
    appState: AppState,
    modifier: Modifier = Modifier,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val noConnectionMessage = stringResource(R.string.error_no_internet_title)
    //TODO this need more testing to apply in production
/*    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = noConnectionMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Short,
            )
        }
    }*/

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        contentColor = MaterialTheme.colorScheme.background,
        containerColor = Color.Transparent,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
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
