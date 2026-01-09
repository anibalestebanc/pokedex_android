package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R

@Composable
fun CharacteristicsSection(
    heightMeters: Double,
    weightKg: Double,
    habitat: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.pokemon_detail_height), color = MaterialTheme.colorScheme.onSurface)
            Text(stringResource(R.string.pokemon_detail_height_value, heightMeters), fontWeight = FontWeight.SemiBold)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.pokemon_detail_weight), color = MaterialTheme.colorScheme.onSurface)
            Text(stringResource(R.string.pokemon_detail_weight_value, weightKg), fontWeight = FontWeight.SemiBold)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.pokemon_detail_habitat), color = MaterialTheme.colorScheme.onSurface)
            Text(habitat?.replaceFirstChar { it.uppercase() } ?: "", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacteristicsSectionPreview() {
    CharacteristicsSection(
        heightMeters = 1.0,
        weightKg = 1.0,
        habitat = "Forest",
    )
}

