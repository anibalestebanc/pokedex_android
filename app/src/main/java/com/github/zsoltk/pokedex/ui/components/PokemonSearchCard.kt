package com.github.zsoltk.pokedex.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.zsoltk.pokedex.theme.PokeAppTheme
import com.github.zsoltk.pokedex.ui.components.utils.PokeBackgroundUtil.primaryTypeColorRes

@Composable
fun PokemonSearchCard(
    index: Int,
    number: Int?,
    name: String,
    types: List<String>,
    imageUrl: String?,
    fallbackThumbUrl: String?,
    isLoadingDetail: Boolean,
    errorDetail: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = colorResource(id = primaryTypeColorRes(types))
    val bgGradient = remember(bgColor) {
        Brush.linearGradient(
            colors = listOf(bgColor.copy(alpha = 0.98f), bgColor.copy(alpha = 0.78f)),
            start = Offset.Zero,
            end = Offset.Infinite,
        )
    }
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.9f)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(0.dp, Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {

        Box(modifier = Modifier.background(bgGradient)) {

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 28.dp)
                    .background(Color.White.copy(alpha = 0.18f), shape = CircleShape),
            )

            if (number != null) {
                Text(
                    text = "#${number.toString().padStart(3, '0')}",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White.copy(alpha = 0.85f),
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 24.dp),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(Modifier.height(6.dp))

                    when {
                        isLoadingDetail && types.isEmpty() -> {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                ChipPlaceholder(width = 56.dp)
                                ChipPlaceholder(width = 44.dp)
                            }
                        }

                        errorDetail != null && types.isEmpty() -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                modifier = Modifier
                                    .padding(top = 4.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Warning,
                                    contentDescription = "error",
                                    tint = Color.White.copy(alpha = 0.9f),
                                    modifier = Modifier.size(16.dp),
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    errorDetail,
                                    style = MaterialTheme.typography.labelMedium.copy(color = Color.White),
                                )
                            }
                        }

                        else -> {
                            FlowRow(
                                modifier = Modifier
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalArrangement = Arrangement.Top,
                            ) {
                                types.take(3).forEach { t -> TypeChip(t) }
                            }
                        }
                    }
                }
                val finalUrl = imageUrl ?: fallbackThumbUrl
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    if (finalUrl == null) {
                        Box(
                            Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .shimmer()
                                .background(Color.White.copy(alpha = 0.35f)),
                        )
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(finalUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = name,
                            modifier = Modifier
                                .size(110.dp)
                                .padding(end = 6.dp)
                                .graphicsLayer { translationY = -6f },
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
            }

        }
    }
}


@Composable
private fun TypeChip(type: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.22f))
            .border(1.dp, Color.White.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = type.replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.labelMedium.copy(color = Color.White),
        )
    }
}

@Composable
private fun ChipPlaceholder(width: Dp) {
    Box(
        Modifier
            .height(22.dp)
            .width(width)
            .clip(RoundedCornerShape(12.dp))
            .shimmer()
            .background(Color.White.copy(alpha = 0.35f)),
    )
}

fun Modifier.shimmer(): Modifier = composed {
    val infinite = rememberInfiniteTransition(label = "shimmer")
    val alpha by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "shimmerAlpha",
    )
    this.alpha(alpha)
}

@Preview
@Composable
fun PokemonSearchCardPreview() {
    PokeAppTheme {
        PokemonSearchCard(
            index = 1,
            number = 1,
            name = "Pikachu",
            types = listOf("Electric", "grass"),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/25.png",
            fallbackThumbUrl = "R.drawable.poke001",
            isLoadingDetail = false,
            errorDetail = "Ha ocurrido un error",
            onClick = {},
        )
    }
}
