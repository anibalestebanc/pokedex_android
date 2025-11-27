package com.github.zsoltk.pokedex.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavoriteRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FavoriteScreen()
}

@Composable
fun FavoriteScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Favorite")
    }
}
