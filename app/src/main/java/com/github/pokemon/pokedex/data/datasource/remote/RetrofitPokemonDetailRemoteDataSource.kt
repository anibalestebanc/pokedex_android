package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.core.network.safeApiCall
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApiV2
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto

class RetrofitPokemonDetailRemoteDataSource(
    private val apiV2: PokemonApiV2,
) : PokemonDetailRemoteDataSource {
    override suspend fun getPokemon(idOrName: String): PokemonDetailDto =
        safeApiCall { apiV2.getPokemon(idOrName) }
}
