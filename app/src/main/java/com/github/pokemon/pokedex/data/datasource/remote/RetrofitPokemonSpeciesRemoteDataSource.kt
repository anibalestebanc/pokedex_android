package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.core.network.safeApiCall
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonSpeciesDto

class RetrofitPokemonSpeciesRemoteDataSource(
    private val apiV2: PokemonApi
) : PokemonSpeciesRemoteDataSource {

    override suspend fun getSpecies(idOrName: String): PokemonSpeciesDto =
        safeApiCall { apiV2.getSpecies(idOrName) }

}
