package com.github.zsoltk.pokedex.data.mapper

import com.github.zsoltk.pokedex.core.database.entity.PokemonDetailEntity
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonDetailDto
import com.github.zsoltk.pokedex.domain.model.PokemonDetail
import com.github.zsoltk.pokedex.domain.model.Stat
import com.github.zsoltk.pokedex.utils.PokeTimeUtils

fun PokemonDetailDto.toDomain(): PokemonDetail {
    val typesList = types.sortedBy { it.slot ?: Int.MAX_VALUE }.mapNotNull { it.type?.name }
    val abilitiesList = abilities.sortedBy { it.slot ?: Int.MAX_VALUE }.mapNotNull { it.ability?.name }
    val statsList = stats.mapNotNull { s ->
        val n = s.stat?.name ?: return@mapNotNull null
        Stat(name = n, value = s.base_stat ?: 0)
    }
    val official = sprites?.other?.get("official-artwork")?.front_default
    val dream = sprites?.other?.get("dream_world")?.front_default
    val front = sprites?.front_default
    val img = official ?: dream ?: front

    return PokemonDetail(
        id = id,
        name = name,
        imageUrl = img,
        types = typesList,
        height = height ?: 0,
        weight = weight ?: 0,
        abilities = abilitiesList,
        stats = statsList,
        lastUpdated = PokeTimeUtils.getNow(),
    )
}

fun PokemonDetail.toEntity(): PokemonDetailEntity =
    PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        types = types,
        height = height,
        weight = weight,
        abilities = abilities,
        stats = stats,
        lastUpdated = lastUpdated,
    )

fun PokemonDetailEntity.toDomain(): PokemonDetail = PokemonDetail(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = types,
    height = height,
    weight = weight,
    abilities = abilities,
    stats = stats,
    lastUpdated = lastUpdated,
)



