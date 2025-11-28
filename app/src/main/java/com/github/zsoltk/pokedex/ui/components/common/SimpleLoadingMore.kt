package com.github.zsoltk.pokedex.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleLoadingMore() {
    Box(modifier = Modifier.fillMaxWidth()
        .padding(vertical = 4.dp)) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
