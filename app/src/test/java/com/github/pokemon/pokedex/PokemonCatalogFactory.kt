package com.github.pokemon.pokedex

import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonCatalogDto
import com.github.pokemon.pokedex.domain.model.PokemonCatalog

val PikachuCatalog = PokemonCatalog(
    id = 1,
    name = "Pikachu",
    displayName = "Pikachu",
    url = "url",
)

val BulbasaurCatalog = PokemonCatalog(
    id = 2,
    name = "Bulbasaur",
    displayName = "Bulbasaur",
    url = "url",
)

val SquirtleCatalog = PokemonCatalog(
    id = 3,
    name = "Squirtle",
    displayName = "Squirtle",
    url = "url",
)

val PikachuDto = PokemonCatalogDto(
    name = "Pikachu",
    url = "https://pokeapi.co/api/v2/pokemon/1/",
)

val BulbasaurDto = PokemonCatalogDto(
    name = "Bulbasaur",
    url = "https://pokeapi.co/api/v2/pokemon/2/",
)

val SquirtleDto = PokemonCatalogDto(
    name = "Squirtle",
    url = "https://pokeapi.co/api/v2/pokemon/3/",
)
