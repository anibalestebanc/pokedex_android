package com.github.pokemon.pokedex.ui.pokemondetail.components

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
typealias PokemonStat = com.github.pokemon.pokedex.domain.model.Stat


@Composable
fun BaseStatsSectionV2(stats: List<PokemonStat>) {
    val order = listOf("hp", "attack", "defense", "special-attack", "special-defense", "speed")
    val statsMap = stats.associateBy { it.name.lowercase() }
    val items = order.map { key ->
        val value = statsMap[key]?.value ?: 0
        val label = when (key) {
            "hp" -> "HP"
            "attack" -> "Attack"
            "defense" -> "Defense"
            "special-attack" -> "Sp. Atk"
            "special-defense" -> "Sp. Def"
            "speed" -> "Speed"
            else -> key.replaceFirstChar { it.uppercase() }
        }
        label to value
    }
    val total = items.sumOf { it.second }

    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        items.forEach { (label, value) ->
            StatRow(label = label, value = value)
            Spacer(Modifier.height(8.dp))
        }
        Divider(Modifier.padding(vertical = 8.dp), color = Color(0xFFE0E0E0))
        StatRow(label = "Total", value = total, accent = Color(0xFFE53935))
    }
}

@Composable
fun StatRow(label: String, value: Int, accent: Color = Color(0xFFEF5350)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            modifier = Modifier.width(96.dp),
            color = Color(0xFF616161)
        )
        Text(
            text = value.toString(),
            modifier = Modifier.width(40.dp),
            fontWeight = FontWeight.SemiBold
        )
        StatBar(
            progress = (value / 255f).coerceIn(0f, 1f),
            color = accent,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatBar(progress: Float, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFFFCDD2))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(color)
        )
    }
}
