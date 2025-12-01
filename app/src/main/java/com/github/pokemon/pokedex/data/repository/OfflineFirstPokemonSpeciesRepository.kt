package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.data.datasource.cache.SpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.model.PokemonSpecies
import com.github.pokemon.pokedex.domain.repository.PokemonSpeciesRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstPokemonSpeciesRepository(
    private val remoteDataSource: PokemonSpeciesRemoteDataSource,
    private val speciesCacheDataSource: SpeciesCacheDataSource,
    private val loggerError: LoggerError,
    private val pokeTimeUtil: PokeTimeUtil,
    private val refreshDueUtil: RefreshDueUtil,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PokemonSpeciesRepository {

    override suspend fun getPokemonSpecies(id: Int): Result<PokemonSpecies> = withContext(coroutineDispatcher) {
        return@withContext try {
            speciesCacheDataSource.getSpecieById(id)?.let { speciesEntity ->
                if (!refreshDueUtil.isRefreshDue(speciesEntity.lastUpdated)) {
                   return@withContext Result.success(speciesEntity.toDomain())
                }
            }
            val pokemonSpecies = remoteDataSource.getSpecies(id.toString()).toDomain(pokeTimeUtil)
            speciesCacheDataSource.insertSpecie(pokemonSpecies.toEntity())
            Result.success(pokemonSpecies)
        } catch (e: Exception) {
            loggerError.logError("Error getting pokemon species with id: $id", error = e)
            Result.failure(e)
        }
    }
}
