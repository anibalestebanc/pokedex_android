package com.github.zsoltk.pokedex.data.datasource.remote.api

import com.github.zsoltk.pokedex.data.datasource.remote.dto.PokemonCatalogResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiV2 {
    @GET("pokemon")
    suspend fun getPokemonPage(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonCatalogResponseDto
}
