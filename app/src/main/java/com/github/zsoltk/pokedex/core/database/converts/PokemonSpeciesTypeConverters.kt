package com.github.zsoltk.pokedex.core.database.converts

import androidx.room.TypeConverter
import com.github.zsoltk.pokedex.core.database.DatabaseJson.dbJson

import kotlinx.serialization.builtins.ListSerializer

class PokemonSpeciesTypeConverters {

    @TypeConverter
    fun listStringToText(value: List<String>?): String =
        dbJson.encodeToString(ListSerializer(kotlinx.serialization.serializer()), value ?: emptyList())
    @TypeConverter
    fun textToListString(value: String): List<String> =
        if (value.isBlank()) emptyList()
        else dbJson.decodeFromString(ListSerializer(kotlinx.serialization.serializer()), value)
}
