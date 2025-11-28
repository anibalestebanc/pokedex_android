package com.github.pokemon.pokedex.core.network

import com.github.pokemon.pokedex.core.network.HttpClientProvider.defaultClient
import com.github.pokemon.pokedex.core.network.JsonProvider.json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitFactory {

    private val contentType = "application/json".toMediaType()
    private val retrofitCache = mutableMapOf<String, Retrofit>()

    @Synchronized
    fun getRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return retrofitCache.getOrPut(baseUrl) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(
                    json.asConverterFactory(contentType),
                )
                .build()
        }
    }


    inline fun <reified T> createService(
        baseUrl: String,
        klass: Class<T>,
        client: OkHttpClient = defaultClient,
    ): T {
        return getRetrofit(baseUrl, client).create(klass)
    }
}
