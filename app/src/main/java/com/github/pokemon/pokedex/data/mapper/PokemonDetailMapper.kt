package com.github.pokemon.pokedex.data.mapper

import com.github.pokemon.pokedex.core.database.entity.PokemonDetailEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.DetailDto
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail
import com.github.pokemon.pokedex.domain.model.PokemonSpecies
import com.github.pokemon.pokedex.domain.model.PokemonSprites
import com.github.pokemon.pokedex.domain.model.PokemonStat
import com.github.pokemon.pokedex.utils.PokeTimeUtil

private const val ZERO_VALUE = 0
private const val MAX_PERCENTAGE = 10.0
private const val HUNDRED_LENGTH = 3

fun DetailDto.toDomain(pokeTimeUtil: PokeTimeUtil): PokemonDetail {
    val typesList = types.sortedBy { it.slot ?: Int.MAX_VALUE }.mapNotNull { it.type?.name }
    val abilitiesList = abilities.sortedBy { it.slot ?: Int.MAX_VALUE }.mapNotNull { it.ability?.name }
    val statsList = stats.mapNotNull { s ->
        val n = s.stat?.name ?: return@mapNotNull null
        PokemonStat(name = n, value = s.baseStat ?: ZERO_VALUE)
    }

    val spritesDomain = PokemonSprites(
        dreamWorld = sprites?.other?.dreamWorld?.frontDefault,
        home = sprites?.other?.home?.frontDefault,
        officialArtwork = sprites?.other?.officialArtwork?.frontDefault,
        fallbackFront = sprites?.frontDefault
    )

    return PokemonDetail(
        id = id,
        name = name,
        imageUrl = spritesDomain.defaultImageUrl,
        sprites = spritesDomain,
        types = typesList,
        height = height ?: ZERO_VALUE,
        weight = weight ?: ZERO_VALUE,
        abilities = abilitiesList,
        stats = statsList,
        isFavorite = false,
        lastUpdated = pokeTimeUtil.now(),
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
    val number = "#${id.toString().padStart(HUNDRED_LENGTH, '0')}"
    val heightM = height / MAX_PERCENTAGE
    val weightKg = weight / MAX_PERCENTAGE
    val normalizeAbilities = abilities.map { it.replace("-", " ") }

    return PokemonFullDetail(
        id = id,
        name = name,
        numberLabel = number,
        imageUrl = sprites.defaultImageUrl,
        types = types,
        heightMeters = heightM,
        weightKg = weightKg,
        abilities = normalizeAbilities,
        stats = stats,
        sprites = sprites,
        isFavorite = isFavorite,
        genera = species.genera,
        flavorText = species.flavorText,
        color = species.color,
        description = species.description,
        habitat = species.habitat,
        eggGroups = species.eggGroups,
        captureRate = species.captureRate ?: ZERO_VALUE,
        baseHappiness = species.baseHappiness ?: ZERO_VALUE,
        growthRate = species.growthRate ?: "-",
        isLegendary = species.isLegendary,
        isMythical = species.isMythical,
    )
}



