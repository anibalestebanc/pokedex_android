package com.github.pokemon.pokedex.core.network

import com.github.pokemon.pokedex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClientProvider {

    val defaultClient by lazy {
        OkHttpClient.Builder()
            .apply {
                connectTimeout(15, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)

                if (BuildConfig.DEBUG) {

                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }

                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }
}
