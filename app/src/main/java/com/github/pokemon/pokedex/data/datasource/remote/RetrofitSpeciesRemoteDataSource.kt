package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.core.network.safeApiCall
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonSpeciesDto

class RetrofitSpeciesRemoteDataSource(private val pokeApi: PokemonApi) : SpeciesRemoteDataSource {

    override suspend fun getSpecies(idOrName: String): PokemonSpeciesDto =
        safeApiCall { pokeApi.getSpecies(idOrName) }

}
