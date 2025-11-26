package com.github.zsoltk.pokedex.data.repository

import com.github.zsoltk.pokedex.core.database.dao.PokemonSpeciesDao
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.zsoltk.pokedex.data.mapper.toDomain
import com.github.zsoltk.pokedex.data.mapper.toEntity
import com.github.zsoltk.pokedex.domain.model.PokemonSpecies
import com.github.zsoltk.pokedex.domain.repository.PokemonSpeciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstPokemonSpeciesRepository(
    private val remoteDataSource: PokemonSpeciesRemoteDataSource,
    private val pokemonSpeciesDao: PokemonSpeciesDao,
) : PokemonSpeciesRepository {

    override suspend fun getPokemonSpecies(idOrName: String): Result<PokemonSpecies> = withContext(Dispatchers.IO){
        return@withContext try {
            val id = idOrName.toIntOrNull()
            if (id != null) {
                pokemonSpeciesDao.getPokemonSpecies(id)?.let { speciesEntity ->
                    Result.success(speciesEntity.toDomain())
                }
            }
            val pokemonSpecies = remoteDataSource.getSpecies(idOrName).toDomain()
            pokemonSpeciesDao.insertReplace(pokemonSpecies.toEntity())
            Result.success(pokemonSpecies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
