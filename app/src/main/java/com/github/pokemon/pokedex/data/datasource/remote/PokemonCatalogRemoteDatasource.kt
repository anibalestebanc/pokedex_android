package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonCatalogDto

interface PokemonCatalogRemoteDatasource {
    suspend fun fetchFullCatalog(): List<PokemonCatalogDto>
}
