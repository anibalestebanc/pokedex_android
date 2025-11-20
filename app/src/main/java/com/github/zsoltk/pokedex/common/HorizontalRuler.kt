package com.github.zsoltk.pokedex.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalRuler(
    color: Color,
    width: Dp? = null,
    height: Dp = 2.dp,
    centered: Boolean = true
) {
    val arrangement = if (centered) Arrangement.Center else Arrangement.Start
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement
    ) {
        val modifier = if (width != null) {
            Modifier.width(width).height(height)
        } else {
            Modifier.fillMaxWidth().height(height)
        }

        Surface(color = color) {
            Box(modifier = modifier) { }
        }
    }
}
