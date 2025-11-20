package com.github.zsoltk.pokedex

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.github.zsoltk.pokedex.navigation.AppNavHost
import com.github.zsoltk.pokedex.navigation.rememberAppState

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightThemeColors,
                typography = themeTypography,
            ) {
                val appState = rememberAppState()
                AppNavHost(appState)
            }
        }
    }
}
