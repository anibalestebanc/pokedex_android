package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResourceDto(
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null
)
