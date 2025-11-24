package com.github.zsoltk.pokedex.core.network

import com.github.zsoltk.pokedex.BuildConfig

object BaseUrlProvider {

    const val DEFAULT_BASE_URL = "https://pokeapi.co/api/v2/"
    const val TEST_BASE_URL = "https://pokeapi.co/api/v2/"

    fun getBaseUrl(): String = if (BuildConfig.DEBUG) TEST_BASE_URL else DEFAULT_BASE_URL

}
