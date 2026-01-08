package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpeciesDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("color")
    val color: ApiResourceDto? = null,
    @SerialName("habitat")
    val habitat: ApiResourceDto? = null,
    @SerialName("egg_groups")
    val eggGroups: List<ApiResourceDto> = emptyList(),
    @SerialName("genera")
    val genera: List<GenusDto> = emptyList(),
    @SerialName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntryDto> = emptyList(),
    @SerialName("form_descriptions")
    val formDescriptions: List<FormDescriptionDto> = emptyList(),
    @SerialName("varieties")
    val varieties: List<SpeciesVarietyDto> = emptyList(),
    @SerialName("capture_rate")
    val captureRate: Int? = null,
    @SerialName("base_happiness")
    val baseHappiness: Int? = null,
    @SerialName("growth_rate")
    val growthRate: ApiResourceDto? = null,
    @SerialName("is_legendary")
    val isLegendary: Boolean = false,
    @SerialName("is_mythical")
    val isMythical: Boolean = false
)

@Serializable
data class FormDescriptionDto(
    @SerialName("description")
    val description: String? = null,
    @SerialName("language")
    val language: ApiResourceDto? = null
)

@Serializable
data class SpeciesVarietyDto(
    @SerialName("is_default")
    val isDefault: Boolean = false,
    @SerialName("pokemon")
    val pokemon: ApiResourceDto? = null
)
@Serializable
data class GenusDto(
    @SerialName("genus")
    val genus: String? = null,
    @SerialName("language")
    val language: ApiResourceDto? = null,
)

@Serializable
data class FlavorTextEntryDto(
    @SerialName("flavor_text")
    val flavorText: String? = null,
    @SerialName("language")
    val language: ApiResourceDto? = null,
    @SerialName("version")
    val version: ApiResourceDto? = null
)
