package com.github.pokemon.pokedex.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    return remember(networkMonitor, coroutineScope) {
        AppState(coroutineScope, networkMonitor)
    }
}

@Stable
class AppState(
    val coroutineScope: CoroutineScope,
    val networkMonitor: NetworkMonitor,
) {

    @Suppress("MagicNumber")
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}
