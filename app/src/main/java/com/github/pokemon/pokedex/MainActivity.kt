package com.github.pokemon.pokedex

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.ui.rememberAppState
import com.github.pokemon.pokedex.theme.PokeAppTheme
import com.github.pokemon.pokedex.ui.PokeApp
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val networkMonitor: NetworkMonitor by  inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState(networkMonitor)
            PokeAppTheme {
                PokeApp(appState)
            }
        }
    }
}
