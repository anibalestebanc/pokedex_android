package com.github.pokemon.pokedex.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class PokemonCatalogResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonCatalogDto>,
)


