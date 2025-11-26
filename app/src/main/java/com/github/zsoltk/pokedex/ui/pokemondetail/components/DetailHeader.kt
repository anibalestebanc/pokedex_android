package com.github.zsoltk.pokedex.ui.pokemondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun DetailHeader(
    name: String,
    numberLabel: String,
    genera: String?,
    types: List<String>,
    imageUrl: String?,
    headerColor: Color,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(headerColor)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 24.dp, y = 24.dp)
                .background(Color.White.copy(alpha = 0.15f), shape = CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 36.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = numberLabel,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (!genera.isNullOrBlank()) {
                        Text(
                            text = genera,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            ChipRow(
                chips = types.map { it.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() } },
                chipColor = Color.White.copy(alpha = 0.25f),
                contentColor = Color.White
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                )
                AsyncImage(
                    model = imageUrl,
                    contentDescription = name,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipRow(
    chips: List<String>,
    chipColor: Color = Color(0xFFF2F2F2),
    contentColor: Color = Color.Black
) {
    FlowRow {
        chips.forEach { TypeChip(it, chipColor, contentColor) }
    }
}

@Composable
fun TypeChip(
    text: String,
    background: Color = Color(0xFFF2F2F2),
    contentColor: Color = Color.Black
) {
    Box(
        modifier = Modifier
            .background(background, shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text, color = contentColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
