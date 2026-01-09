package com.github.pokemon.pokedex.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.domain.model.PokemonStat
import com.github.pokemon.pokedex.domain.model.PokemonStatType

@Composable
fun BaseStatsSection(
    stats: List<PokemonStat>,
    modifier: Modifier = Modifier
) {
    val items = remember(stats) {
        val statsMap = stats.associateBy { it.name.lowercase() }
        PokemonStatType.entries.map { type ->
            val value = statsMap[type.key]?.value ?: 0
            type.label to value
        }
    }

    val total = remember(items) { items.sumOf { it.second } }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
    ) {
        items.forEach { (label, value) ->
            StatRow(label = label, value = value)
            Spacer(Modifier.height(8.dp))
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        StatRow(
            label = "Total",
            value = total,
            accent = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun StatRow(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
    accent: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.width(96.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value.toString(),
            modifier = Modifier.width(40.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        StatBar(
            progress = (value / 255f).coerceIn(0f, 1f),
            color = accent,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun StatBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(color),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BaseStatsSectionPreview() {
    MaterialTheme {
        BaseStatsSection(
            stats = listOf(
                PokemonStat("hp", 45),
                PokemonStat("attack", 49),
                PokemonStat("defense", 49),
                PokemonStat("special-attack", 65),
                PokemonStat("special-defense", 65),
                PokemonStat("speed", 45),
            ),
        )
    }
}
