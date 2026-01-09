package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.dao.HistorySearchDao
import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import kotlinx.coroutines.flow.Flow

class RoomHistorySearchCacheDataSource(
    private val historySearchDao: HistorySearchDao,
) : HistorySearchCacheDataSource {
    override fun observeHistorySearch(limit: Int): Flow<List<HistorySearchEntity>> =
        historySearchDao.getLastSearch(limit)

    override suspend fun insertQuery(entity: HistorySearchEntity): Long =
        historySearchDao.insertIgnore(entity)

    override suspend fun updateLastTimeQuery(query: String, newTs: Long) =
        historySearchDao.updateTimestamp(query, newTs)

    override suspend fun getQuery(query: String): HistorySearchEntity? =
        historySearchDao.getQuery(query)

    override suspend fun deleteQuery(query: String) =
        historySearchDao.deleteByQuery(query)

    override suspend fun clearAll() = historySearchDao.clearAll()
}
