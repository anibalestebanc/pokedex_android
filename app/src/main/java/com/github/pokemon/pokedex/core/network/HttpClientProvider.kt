package com.github.pokemon.pokedex.core.network

import com.github.pokemon.pokedex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClientProvider {
    private const val CONNECT_TIME_OUT = 15L
    private const val READ_TIME_OUT = 30L
    val defaultClient by lazy {
        OkHttpClient.Builder()
            .apply {
                connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)

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
