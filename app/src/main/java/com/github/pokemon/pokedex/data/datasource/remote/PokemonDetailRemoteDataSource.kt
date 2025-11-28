package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto

interface PokemonDetailRemoteDataSource {
    suspend fun getPokemon(idOrName: String): PokemonDetailDto
}
