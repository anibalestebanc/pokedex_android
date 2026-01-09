package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.theme.PokeAppTheme
import com.github.pokemon.pokedex.ui.components.FavoriteButton
import com.github.pokemon.pokedex.ui.components.common.ChipGroup
import com.github.pokemon.pokedex.ui.components.utils.PokeBackgroundUtil.primaryTypeColorRes

@Composable
fun DetailHeader(
    name: String,
    numberLabel: String,
    genera: String?,
    types: List<String>,
    imageUrl: String?,
    isFavorite: Boolean,
    headerColor: Color,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShareClick: (String) -> Unit ,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(headerColor),
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp),
            ) {
                Icon( Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            FavoriteButton(
                isFavorite = isFavorite,
                onToggle = onToggleFavorite,
                modifier = Modifier
            )
        }


        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 24.dp, y = 24.dp)
                .background(Color.White.copy(alpha = 0.15f), shape = CircleShape),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 56.dp, bottom = 12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = numberLabel,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    if (!genera.isNullOrBlank()) {
                        Text(
                            text = genera,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                        )
                    }
                }
            }

            ChipGroup(
                chips = types.map { it.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() } },
                containerColor = Color.White.copy(alpha = 0.25f),
                contentColor = Color.White,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(160.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape),
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 24.dp, horizontal = 16.dp),
        ) {
            IconButton(
                modifier = modifier.size(40.dp),
                onClick = { onShareClick(imageUrl.orEmpty()) },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = stringResource(R.string.share),
                    tint = Color.White,
                )
            }
        }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .size(250.dp)
            .padding(bottom = 24.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                ),
        )

    }
}


@Preview
@Composable
fun DetailHeaderPreview() {
    PokeAppTheme {
        DetailHeader(
            name = "pikachu",
            numberLabel = "002",
            genera = "Seed Pokémon",
            types = listOf("Electric", "Electric"),
            isFavorite = false,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/",
            headerColor = colorResource(id = primaryTypeColorRes(listOf("Electric"))),
            onBackClick = {},
            onToggleFavorite = {},
            onShareClick = {}
        )
    }
}
