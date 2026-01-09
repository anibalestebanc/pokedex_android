package com.github.pokemon.pokedex.domain.model

data class PokemonFullDetail(
    // detail values
    val id: Int,
    val name: String,
    val numberLabel: String,
    val imageUrl: String?,
    val types: List<String>,
    val heightMeters: Double,
    val weightKg: Double,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites?,
    val isFavorite: Boolean,
    // species values
    val genera: String?,
    val flavorText: String?,
    val color: String?,
    val description: String?,
    val habitat: String?,
    val eggGroups: List<String>,
    val captureRate: Int,
    val baseHappiness: Int,
    val growthRate: String,
    val isLegendary: Boolean,
    val isMythical: Boolean,
)
