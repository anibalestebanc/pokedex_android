package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.cache.SpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.SpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.model.PokemonSpecies
import com.github.pokemon.pokedex.domain.repository.SpeciesRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstSpeciesRepository(
    private val remoteDataSource: SpeciesRemoteDataSource,
    private val cacheDataSource: SpeciesCacheDataSource,
    private val pokeTimeUtil: PokeTimeUtil,
    private val refreshDueUtil: RefreshDueUtil,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SpeciesRepository {

    override suspend fun getSpecies(id: Int): Result<PokemonSpecies> =
        withContext(ioDispatcher) {
            runCatching {
                val speciesEntity = cacheDataSource.getSpecieById(id)
                if (speciesEntity != null && !refreshDueUtil.isRefreshDue(speciesEntity.lastUpdated)) {
                    return@runCatching speciesEntity.toDomain()
                }
                val speciesDto = remoteDataSource.getSpecies(id.toString())
                val species = speciesDto.toDomain(pokeTimeUtil)
                cacheDataSource.insertSpecie(species.toEntity())
                species
            }
        }
}
