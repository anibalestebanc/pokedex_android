package com.github.zsoltk.pokedex.data.repository

import com.github.zsoltk.pokedex.core.common.loggin.LoggerError
import com.github.zsoltk.pokedex.core.database.dao.PokemonSpeciesDao
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.zsoltk.pokedex.data.mapper.toDomain
import com.github.zsoltk.pokedex.data.mapper.toEntity
import com.github.zsoltk.pokedex.domain.model.PokemonSpecies
import com.github.zsoltk.pokedex.domain.repository.PokemonSpeciesRepository
import com.github.zsoltk.pokedex.utils.RefreshDueUtil.isRefreshDue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstPokemonSpeciesRepository(
    private val remoteDataSource: PokemonSpeciesRemoteDataSource,
    private val pokemonSpeciesDao: PokemonSpeciesDao,
) : PokemonSpeciesRepository {

    override suspend fun getPokemonSpecies(id : Int): Result<PokemonSpecies> = withContext(Dispatchers.IO){
        return@withContext try {
            pokemonSpeciesDao.getPokemonSpecies(id)?.let { speciesEntity ->
                if (!isRefreshDue(speciesEntity.lastUpdated)) {
                    Result.success(speciesEntity.toDomain())
                }
            }
            val pokemonSpecies = remoteDataSource.getSpecies(id.toString()).toDomain()
            pokemonSpeciesDao.insertReplace(pokemonSpecies.toEntity())
            Result.success(pokemonSpecies)
        } catch (e: Exception) {
            LoggerError.logError("Error getting pokemon species with id: $id", error = e)
            Result.failure(e)
        }
    }
}
