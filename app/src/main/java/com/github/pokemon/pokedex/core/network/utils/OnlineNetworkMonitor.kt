package com.github.pokemon.pokedex.core.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn

class OnlineNetworkMonitor(private val context: Context) : NetworkMonitor {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isOnline: Flow<Boolean> = callbackFlow {
        trySend(isCurrentlyConnected())

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d("OnlineNetworkMonitor", "onAvailable")
                trySend(isCurrentlyConnected())
            }

            override fun onLost(network: Network) {
                Log.d("OnlineNetworkMonitor", "onLost")
                trySend(isCurrentlyConnected())
            }

            override fun onUnavailable() {
                Log.d("OnlineNetworkMonitor", "onUnavailable")
                trySend(false)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }

    }
        .distinctUntilChanged()
        .shareIn(coroutineScope, started = SharingStarted.Eagerly, replay = 1)


    private fun isCurrentlyConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
