package com.github.zsoltk.pokedex

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.zsoltk.pokedex.ui.rememberAppState
import com.github.zsoltk.pokedex.theme.PokeAppTheme
import com.github.zsoltk.pokedex.ui.PokeApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState()
            PokeAppTheme {
                PokeApp(appState)
            }
        }
    }
}
