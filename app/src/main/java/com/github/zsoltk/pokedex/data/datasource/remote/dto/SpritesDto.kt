package com.github.zsoltk.pokedex.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class SpritesDto(
    val front_default: String? = null,
    val other: OtherSpritesDto? = null
)
@Serializable
data class OtherSpritesDto(
    val dream_world: SimpleSpriteDto? = null,
    val home: HomeSpriteDto? = null,
    @SerialName("official-artwork")
    val official_artwork: SimpleSpriteDto? = null
)

@Serializable
data class SimpleSpriteDto(
    val front_default: String? = null
)

@Serializable
data class HomeSpriteDto(
    val front_default: String? = null
)
