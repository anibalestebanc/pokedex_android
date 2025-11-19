package com.github.zsoltk.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.zsoltk.pokedex.home.Home
import com.github.zsoltk.pokedex.home.Home.MenuItem
import com.github.zsoltk.pokedex.pokedex.Pokedex

interface Root {

    sealed class Routing(val route: String) {
        object Home : Routing("home")
        object Pokedex : Routing("pokedex")
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing = Routing.Home) {
            val navController = rememberNavController()
            NavHost(navController, startDestination = defaultRouting.route) {
                composable(Routing.Home.route) {
                    Home.Content {
                        when (it) {
                            MenuItem.Pokedex -> navController.navigate(Routing.Pokedex.route)
                            else -> {}
                        }
                    }
                }
                composable(Routing.Pokedex.route) {
                    Pokedex.Content()
                }
            }
        }
    }
}
