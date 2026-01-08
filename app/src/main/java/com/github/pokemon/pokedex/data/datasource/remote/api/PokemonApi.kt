package com.github.pokemon.pokedex.data.datasource.remote.api

import com.github.pokemon.pokedex.data.datasource.remote.dto.CatalogResponseDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.DetailDto
import com.github.pokemon.pokedex.data.datasource.remote.dto.SpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getCatalog(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CatalogResponseDto

    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(
        @Path("idOrName") idOrName: String,
    ): DetailDto

    @GET("pokemon-species/{idOrName}")
    suspend fun getSpecies(
        @Path("idOrName") idOrName: String,
    ): SpeciesDto
}
