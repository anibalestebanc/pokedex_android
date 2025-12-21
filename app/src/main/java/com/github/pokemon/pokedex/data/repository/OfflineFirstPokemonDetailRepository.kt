package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonDetailRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.exception.PokeException.NotFoundException
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class OfflineFirstPokemonDetailRepository(
    private val remoteDataSource: PokemonDetailRemoteDataSource,
    private val cacheDataSource: DetailCacheDataSource,
    private val loggerError: LoggerError,
    private val pokeTimeUtil: PokeTimeUtil,
    private val refreshDueUtil: RefreshDueUtil,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokemonDetailRepository {

    override fun observePokemonDetail(id: Int): Flow<PokemonDetail?> =
        cacheDataSource.observeDetail(id)
            .map { entity -> entity?.toDomain() }
            .onStart {
                if (cacheDataSource.getDetail(id) == null) {
                    getPokemonDetail(id)
                }
            }

    override fun observeFavorites(): Flow<List<PokemonDetail>> =
        cacheDataSource.observeFavorites()
            .map { entities -> entities.map { it.toDomain() } }

    override fun observeIsFavorite(id: Int): Flow<Boolean> = cacheDataSource.observeIsFavorite(id)

    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail> = withContext(coroutineDispatcher) {
        return@withContext try {
            cacheDataSource.getDetail(id)?.let { detailEntity ->
                if (!refreshDueUtil.isRefreshDue(detailEntity.lastUpdated)) {
                    return@withContext Result.success(detailEntity.toDomain())
                }
            }
            val remotePokemonDto = remoteDataSource.getPokemon(id.toString())
            val remotePokemon = remotePokemonDto.toDomain(pokeTimeUtil)
            cacheDataSource.insertDetail(remotePokemon.toEntity())
            Result.success(remotePokemon)
        } catch (e: Exception) {
            loggerError.logError("Error getting pokemon detail with id: $id", error = e)
            Result.failure(e)
        }
    }

    override suspend fun setFavorite(id: Int, favorite: Boolean): Result<Unit> = withContext(coroutineDispatcher) {
        return@withContext try {
            cacheDataSource.setFavorite(id, favorite)
            Result.success(Unit)
        } catch (e: Exception) {
            loggerError.logError("Error setting favorite with id: $id", error = e)
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(id: Int): Result<Boolean> = withContext(coroutineDispatcher) {
        return@withContext try {
            val entity = cacheDataSource.getDetail(id) ?: throw NotFoundException("Pokemon not found id : $id")
            val newValue = !entity.isFavorite
            cacheDataSource.setFavorite(id, newValue)
            Result.success(newValue)
        } catch (e: Exception) {
            loggerError.logError("Error toggling favorite with id: $id", error = e)
            Result.failure(e)
        }
    }
}
