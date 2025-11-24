package com.github.zsoltk.pokedex.domain.repository

interface PokemonCatalogRepository {
    suspend fun getPokemonCatalog(force: Boolean = false): Result<Int>
    suspend fun refreshCatalog(): Result<Int>
}
