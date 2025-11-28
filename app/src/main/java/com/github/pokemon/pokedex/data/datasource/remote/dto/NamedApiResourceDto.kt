package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NamedApiResourceDto(
    val name: String? = null,
    val url: String? = null
)
