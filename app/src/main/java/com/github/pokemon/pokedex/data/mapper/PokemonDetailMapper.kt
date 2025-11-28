package com.github.pokemon.pokedex.data.mapper

import com.github.pokemon.pokedex.core.database.entity.PokemonDetailEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail
import com.github.pokemon.pokedex.domain.model.PokemonSpecies
import com.github.pokemon.pokedex.domain.model.PokemonSprites
import com.github.pokemon.pokedex.domain.model.Stat
import com.github.pokemon.pokedex.utils.PokeTimeUtils

fun PokemonDetailDto.toDomain(): PokemonDetail {
    val typesList = types.sortedBy { it.slot ?: Int.MAX_VALUE }.mapNotNull { it.type?.name }
    val abilitiesList = abilities.sortedBy { it.slot ?: Int.MAX_VALUE }.mapNotNull { it.ability?.name }
    val statsList = stats.mapNotNull { s ->
        val n = s.stat?.name ?: return@mapNotNull null
        Stat(name = n, value = s.base_stat ?: 0)
    }

    val spritesDomain = PokemonSprites(
        dreamWorld = sprites?.other?.dream_world?.front_default,
        home = sprites?.other?.home?.front_default,
        officialArtwork = sprites?.other?.official_artwork?.front_default,
        fallbackFront = sprites?.front_default
    )

    return PokemonDetail(
        id = id,
        name = name,
        imageUrl = spritesDomain.defaultImageUrl,
        sprites = spritesDomain,
        types = typesList,
        height = height ?: 0,
        weight = weight ?: 0,
        abilities = abilitiesList,
        stats = statsList,
        isFavorite = false,
        lastUpdated = PokeTimeUtils.getNow(),
    )
}

fun PokemonDetail.toEntity(): PokemonDetailEntity =
    PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        sprites = sprites,
        types = types,
        height = height,
        weight = weight,
        abilities = abilities,
        stats = stats,
        isFavorite = isFavorite,
        lastUpdated = lastUpdated,
    )

fun PokemonDetailEntity.toDomain(): PokemonDetail = PokemonDetail(
    id = id,
    name = name,
    imageUrl = imageUrl,
    sprites = sprites,
    types = types,
    height = height,
    weight = weight,
    abilities = abilities,
    stats = stats,
    isFavorite = isFavorite,
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
        imageUrl = sprites.defaultImageUrl,
        types = types,
        heightMeters = heightM,
        weightKg = weightKg,
        abilities = abilities,
        stats = stats,
        sprites = sprites,
        isFavorite = isFavorite,
        genera = species.genera,
        flavorText = species.flavorText,
        color = species.color,
        description = species.description,
        habitat = species.habitat,
        eggGroups = species.eggGroups,
        captureRate = species.captureRate,
        baseHappiness = species.baseHappiness,
        growthRate = species.growthRate,
        isLegendary = species.isLegendary,
        isMythical = species.isMythical,
    )
}



