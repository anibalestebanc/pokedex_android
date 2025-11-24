package com.github.zsoltk.pokedex.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchRoute(
    initialQuery: String,
    onBackClick: () -> Unit,
    onDetailClick: (String) -> Unit,
) {
    SearchScreen(initialQuery)
}

@Composable
fun SearchScreen(initialQuery: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = initialQuery)
    }
}
