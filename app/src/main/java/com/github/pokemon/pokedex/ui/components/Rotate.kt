package com.github.pokemon.pokedex.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun RotateIndefinitely(durationPerRotation: Int, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationPerRotation, easing = LinearEasing)
        )
    )

    Rotate(degree = angle) {
        content()
    }
}

@Composable
fun Rotate(degree: Float, content: @Composable () -> Unit) {
    Box(modifier = Modifier.graphicsLayer(rotationZ = degree)) {
        content()
    }
}
