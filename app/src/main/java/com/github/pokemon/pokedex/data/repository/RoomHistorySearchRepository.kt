package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import com.github.pokemon.pokedex.data.datasource.cache.HistorySearchCacheDataSource
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomHistorySearchRepository(
    private val historySearchCacheDataSource: HistorySearchCacheDataSource,
    private val pokeTimeUtil: PokeTimeUtil,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : HistorySearchRepository {

    override fun getHistorySearch(limit: Int): Flow<List<String>> =
        historySearchCacheDataSource.getLastTimeSearch(limit).map { list -> list.map { it.query } }

    override suspend fun save(query: String): Result<Unit> = withContext(coroutineDispatcher) {
       return@withContext try {
            val q = normalizer(query)
            if (q.isBlank()) return@withContext Result.failure(Exception("Query is blank"))
            val now = pokeTimeUtil.now()
            val insertedId = historySearchCacheDataSource.insertHistorySearch(
                entity = HistorySearchEntity(query = q, timestamp = now),
            )
            if (insertedId == -1L) {
                historySearchCacheDataSource.updateLastTimeSearch(q, now)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun remove(query: String): Result<Unit> = withContext(coroutineDispatcher) {
        return@withContext try {
            val q = normalizer(query)
            historySearchCacheDataSource.deleteByQuery(q)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearAll() = withContext(coroutineDispatcher) {
        return@withContext try {
            historySearchCacheDataSource.clearAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun normalizer(queryRaw: String): String = queryRaw.trim().lowercase()
}
