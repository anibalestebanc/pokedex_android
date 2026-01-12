package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.components.MediumChip

@Composable
fun LegendarySection(
    modifier: Modifier = Modifier,
    isLegendary: Boolean = false,
    isMythical: Boolean = false,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isLegendary) {
            MediumChip(
                text = stringResource(R.string.pokemon_detail_legendary),
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                contentColor = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(Modifier.width(8.dp))
        if (isMythical) {
            MediumChip(
                text = stringResource(R.string.pokemon_detail_mythical),
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                contentColor = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LegendarySectionPreview() {
    LegendarySection(
        isLegendary = true,
        isMythical = true,
    )
}
