package com.github.zsoltk.pokedex.data.datasource.remote

import com.github.zsoltk.pokedex.core.network.safeApiCall
import com.github.zsoltk.pokedex.data.datasource.remote.api.PokemonApiV2
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonCatalogDto

class RetrofitCatalogRemoteDataSource(
    private val pokemonApiV2: PokemonApiV2
) : PokemonCatalogRemoteDatasource {

    override suspend fun fetchFullCatalog(): List<PokemonCatalogDto> =
        safeApiCall {
            val all = mutableListOf<PokemonCatalogDto>()
            val pageSize = PAGE_SIZE
            var offset = 0
            var total = Int.MAX_VALUE
            while (offset < total) {
                val page = pokemonApiV2.getPokemonPage(limit = pageSize, offset = offset)
                total = page.count
                val mapped = page.results
                all += mapped
                offset += pageSize
                if (mapped.isEmpty()) break
            }
            all
        }

    private companion object{
        const val PAGE_SIZE: Int = 200
    }
}
