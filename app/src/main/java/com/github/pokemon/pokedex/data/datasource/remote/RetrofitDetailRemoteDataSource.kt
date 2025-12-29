package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.core.network.safeApiCall
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto

class RetrofitDetailRemoteDataSource(private val pokeApi: PokemonApi, ) : DetailRemoteDataSource {
    override suspend fun getDetail(idOrName: String): PokemonDetailDto =
        safeApiCall { pokeApi.getPokemon(idOrName) }
}
