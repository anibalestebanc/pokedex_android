package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.core.network.safeApiCall
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import com.github.pokemon.pokedex.data.datasource.remote.dto.CatalogDto

class RetrofitCatalogRemoteDataSource(
    private val pokeApi: PokemonApi,
) : CatalogRemoteDataSource {

    override suspend fun fetchFullCatalog(): List<CatalogDto> =
        safeApiCall {
            val all = mutableListOf<CatalogDto>()
            val pageSize = PAGE_SIZE
            var offset = OFFSET
            var total = Int.MAX_VALUE
            while (offset < total) {
                val page = pokeApi.getCatalog(limit = pageSize, offset = offset)
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
