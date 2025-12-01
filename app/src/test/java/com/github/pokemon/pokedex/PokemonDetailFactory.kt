package com.github.pokemon.pokedex

import com.github.pokemon.pokedex.core.database.entity.PokemonDetailEntity
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.SpritesDto
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.model.PokemonSprites

val Pikachu = PokemonDetail(
    id = 25,
    name = "Pikachu",
    isFavorite = true,
    imageUrl = "url",
    sprites = PokemonSprites(
        dreamWorld = null,
        home = null,
        officialArtwork = null,
        fallbackFront = null,
    ),
    types = listOf("Electric"),
    height = 10,
    weight = 10,
    abilities = listOf("Lightning Rod"),
    stats = listOf(),
    lastUpdated = 0,
)

val Charmander = PokemonDetail(
    id = 25,
    name = "Charmander",
    isFavorite = true,
    imageUrl = "url",
    sprites = PokemonSprites(
        dreamWorld = null,
        home = null,
        officialArtwork = null,
        fallbackFront = null,
    ),
    types = listOf("Fire"),
    height = 10,
    weight = 10,
    abilities = listOf("Lightning Rod"),
    stats = listOf(),
    lastUpdated = 0,
)

val Bulbasaur = PokemonDetail(
    id = 25,
    name = "Bulbasaur",
    isFavorite = true,
    imageUrl = "url",
    sprites = PokemonSprites(
        dreamWorld = null,
        home = null,
        officialArtwork = null,
        fallbackFront = null,
    ),
    types = listOf("Grass"),
    height = 10,
    weight = 10,
    abilities = listOf("Lightning Rod"),
    stats = listOf(),
    lastUpdated = 0,
)

val Squirtle = PokemonDetail(
    id = 25,
    name = "Squirtle",
    isFavorite = true,
    imageUrl = "url",
    sprites = PokemonSprites(
        dreamWorld = null,
        home = null,
        officialArtwork = null,
        fallbackFront = null,
    ),
    types = listOf("Water"),
    height = 10,
    weight = 10,
    abilities = listOf("Lightning Rod"),
    stats = listOf(),
    lastUpdated = 0,
)

val PikachuDetailEntity = PokemonDetailEntity(
    id = 1,
    name = "Pikachu",
    isFavorite = true,
    imageUrl = "url",
    sprites = PokemonSprites(
        dreamWorld = null,
        home = null,
        officialArtwork = null,
        fallbackFront = null,
    ),
    types = listOf("Electric"),
    height = 10,
    weight = 10,
    abilities = listOf("Lightning Rod"),
    stats = listOf(),
    lastUpdated = 0,
)

val CharmanderDetailEntity = PokemonDetailEntity(
    id = 2,
    name = "Charmander",
    isFavorite = false,
    imageUrl = "url",
    sprites = PokemonSprites(
        dreamWorld = null,
        home = null,
        officialArtwork = null,
        fallbackFront = null,
    ),
    types = listOf("Fire"),
    height = 10,
    weight = 10,
    abilities = listOf("Lightning Rod"),
    stats = listOf(),
    lastUpdated = 0,
)

val PikachuDetailDto = PokemonDetailDto(
    id = 1,
    name = "Pikachu",
    height = 10,
    weight = 10,
    abilities = emptyList(),
    types = emptyList(),
    stats = listOf(),
    sprites = SpritesDto()
)

val BulbasaurDetailDto = PokemonDetailDto(
    id = 2,
    name = "Bulbasaur",
    height = 10,
    weight = 10,
    abilities = emptyList(),
    types = emptyList(),
    stats = listOf(),
    sprites = SpritesDto()
)
