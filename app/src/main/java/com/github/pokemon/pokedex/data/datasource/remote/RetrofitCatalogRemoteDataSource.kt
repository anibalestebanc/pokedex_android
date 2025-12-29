package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.core.network.safeApiCall
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonCatalogDto

class RetrofitCatalogRemoteDataSource(
    private val pokeApi: PokemonApi,
) : CatalogRemoteDataSource {

    override suspend fun fetchFullCatalog(): List<PokemonCatalogDto> =
        safeApiCall {
            val all = mutableListOf<PokemonCatalogDto>()
            val pageSize = PAGE_SIZE
            var offset = OFFSET
            var total = Int.MAX_VALUE
            while (offset < total) {
                val page = pokeApi.getPokemonCatalog(limit = pageSize, offset = offset)
                total = page.count
                val mapped = page.results
                all += mapped
                offset += pageSize
                if (mapped.isEmpty()) break
            }
            all
        }

    private companion object {
        const val OFFSET = 0
        const val PAGE_SIZE: Int = 200
    }
}
