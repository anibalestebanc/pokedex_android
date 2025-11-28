package com.github.pokemon.pokedex.core.network.utils

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
