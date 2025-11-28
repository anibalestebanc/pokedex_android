package com.github.pokemon.pokedex.domain.repository

import com.github.pokemon.pokedex.domain.model.PokemonSpecies

interface PokemonSpeciesRepository {
    suspend fun getPokemonSpecies(id: Int): Result<PokemonSpecies>
}
