package com.github.zsoltk.pokedex.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val sprites: PokemonSprites,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val stats: List<Stat>,
    val isFavorite: Boolean,
    val lastUpdated: Long
)
