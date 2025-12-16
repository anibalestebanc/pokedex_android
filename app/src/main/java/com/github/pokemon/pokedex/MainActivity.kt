package com.github.pokemon.pokedex

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.ui.rememberAppState
import com.github.pokemon.pokedex.theme.PokeAppTheme
import com.github.pokemon.pokedex.ui.PokeApp
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            )
        )

        setContent {
            val appState = rememberAppState(networkMonitor)
            PokeAppTheme {
                PokeApp(appState)
            }
        }
    }
}
