package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import kotlinx.coroutines.flow.Flow

interface HistorySearchCacheDataSource {
    fun getLastTimeSearch(limit: Int): Flow<List<HistorySearchEntity>>
    suspend fun insertHistorySearch(entity: HistorySearchEntity): Long
    suspend fun updateLastTimeSearch(query: String, newTs: Long)
    suspend fun deleteByQuery(query: String)
    suspend fun clearAll()
}
