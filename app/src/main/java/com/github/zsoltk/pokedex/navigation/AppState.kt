package com.github.zsoltk.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState = remember(coroutineScope, navController) {
    AppState(coroutineScope, navController)
}

@Stable
class AppState(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {

}
