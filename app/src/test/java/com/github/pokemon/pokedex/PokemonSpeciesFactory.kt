package com.github.pokemon.pokedex

import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.FlavorTextEntryDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.GenusDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.ApiResourceDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.SpeciesDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.SpeciesVarietyDto
import com.github.pokemon.pokedex.domain.model.PokemonSpecies


val PikachuSpecies = PokemonSpecies(
    id = 1,
    name = "Pikachu",
    flavorText = "This is a description",
    genera = "Pikachu",
    color = "Yellow",
    description = "This is a description",
    habitat = "Forest",
    eggGroups = listOf("Monster"),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = "Fast",
    isLegendary = false,
    isMythical = false,
    lastUpdated = 0,
)

val CharmanderSpecies = PokemonSpecies(
    id = 2,
    name = "Charmander",
    flavorText = "This is a description",
    genera = "Pikachu",
    color = "Yellow",
    description = "This is a description",
    habitat = "Forest",
    eggGroups = listOf("Monster"),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = "Fast",
    isLegendary = false,
    isMythical = false,
    lastUpdated = 0,
)

val BulbasaurSpecies = PokemonSpecies(
    id = 3,
    name = "Bulbasaur",
    flavorText = "This is a description",
    genera = "Pikachu",
    color = "Yellow",
    description = "This is a description",
    habitat = "Forest",
    eggGroups = listOf("Monster"),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = "Fast",
    isLegendary = false,
    isMythical = false,
    lastUpdated = 0,
)

val PikachuSpeciesDto = SpeciesDto(
    id = 1,
    name = "Pikachu",
    flavorTextEntries = listOf(FlavorTextEntryDto(
        flavorText = "This is a description")
    ),
    genera = listOf(GenusDto(
        genus = "Pikachu",
        language = null
    )),
    color = null,
    habitat = null,
    eggGroups = listOf(ApiResourceDto(
        name = "Monster",
        url = "https://pokeapi.co/api/v2/egg-group/1/"
    )),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = null,
    isLegendary = false,
    isMythical = false,
    varieties = listOf(SpeciesVarietyDto
        (isDefault = true, pokemon = null)
    )
)

val CharmanderSpeciesDto = SpeciesDto(
    id = 2,
    name = "Charmander",
    flavorTextEntries = listOf(FlavorTextEntryDto(
        flavorText = "This is a description")
    ),
    genera = listOf(GenusDto(
        genus = "Charmander",
        language = null
    )),
    color = null,
    habitat = null,
    eggGroups = listOf(ApiResourceDto(
        name = "Monster",
        url = "https://pokeapi.co/api/v2/egg-group/1/"
    )),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = null,
    isLegendary = false,
    isMythical = false,
    varieties = listOf(SpeciesVarietyDto
        (isDefault = true, pokemon = null)
    )
)

val PikachuSpeciesEntity = PokemonSpeciesEntity(
    id = 1,
    name = "Pikachu",
    flavorText = "This is a description",
    genera = "Pikachu",
    color = "Yellow",
    description = "This is a description",
    habitat = "Forest",
    eggGroups = listOf("Monster"),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = "Fast",
    isLegendary = false,
    isMythical = false,
    lastUpdated = 0,
)

val CharmanderSpeciesEntity = PokemonSpeciesEntity(
    id = 2,
    name = "Charmander",
    flavorText = "This is a description",
    genera = "Pikachu",
    color = "Yellow",
    description = "This is a description",
    habitat = "Forest",
    eggGroups = listOf("Monster"),
    captureRate = 1,
    baseHappiness = 1,
    growthRate = "Fast",
    isLegendary = false,
    isMythical = false,
    lastUpdated = 0,
)
