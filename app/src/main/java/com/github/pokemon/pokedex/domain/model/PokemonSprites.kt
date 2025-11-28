package com.github.pokemon.pokedex.domain.model

data class PokemonSprites(
    val dreamWorld: String?,
    val home: String?,
    val officialArtwork: String?,
    val fallbackFront: String?,
){
    val defaultImageUrl: String?
        get() = officialArtwork  ?: home ?: dreamWorld ?: fallbackFront
}
