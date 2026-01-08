package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CatalogDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)
