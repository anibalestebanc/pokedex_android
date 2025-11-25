package com.github.zsoltk.pokedex.data.repository

import com.github.zsoltk.pokedex.core.common.error.DatabaseOperationException
import com.github.zsoltk.pokedex.core.common.loggin.LoggerError
import com.github.zsoltk.pokedex.core.database.dao.HistorySearchDao
import com.github.zsoltk.pokedex.core.database.entity.HistorySearchEntity
import com.github.zsoltk.pokedex.domain.repository.HistorySearchRepository
import com.github.zsoltk.pokedex.utils.PokeTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomHistorySearchRepository(
    private val searchHistoryDao: HistorySearchDao,
) : HistorySearchRepository {

    override fun getHistorySearch(limit: Int): Flow<List<String>> =
        searchHistoryDao.getLastSearch(limit).map { list -> list.map { it.query } }

    override suspend fun save(queryRaw: String, limit: Int) = withContext(Dispatchers.IO) {
        try {
            val q = normalizer(queryRaw)
            if (q.isBlank()) return@withContext
            val now = PokeTimeUtils.getNow()
            val insertedId = searchHistoryDao.insertIgnore(HistorySearchEntity(query = q, timestamp = now))
            if (insertedId == -1L) {
                searchHistoryDao.updateTimestamp(q, now)
            }
        } catch (e: Exception) {
            val error = DatabaseOperationException(message = "Error saving search query: $queryRaw", cause = e)
            LoggerError.logError(error = error)
        }
    }

    override suspend fun remove(query: String) {
        val q = normalizer(query)
        searchHistoryDao.deleteByQuery(q)
    }

    override suspend fun clear() = searchHistoryDao.clearAll()

    private fun normalizer(queryRaw: String): String = queryRaw.trim().lowercase()
}
