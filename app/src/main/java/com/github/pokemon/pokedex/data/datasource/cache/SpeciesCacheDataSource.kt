package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity

interface SpeciesCacheDataSource {
    suspend fun getSpecieById(id: Int): PokemonSpeciesEntity?
    suspend fun insertSpecie(entity: PokemonSpeciesEntity)
}
