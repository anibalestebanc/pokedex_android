package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.dao.PokemonSpeciesDao
import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity

class RoomSpeciesCacheDataSource(
    private val speciesDao: PokemonSpeciesDao,
) : SpeciesCacheDataSource {
    override suspend fun getSpecieById(id: Int): PokemonSpeciesEntity? = speciesDao.getPokemonSpecies(id)

    override suspend fun insertSpecie(entity: PokemonSpeciesEntity) = speciesDao.insertReplace(entity)
}
