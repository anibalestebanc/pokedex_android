package com.github.zsoltk.pokedex.data.datasource.remote

import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonCatalogDto

interface PokemonCatalogRemoteDatasource {
    suspend fun fetchFullCatalog(): List<PokemonCatalogDto>
}
