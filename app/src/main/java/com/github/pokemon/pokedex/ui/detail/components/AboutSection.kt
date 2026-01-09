package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail
import com.github.pokemon.pokedex.domain.model.PokemonSprites

@Composable
fun AboutSection(detail: PokemonFullDetail, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
    ) {
        val description = detail.description ?: detail.flavorText
        if (!description.isNullOrBlank()) {
            Text(description, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(16.dp))
        }
        CharacteristicsSection(
            heightMeters = detail.heightMeters,
            weightKg = detail.weightKg,
            habitat = detail.habitat,
            modifier = Modifier.padding(top = 8.dp),
        )

        if (detail.abilities.isNotEmpty() || detail.eggGroups.isNotEmpty()) {
            BreedingSection(
                abilities = detail.abilities,
                eggGroups = detail.eggGroups,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        TrainingSection(
            growthRate = detail.growthRate,
            captureRate = detail.captureRate,
            baseHappiness = detail.baseHappiness,
            modifier = Modifier.padding(top = 8.dp),
        )

        if (detail.isLegendary || detail.isMythical) {
            LegendarySection(
                isLegendary = detail.isLegendary,
                isMythical = detail.isMythical,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutSectionPreview() {
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
            isLegendary = true,
            isMythical = true,
        ),
    )
}
