package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.core.database.dao.PokemonSpeciesDao
import com.github.pokemon.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.model.PokemonSpecies
import com.github.pokemon.pokedex.domain.repository.PokemonSpeciesRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstPokemonSpeciesRepository(
    private val remoteDataSource: PokemonSpeciesRemoteDataSource,
    private val pokemonSpeciesDao: PokemonSpeciesDao,
    private val pokeTimeUtil: PokeTimeUtil,
    private val refreshDueUtil: RefreshDueUtil
) : PokemonSpeciesRepository {

    override suspend fun getPokemonSpecies(id : Int): Result<PokemonSpecies> = withContext(Dispatchers.IO){
        return@withContext try {
            pokemonSpeciesDao.getPokemonSpecies(id)?.let { speciesEntity ->
                if (!refreshDueUtil.isRefreshDue(speciesEntity.lastUpdated)) {
                    Result.success(speciesEntity.toDomain())
                }
            }
            val pokemonSpecies = remoteDataSource.getSpecies(id.toString()).toDomain(pokeTimeUtil)
            pokemonSpeciesDao.insertReplace(pokemonSpecies.toEntity())
            Result.success(pokemonSpecies)
        } catch (e: Exception) {
            LoggerError.logError("Error getting pokemon species with id: $id", error = e)
            Result.failure(e)
        }
    }
}
