package com.github.pokemon.pokedex.domain.repository

import kotlinx.coroutines.flow.Flow

interface HistorySearchRepository {
    fun getHistorySearch(limit: Int = 10): Flow<List<String>>
    suspend fun save(query: String): Result<Unit>
    suspend fun remove(query: String): Result<Unit>
    suspend fun clearAll(): Result<Unit>
}
