package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonSpeciesDto

interface PokemonSpeciesRemoteDataSource {
    suspend fun getSpecies(idOrName: String): PokemonSpeciesDto
}
