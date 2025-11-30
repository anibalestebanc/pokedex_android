package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.core.common.error.DatabaseOperationException
import com.github.pokemon.pokedex.core.common.loggin.LoggerError
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
    private val loggerError: LoggerError,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HistorySearchRepository {

    override fun getHistorySearch(limit: Int): Flow<List<String>> =
        historySearchCacheDataSource.getLastTimeSearch(limit).map { list -> list.map { it.query } }

    override suspend fun save(query: String) = withContext(coroutineDispatcher) {
        try {
            val q = normalizer(query)
            if (q.isBlank()) return@withContext
            val now = pokeTimeUtil.now()
            val insertedId = historySearchCacheDataSource.insertHistorySearch(
                    entity = HistorySearchEntity(query = q, timestamp = now)
                )
            if (insertedId == -1L) {
                historySearchCacheDataSource.updateLastTimeSearch(q, now)
            }
        } catch (e: Exception) {
            val error = DatabaseOperationException(message = "Error saving search query: $query", cause = e)
            loggerError.logError(error = error)
        }
    }

    override suspend fun remove(query: String) {
        val q = normalizer(query)
        historySearchCacheDataSource.deleteByQuery(q)
    }

    override suspend fun clearAll() = historySearchCacheDataSource.clearAll()

    private fun normalizer(queryRaw: String): String = queryRaw.trim().lowercase()
}
