package com.github.zsoltk.pokedex.domain.repository

import com.github.zsoltk.pokedex.domain.model.PokemonSpecies

interface PokemonSpeciesRepository {
    suspend fun getPokemonSpecies(id: Int): Result<PokemonSpecies>
}
