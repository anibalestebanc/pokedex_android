package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository

class SaveHistorySearchUseCase(
    private val repository : HistorySearchRepository
) {
    suspend operator fun invoke(query: String): Result<Unit> {
        val q = normalizeQuery(query)
        if (q.isBlank()) {
            Result.failure<Unit>(IllegalArgumentException("Query is blank"))
        }
        return repository.saveQuery(query)
    }

    private fun normalizeQuery(query: String): String = query.trim().lowercase()
}
