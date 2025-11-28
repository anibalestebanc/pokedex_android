package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.common.error.NotFoundException
import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.core.database.dao.PokemonDetailDao
import com.github.pokemon.pokedex.data.datasource.remote.PokemonDetailRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.utils.RefreshDueUtil.isRefreshDue
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
                    getPokemonDetail(id)
                }
            }

    override fun observeFavorites(): Flow<List<PokemonDetail>> =
        pokemonDetailDao.observeFavorites()
            .map { entities -> entities.map { it.toDomain() } }

    override fun observeIsFavorite(id: Int): Flow<Boolean> = pokemonDetailDao.observeIsFavorite(id)

    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail> = withContext(Dispatchers.IO) {
        return@withContext try {
            pokemonDetailDao.getPokemonDetail(id)?.let { detailEntity ->
                if (!isRefreshDue(detailEntity.lastUpdated)) {
                    return@withContext Result.success(detailEntity.toDomain())
                }
            }
            val remotePokemonDto = remoteDataSource.getPokemon(id.toString())
            val remotePokemon = remotePokemonDto.toDomain()
            pokemonDetailDao.insertReplace(remotePokemon.toEntity())
            Result.success(remotePokemon)
        } catch (e: Exception) {
            LoggerError.logError("Error getting pokemon detail with id: $id", error = e)
            Result.failure(e)
        }
    }

    override suspend fun setFavorite(id: Int, favorite: Boolean): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            pokemonDetailDao.setFavorite(id, favorite)
            Result.success(Unit)
        } catch (e: Exception) {
            LoggerError.logError("Error setting favorite with id: $id", error = e)
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
            LoggerError.logError("Error toggling favorite with id: $id", error = e)
            Result.failure(e)
        }
    }
}
