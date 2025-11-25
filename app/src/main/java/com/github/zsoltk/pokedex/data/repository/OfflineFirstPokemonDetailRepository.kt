package com.github.zsoltk.pokedex.data.repository

import com.github.zsoltk.pokedex.core.database.dao.PokemonDetailDao
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonDetailRemoteDataSource
import com.github.zsoltk.pokedex.data.mapper.toDomain
import com.github.zsoltk.pokedex.data.mapper.toEntity
import com.github.zsoltk.pokedex.domain.model.PokemonDetail
import com.github.zsoltk.pokedex.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class OfflineFirstPokemonDetailRepository(
    private val remoteDataSource: PokemonDetailRemoteDataSource,
    private val pokemonDetailDao: PokemonDetailDao
) : PokemonDetailRepository {

    override fun observePokemonDetail(id: Int): Flow<PokemonDetail?> =
        pokemonDetailDao.observePokemonDetail(id)
            .map { entity -> entity?.toDomain() }
            .onStart {
                if (pokemonDetailDao.getPokemonDetail(id) == null) {
                    getPokemonDetail(id.toString())
                }
            }


    override suspend fun getPokemonDetail(nameOrId: String): Result<PokemonDetail> = withContext(Dispatchers.IO){
        return@withContext try {
            val pokemonDetailDto = remoteDataSource.getPokemon(nameOrId)
            val pokemonDetail = pokemonDetailDto.toDomain()
            pokemonDetailDao.insertReplace(pokemonDetail.toEntity())
            Result.success(pokemonDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
