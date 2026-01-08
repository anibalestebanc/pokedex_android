package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.SpeciesDto

interface SpeciesRemoteDataSource {
    suspend fun getSpecies(idOrName: String): SpeciesDto
}
