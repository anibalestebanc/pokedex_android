package com.github.zsoltk.pokedex.data.mapper

import com.github.zsoltk.pokedex.core.database.entity.PokemonDetailEntity
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonDetailDto
import com.github.zsoltk.pokedex.domain.model.PokemonDetail
import com.github.zsoltk.pokedex.domain.model.PokemonFullDetail
import com.github.zsoltk.pokedex.domain.model.PokemonSpecies
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

fun PokemonDetail.combineWith(species: PokemonSpecies): PokemonFullDetail {
    val number = "#${id.toString().padStart(3, '0')}"
    val heightM = height / 10.0
    val weightKg = weight / 10.0

    return PokemonFullDetail(
        id = id,
        name = name,
        numberLabel = number,
        imageUrl = imageUrl,
        types = types,
        heightMeters = heightM,
        weightKg = weightKg,
        abilities = abilities,
        stats = stats,
        genera = species.genera,
        flavorText = species.flavorText,
        color = species.color,
        habitat = species.habitat,
        eggGroups = species.eggGroups,
        captureRate = species.captureRate,
        baseHappiness = species.baseHappiness,
        growthRate = species.growthRate,
        isLegendary = species.isLegendary,
        isMythical = species.isMythical,
    )
}



