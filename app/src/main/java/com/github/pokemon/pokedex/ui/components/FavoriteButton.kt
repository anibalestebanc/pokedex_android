package com.github.pokemon.pokedex.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.05f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "fav-scale"
    )
    IconButton(
        onClick = onToggle,
        enabled = enabled,
        modifier = modifier
            .size(40.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
    ) {
        val icon = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
        val tint = if (isFavorite) Color.White else Color.White
        Icon(icon, contentDescription = if (isFavorite) "Unfavorite" else "Favorite", tint = tint)
    }
}

@Preview
@Composable
fun FavoriteButtonPreview(){
    FavoriteButton(
        isFavorite = false,
        onToggle = {},
        modifier = Modifier
    )
}
