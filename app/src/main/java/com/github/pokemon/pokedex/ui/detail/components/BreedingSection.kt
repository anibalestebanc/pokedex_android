package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.components.common.ChipGroup
import com.github.pokemon.pokedex.utils.capitalizeFirst

@Composable
fun BreedingSection(
    abilities: List<String>,
    eggGroups: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (eggGroups.isNotEmpty()) {
            Text(
                text = stringResource(R.string.pokemon_detail_egg_groups),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
            )
            Spacer(Modifier.height(4.dp))
            ChipGroup(chips = eggGroups.map { egg -> capitalizeFirst(egg) })
        }

        if (abilities.isNotEmpty()) {
            Text(
                text = stringResource(R.string.pokemon_detail_abilities),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
            )
            Spacer(Modifier.height(4.dp))
            ChipGroup(
                chips = abilities.map { ability -> capitalizeFirst(ability) },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BreedingSectionPreview() {
    BreedingSection(
        abilities = listOf("Lightning Rod"),
        eggGroups = listOf("Monster"),
    )
}
