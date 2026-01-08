package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.CatalogDto

interface CatalogRemoteDataSource {
    suspend fun fetchFullCatalog(): List<CatalogDto>
}
