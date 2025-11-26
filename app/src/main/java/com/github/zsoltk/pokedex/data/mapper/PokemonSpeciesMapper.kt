package com.github.zsoltk.pokedex.data.mapper

import com.github.zsoltk.pokedex.core.database.entity.PokemonSpeciesEntity
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonSpeciesDto
import com.github.zsoltk.pokedex.domain.model.PokemonSpecies
import com.github.zsoltk.pokedex.utils.PokeTimeUtils

private fun String.cleanFlavor(): String =
    this.replace("\n", " ").replace("\u000c", " ").replace("\\s+".toRegex(), " ").trim()

fun PokemonSpeciesDto.toDomain(preferredLang: String = "en"): PokemonSpecies {
    val genus = genera.firstOrNull { it.language?.name == preferredLang }?.genus
        ?: genera.firstOrNull()?.genus
    val flavor = flavor_text_entries.firstOrNull { it.language?.name == preferredLang }?.flavor_text
        ?: flavor_text_entries.firstOrNull()?.flavor_text
    return PokemonSpecies(
        id = id,
        name = name,
        genera = genus?.trim(),
        flavorText = flavor?.cleanFlavor(),
        description = form_descriptions.firstOrNull()?.description?.cleanFlavor(),
        color = color?.name,
        habitat = habitat?.name,
        eggGroups = egg_groups.mapNotNull { it.name },
        captureRate = capture_rate,
        baseHappiness = base_happiness,
        growthRate = growth_rate?.name,
        isLegendary = is_legendary,
        isMythical = is_mythical,
        lastUpdated = PokeTimeUtils.getNow()
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
