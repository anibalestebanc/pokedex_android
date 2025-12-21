package com.github.pokemon.pokedex.domain.repository

interface CatalogRepository {
    suspend fun syncCatalog(): Result<Int>
}
