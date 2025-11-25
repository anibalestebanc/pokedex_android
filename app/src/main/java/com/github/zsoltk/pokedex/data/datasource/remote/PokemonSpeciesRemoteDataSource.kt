package com.github.zsoltk.pokedex.data.datasource.remote

import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonSpeciesDto

interface PokemonSpeciesRemoteDataSource {
    suspend fun getSpecies(idOrName: String): PokemonSpeciesDto
}
