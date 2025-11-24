package com.github.zsoltk.pokedex.data.mapper

import com.github.zsoltk.pokedex.core.database.entity.PokemonCatalogEntity
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonCatalogDto
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog

fun PokemonCatalogDto.toEntity(): PokemonCatalogEntity {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return PokemonCatalogEntity(id = id, name = name, url = url)
}

fun PokemonCatalogEntity.toDomain(): PokemonCatalog =
    PokemonCatalog(id = id, name = name, url = url)


