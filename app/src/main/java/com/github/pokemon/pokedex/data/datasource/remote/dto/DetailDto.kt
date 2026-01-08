package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("weight")
    val weight: Int? = null,
    @SerialName("abilities")
    val abilities: List<AbilitySlotDto> = emptyList(),
    @SerialName("types")
    val types: List<TypeSlotDto> = emptyList(),
    @SerialName("stats")
    val stats: List<StatDto> = emptyList(),
    @SerialName("sprites")
    val sprites: SpritesDto? = null,
)

@Serializable
data class AbilitySlotDto(
    @SerialName("ability")
    val ability: ApiResourceDto? = null,
    @SerialName("is_hidden")
    val isHidden: Boolean? = null,
    @SerialName("slot")
    val slot: Int? = null,
)

@Serializable
data class TypeSlotDto(
    @SerialName("slot")
    val slot: Int? = null,
    @SerialName("type")
    val type: ApiResourceDto? = null,
)

@Serializable
data class StatDto(
    @SerialName("base_stat")
    val baseStat: Int? = null,
    @SerialName("stat")
    val stat: ApiResourceDto? = null,
)
