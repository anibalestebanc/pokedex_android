package com.github.zsoltk.pokedex.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.R

@Composable
fun PokeBallBackground() {
    Box(
        modifier = Modifier.offset(x = 90.dp, y = (-70).dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier.size(240.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            PokeBallLarge(
                tint = colorResource(id = R.color.grey_100)
            )
        }
    }
}
