package com.github.zsoltk.pokedex.data.datasource.remote.dto

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
    val capture_rate: Int? = null,
    val base_happiness: Int? = null,
    val growth_rate: NamedApiResourceDto? = null,
    val is_legendary: Boolean = false,
    val is_mythical: Boolean = false
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
