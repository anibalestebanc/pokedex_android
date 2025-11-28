package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class PokemonCatalogDto(
    val name: String,
    val url: String
)
