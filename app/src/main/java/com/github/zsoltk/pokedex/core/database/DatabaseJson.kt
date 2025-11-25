package com.github.zsoltk.pokedex.core.database

import kotlinx.serialization.json.Json

object DatabaseJson {
    val dbJson = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}
