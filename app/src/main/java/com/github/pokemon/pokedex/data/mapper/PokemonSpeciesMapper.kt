package com.github.pokemon.pokedex.data.mapper

import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.SpeciesDto
import com.github.pokemon.pokedex.domain.model.PokemonSpecies
import com.github.pokemon.pokedex.utils.PokeTimeUtil

private fun String.cleanFlavor(): String =
    this.replace("\n", " ").replace("\u000c", " ").replace("\\s+".toRegex(), " ").trim()

fun SpeciesDto.toDomain(pokeTimeUtil: PokeTimeUtil, preferredLang: String = "en"): PokemonSpecies {
    val genus = genera.firstOrNull { it.language?.name == preferredLang }?.genus
        ?: genera.firstOrNull()?.genus
    val flavor = flavorTextEntries.firstOrNull { it.language?.name == preferredLang }?.flavorText
        ?: flavorTextEntries.firstOrNull()?.flavorText
    return PokemonSpecies(
        id = id,
        name = name,
        genera = genus?.trim(),
        flavorText = flavor?.cleanFlavor(),
        description = formDescriptions.firstOrNull()?.description?.cleanFlavor(),
        color = color?.name,
        habitat = habitat?.name,
        eggGroups = eggGroups.mapNotNull { it.name },
        captureRate = captureRate,
        baseHappiness = baseHappiness,
        growthRate = growthRate?.name,
        isLegendary = isLegendary,
        isMythical = isMythical,
        lastUpdated = pokeTimeUtil.now()
    )
}

fun PokemonSpecies.toEntity(): PokemonSpeciesEntity = PokemonSpeciesEntity(
    id, name, genera, flavorText, description, color, habitat, eggGroups, captureRate, baseHappiness,
    growthRate, isLegendary, isMythical, lastUpdated
)

fun PokemonSpeciesEntity.toDomain(): PokemonSpecies = PokemonSpecies(
    id, name, genera, flavorText, description,color, habitat, eggGroups, captureRate, baseHappiness,
    growthRate, isLegendary, isMythical, lastUpdated
)
