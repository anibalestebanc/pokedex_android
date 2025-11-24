package com.github.zsoltk.pokedex.data.datasource.mapper

import com.github.zsoltk.pokedex.core.database.entity.PokemonCatalogEntity
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonCatalogDto

fun PokemonCatalogDto.toEntity(): PokemonCatalogEntity {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return PokemonCatalogEntity(id = id, name = name, url = url)
}
