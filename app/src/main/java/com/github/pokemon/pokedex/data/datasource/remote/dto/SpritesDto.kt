package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class SpritesDto(
    @SerialName("front_default")
    val frontDefault: String? = null,
    @SerialName("other")
    val other: OtherSpritesDto? = null
)
@Serializable
data class OtherSpritesDto(
    @SerialName("dream_world")
    val dreamWorld: SimpleSpriteDto? = null,
    @SerialName("home")
    val home: HomeSpriteDto? = null,
    @SerialName("official-artwork")
    val officialArtwork: SimpleSpriteDto? = null
)

@Serializable
data class SimpleSpriteDto(
    @SerialName("front_default")
    val frontDefault: String? = null
)

@Serializable
data class HomeSpriteDto(
    @SerialName("front_default")
    val frontDefault: String? = null
)
