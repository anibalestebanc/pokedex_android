package com.github.zsoltk.pokedex.data.repository

import com.github.zsoltk.pokedex.core.common.error.NotFoundException
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
    private val pokemonDetailDao: PokemonDetailDao,
) : PokemonDetailRepository {

    override fun observePokemonDetail(id: Int): Flow<PokemonDetail?> =
        pokemonDetailDao.observePokemonDetail(id)
            .map { entity -> entity?.toDomain() }
            .onStart {
                if (pokemonDetailDao.getPokemonDetail(id) == null) {
                    getPokemonDetail(id.toString())
                }
            }

    override fun observeFavorites(): Flow<List<PokemonDetail>> =
        pokemonDetailDao.observeFavorites()
            .map { entities -> entities.map { it.toDomain() } }

    override fun observeIsFavorite(id: Int): Flow<Boolean> = pokemonDetailDao.observeIsFavorite(id)

    override suspend fun getPokemonDetail(nameOrId: String): Result<PokemonDetail> = withContext(Dispatchers.IO) {
        return@withContext try {
            val pokemonDetailDto = remoteDataSource.getPokemon(nameOrId)
            val pokemonDetail = pokemonDetailDto.toDomain()
            pokemonDetailDao.insertReplace(pokemonDetail.toEntity())
            Result.success(pokemonDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setFavorite(id: Int, favorite: Boolean): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            pokemonDetailDao.setFavorite(id, favorite)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(id: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        return@withContext try {
            val entity = pokemonDetailDao.getPokemonDetail(id)
            if (entity == null) {
                throw NotFoundException("Pokemon not found id : $id")
            }
            val newValue = !entity.isFavorite
            pokemonDetailDao.setFavorite(id, newValue)
            Result.success(newValue)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
