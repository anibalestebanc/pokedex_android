package com.github.zsoltk.pokedex.data.datasource.remote

import com.github.zsoltk.pokedex.core.network.safeApiCall
import com.github.zsoltk.pokedex.data.datasource.remote.api.PokemonApiV2
import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonSpeciesDto

class RetrofitPokemonSpeciesRemoteDataSource(
    private val apiV2: PokemonApiV2
) : PokemonSpeciesRemoteDataSource {

    override suspend fun getSpecies(idOrName: String): PokemonSpeciesDto =
        safeApiCall { apiV2.getSpecies(idOrName) }

}
