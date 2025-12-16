package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail
import com.github.pokemon.pokedex.domain.model.PokemonSprites

@Composable
fun AboutSection(data: PokemonFullDetail) {
    Column(Modifier.fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
        .padding(16.dp)
    ) {
        val description = data.description ?: data.flavorText
        if (!description.isNullOrBlank()) {
            Text(description, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(12.dp))
        }

        KeyValueRow(stringResource(R.string.pokemon_detail_height), String.format("%.1f m", data.heightMeters))
        KeyValueRow(stringResource(R.string.pokemon_detail_weight), String.format("%.1f kg", data.weightKg))

        if (!data.habitat.isNullOrBlank()) {
            KeyValueRow(stringResource(R.string.pokemon_detail_habitat), data.habitat!!.replaceFirstChar { it.uppercase() })
        }

        if (data.eggGroups.isNotEmpty()) {
            Text(stringResource(R.string.pokemon_detail_egg_groups), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
            ChipRow(chips = data.eggGroups.map { it.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() } })
        }

        if (data.abilities.isNotEmpty()) {
            Text(stringResource(R.string.pokemon_detail_abilities), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 12.dp, bottom = 4.dp))
            ChipRow(chips = data.abilities.map { it.replace('-', ' ').replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() } })
        }

        Row(Modifier.padding(top = 12.dp)) {
            data.growthRate?.let { KeyValueSmall(stringResource(R.string.pokemon_detail_growth), it.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() }) }
            Spacer(Modifier.width(16.dp))
            data.captureRate?.let { KeyValueSmall(stringResource(R.string.pokemon_detail_capture), it.toString()) }
            Spacer(Modifier.width(16.dp))
            data.baseHappiness?.let { KeyValueSmall(stringResource(R.string.pokemon_detail_happiness), it.toString()) }
        }

        if (data.isLegendary || data.isMythical) {
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (data.isLegendary) TagPill(stringResource(R.string.pokemon_detail_legendary), MaterialTheme.colorScheme.primary)
                if (data.isMythical) {
                    Spacer(Modifier.width(8.dp))
                    TagPill(stringResource(R.string.pokemon_detail_mythical),MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

@Composable
fun KeyValueRow(label: String, value: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurface)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun KeyValueSmall(label: String, value: String) {
    Column {
        Text(label, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun TagPill(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.12f), shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text, color = color, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AboutSectionPreview(){
    AboutSection(
        PokemonFullDetail(
            id = 25,
            name = "Pikachu",
            isFavorite = true,
            imageUrl = "url",
            sprites = PokemonSprites(
                dreamWorld = null,
                home = null,
                officialArtwork = null,
                fallbackFront = null,
            ),
            types = listOf("Electric"),
            abilities = listOf("Lightning Rod"),
            stats = listOf(),
            flavorText = "This is a description",
            weightKg = 1.0,
            heightMeters = 1.0,
            numberLabel = "001",
            genera = "Pikachu",
            color = "Yellow",
            description = "This is a description",
            habitat = "Forest",
            eggGroups = listOf("Monster"),
            captureRate = 1,
            baseHappiness = 1,
            growthRate = "Fast",
            isLegendary = false,
            isMythical = false,
        )
    )
}
