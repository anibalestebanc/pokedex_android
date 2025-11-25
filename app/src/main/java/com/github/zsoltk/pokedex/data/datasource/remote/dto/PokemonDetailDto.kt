package com.github.zsoltk.pokedex.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int? = null,
    val weight: Int? = null,
    val abilities: List<AbilitySlotDto> = emptyList(),
    val types: List<TypeSlotDto> = emptyList(),
    val stats: List<StatDto> = emptyList(),
    val sprites: SpritesDto? = null
)

@Serializable
data class AbilitySlotDto(
    val ability: NamedApiResourceDto? = null, val is_hidden: Boolean? = null, val slot: Int? = null
)
@Serializable
data class TypeSlotDto(
    val slot: Int? = null, val type: NamedApiResourceDto? = null
)
@Serializable
data class StatDto(
    val base_stat: Int? = null, val stat: NamedApiResourceDto? = null
)
@Serializable
data class NamedApiResourceDto(
    val name: String? = null, val url: String? = null
)

@Serializable
data class SpritesDto(
    val front_default: String? = null,
    val other: Map<String, OtherSpriteDto>? = null
)
@Serializable
data class OtherSpriteDto(
    val front_default: String? = null
)
