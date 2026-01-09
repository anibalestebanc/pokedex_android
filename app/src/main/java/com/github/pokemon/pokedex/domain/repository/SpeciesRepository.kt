package com.github.pokemon.pokedex.domain.repository

import com.github.pokemon.pokedex.domain.model.PokemonSpecies

interface SpeciesRepository {
    suspend fun getSpecies(id: Int): Result<PokemonSpecies>
}
