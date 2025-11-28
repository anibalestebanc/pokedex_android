package com.github.pokemon.pokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

@Composable
fun LoadImage(
    imageResId: Int,
    tint: Color? = null,
    opacity: Float = 1.0f
) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = Modifier.alpha(opacity),
        colorFilter = tint?.let { color ->
            ColorFilter.tint(color)
        }
    )
}
