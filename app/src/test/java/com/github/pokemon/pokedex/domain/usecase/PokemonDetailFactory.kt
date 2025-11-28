package com.github.pokemon.pokedex.domain.usecase

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
