package com.github.pokemon.pokedex.data.datasource.remote.api

import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonCatalogResponseDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonSpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonCatalog(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonCatalogResponseDto

    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(
        @Path("idOrName") idOrName: String,
    ): PokemonDetailDto

    @GET("pokemon-species/{idOrName}")
    suspend fun getSpecies(
        @Path("idOrName") idOrName: String,
    ): PokemonSpeciesDto
}
