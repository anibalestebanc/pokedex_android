package com.github.pokemon.pokedex.ui.components.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun LargeAppBar(
    background: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp
        )
    ) {
        Box {
            background()

            Box(contentAlignment = Alignment.TopCenter) {
                content()
            }
        }
    }
}
