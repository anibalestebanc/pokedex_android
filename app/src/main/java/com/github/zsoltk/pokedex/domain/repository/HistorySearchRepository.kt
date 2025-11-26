package com.github.zsoltk.pokedex.domain.repository

import kotlinx.coroutines.flow.Flow

interface HistorySearchRepository {
    fun getHistorySearch(limit: Int = 10): Flow<List<String>>
    suspend fun save(queryRaw: String, limit: Int = 10)
    suspend fun remove(query: String)
    suspend fun clearAll()
}
