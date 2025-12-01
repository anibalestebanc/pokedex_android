package com.github.pokemon.pokedex

import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.FlavorTextEntryDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.GenusDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.NamedApiResourceDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonSpeciesDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonSpeciesVarietyDto
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

val PikachuSpeciesDto = PokemonSpeciesDto(
    id = 1,
    name = "Pikachu",
    flavor_text_entries = listOf(FlavorTextEntryDto(
        flavor_text = "This is a description")
    ),
    genera = listOf(GenusDto(
        genus = "Pikachu",
        language = null
    )),
    color = null,
    habitat = null,
    egg_groups = listOf(NamedApiResourceDto(
        name = "Monster",
        url = "https://pokeapi.co/api/v2/egg-group/1/"
    )),
    capture_rate = 1,
    base_happiness = 1,
    growth_rate = null,
    is_legendary = false,
    is_mythical = false,
    varieties = listOf(PokemonSpeciesVarietyDto
        (is_default = true, pokemon = null)
    )
)

val CharmanderSpeciesDto = PokemonSpeciesDto(
    id = 2,
    name = "Charmander",
    flavor_text_entries = listOf(FlavorTextEntryDto(
        flavor_text = "This is a description")
    ),
    genera = listOf(GenusDto(
        genus = "Charmander",
        language = null
    )),
    color = null,
    habitat = null,
    egg_groups = listOf(NamedApiResourceDto(
        name = "Monster",
        url = "https://pokeapi.co/api/v2/egg-group/1/"
    )),
    capture_rate = 1,
    base_happiness = 1,
    growth_rate = null,
    is_legendary = false,
    is_mythical = false,
    varieties = listOf(PokemonSpeciesVarietyDto
        (is_default = true, pokemon = null)
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
