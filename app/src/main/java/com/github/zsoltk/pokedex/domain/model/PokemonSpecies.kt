package com.github.zsoltk.pokedex.domain.model

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val genera: String?,
    val flavorText: String?,
    val description: String?,
    val color: String?,
    val habitat: String?,
    val eggGroups: List<String>,
    val captureRate: Int?,
    val baseHappiness: Int?,
    val growthRate: String?,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val lastUpdated: Long
)
