package com.github.zsoltk.pokedex.data.datasource.remote

import com.github.zsoltk.pokedex.core.network.safeApiCall
import com.github.zsoltk.pokedex.data.datasource.remote.api.PokemonApiV2
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonDetailDto

class RetrofitPokemonDetailRemoteDataSource(
    private val apiV2: PokemonApiV2,
) : PokemonDetailRemoteDataSource {
    override suspend fun getPokemon(idOrName: String): PokemonDetailDto =
        safeApiCall { apiV2.getPokemon(idOrName) }
}
