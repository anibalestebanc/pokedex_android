package com.github.pokemon.pokedex.data.mapper

import com.github.pokemon.pokedex.core.database.entity.PokemonCatalogEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonCatalogDto
import com.github.pokemon.pokedex.domain.model.PokemonCatalog

fun PokemonCatalogDto.toEntity(): PokemonCatalogEntity {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    val displayName = name.replace("-", " ")
    return PokemonCatalogEntity(id = id, name = name, displayName = displayName, url = url)
}

fun PokemonCatalogEntity.toDomain(): PokemonCatalog =
    PokemonCatalog(id = id, name = name, displayName = displayName, url = url)


