package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.DetailRemoteDataSource
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

class OfflineFirstDetailRepository(
    private val remoteDataSource: DetailRemoteDataSource,
    private val cacheDataSource: DetailCacheDataSource,
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
            val detailEntity = cacheDataSource.getDetail(id)
            if (detailEntity != null && !refreshDueUtil.isRefreshDue(detailEntity.lastUpdated)) {
                return@withContext Result.success(detailEntity.toDomain())
            }
            val remoteDetailDto = remoteDataSource.getDetail(id.toString())
            val remotePokemon = remoteDetailDto.toDomain(pokeTimeUtil)
            cacheDataSource.insertDetail(remotePokemon.toEntity())
            Result.success(remotePokemon)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setFavorite(id: Int, favorite: Boolean): Result<Unit> = withContext(coroutineDispatcher) {
        return@withContext try {
            cacheDataSource.setFavorite(id, favorite)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(id: Int): Result<Unit> = withContext(coroutineDispatcher) {
        return@withContext try {
            val detailEntity = cacheDataSource.getDetail(id) ?: throw NotFoundException("Pokemon not found with id : $id")
            val newValue = !detailEntity.isFavorite
            cacheDataSource.setFavorite(id, newValue)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
