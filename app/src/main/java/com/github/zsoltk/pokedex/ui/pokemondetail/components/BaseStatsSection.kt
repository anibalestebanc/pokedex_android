package com.github.zsoltk.pokedex.ui.pokemondetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.domain.model.Pokemon

data class Stat(
    val label: String,
    val value: Int?,
    val max: Int = 100
) {
    val progress: Float =
        1f * (value ?: 0) / max
}


@Composable
fun BaseStatsSection(pokemon: Pokemon) {
    val stats = listOf(
        Stat(stringResource(id = R.string.pokemon_detail_hp), pokemon.hp),
        Stat(stringResource(id = R.string.pokemon_detail_attack), pokemon.attack),
        Stat(stringResource(id = R.string.pokemon_detail_defense), pokemon.defense),
        Stat(stringResource(id = R.string.pokemon_detail_sp_atk), pokemon.specialAttack),
        Stat(stringResource(id = R.string.pokemon_detail_sp_def), pokemon.specialDefense),
        Stat(stringResource(id = R.string.pokemon_detail_speed), pokemon.speed),
        Stat(stringResource(id = R.string.pokemon_detail_total), pokemon.total, 600)
    )

    StatsTable(stats)
}

@Composable
private fun StatsTable(stats: List<Stat>) {
    Column {
        stats.forEach { stat ->
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = stat.label,
                    modifier = Modifier.weight(0.3f),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colorResource(id = R.color.grey_900)
                    )
                )

                Text(
                    text = stat.value.toString(),
                    modifier = Modifier.weight(0.2f),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                LinearProgressIndicator(
                    progress = { stat.progress },
                    modifier = Modifier.weight(0.5f).padding(top = 8.dp),
                    color = ProgressIndicatorDefaults.linearColor,
                    trackColor = Color.Red,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            }
        }
    }
}
