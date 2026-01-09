package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import com.github.pokemon.pokedex.data.datasource.cache.HistorySearchCacheDataSource
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomHistorySearchRepository(
    private val cacheDataSource: HistorySearchCacheDataSource,
    private val pokeTimeUtil: PokeTimeUtil,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : HistorySearchRepository {

    override fun observeHistorySearch(limit: Int): Flow<List<String>> =
        cacheDataSource.observeHistorySearch(limit).map { list ->
            list.map { it.query }
        }.flowOn(ioDispatcher)

    override suspend fun saveQuery(query: String): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            val now = pokeTimeUtil.now()
            val cacheQueryEntity = cacheDataSource.getQuery(query)
            if (cacheQueryEntity != null) {
                cacheDataSource.updateLastTimeQuery(query, now)
                return@runCatching
            }
            cacheDataSource.insertQuery(HistorySearchEntity(query = query, timestamp = now))
            Unit
        }
    }

    override suspend fun clearAll() = withContext(ioDispatcher) {
        runCatching {
            cacheDataSource.clearAll()
        }
    }
}
