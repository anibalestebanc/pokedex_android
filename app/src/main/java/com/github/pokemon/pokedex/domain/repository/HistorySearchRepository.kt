package com.github.pokemon.pokedex.domain.repository

import kotlinx.coroutines.flow.Flow

interface HistorySearchRepository {
    fun observeHistorySearch(limit: Int = 10): Flow<List<String>>
    suspend fun saveQuery(query: String): Result<Unit>
    suspend fun clearAll(): Result<Unit>
}
