package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.utils.capitalizeFirst

@Composable
fun TrainingSection(
    growthRate: String,
    captureRate: Int,
    baseHappiness: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(horizontalAlignment = CenterHorizontally) {
            Text(
                stringResource(R.string.pokemon_detail_growth),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(text = capitalizeFirst(growthRate),)
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(
                stringResource(R.string.pokemon_detail_capture),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(captureRate.toString())
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(
                stringResource(R.string.pokemon_detail_happiness),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(baseHappiness.toString())
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TrainingSectionPreview() {
    TrainingSection(
        growthRate = "Fast",
        captureRate = 1,
        baseHappiness = 1,
    )
}
