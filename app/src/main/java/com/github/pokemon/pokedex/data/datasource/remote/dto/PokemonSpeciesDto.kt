package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesDto(
    val id: Int,
    val name: String,
    val color: NamedApiResourceDto? = null,
    val habitat: NamedApiResourceDto? = null,
    val egg_groups: List<NamedApiResourceDto> = emptyList(),
    val genera: List<GenusDto> = emptyList(),
    val flavor_text_entries: List<FlavorTextEntryDto> = emptyList(),
    val form_descriptions: List<FormDescriptionDto> = emptyList(),
    val varieties: List<PokemonSpeciesVarietyDto> = emptyList(),
    val capture_rate: Int? = null,
    val base_happiness: Int? = null,
    val growth_rate: NamedApiResourceDto? = null,
    val is_legendary: Boolean = false,
    val is_mythical: Boolean = false
)

@Serializable
data class FormDescriptionDto(
    val description: String? = null,
    val language: NamedApiResourceDto? = null
)

@Serializable
data class PokemonSpeciesVarietyDto(
    val is_default: Boolean = false,
    val pokemon: NamedApiResourceDto? = null
)
@Serializable
data class GenusDto(
    val genus: String? = null,
    val language: NamedApiResourceDto? = null,
)

@Serializable
data class FlavorTextEntryDto(
    val flavor_text: String? = null,
    val language: NamedApiResourceDto? = null,
    val version: NamedApiResourceDto? = null
)
