package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.domain.exception.PokeException.NotFoundException
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultFavoriteRepository(
    private val cacheDataSource: DetailCacheDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavoriteRepository {
    override fun observeIsFavorite(id: Int): Flow<Boolean> =
        cacheDataSource.observeIsFavorite(id)

    override fun observeFavorites(): Flow<List<PokemonDetail>> =
        cacheDataSource.observeFavorites()
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(ioDispatcher)

    override suspend fun saveFavorite(id: Int, favorite: Boolean): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                cacheDataSource.setFavorite(id, favorite)
            }
        }

    override suspend fun toggleFavorite(id: Int): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                val detailEntity =
                    cacheDataSource.getDetail(id) ?: throw NotFoundException("Pokemon not found with id : $id")
                val newValue = !detailEntity.isFavorite
                cacheDataSource.setFavorite(id, newValue)
            }
        }
}
