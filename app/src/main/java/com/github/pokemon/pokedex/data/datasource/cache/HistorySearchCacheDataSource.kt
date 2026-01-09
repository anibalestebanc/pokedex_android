package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import kotlinx.coroutines.flow.Flow

interface HistorySearchCacheDataSource {
    fun observeHistorySearch(limit: Int): Flow<List<HistorySearchEntity>>
    suspend fun insertQuery(entity: HistorySearchEntity): Long
    suspend fun updateLastTimeQuery(query: String, newTs: Long)
    suspend fun getQuery(query: String): HistorySearchEntity?
    suspend fun deleteQuery(query: String)
    suspend fun clearAll()
}
