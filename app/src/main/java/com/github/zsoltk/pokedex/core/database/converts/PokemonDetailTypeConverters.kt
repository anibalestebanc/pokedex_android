package com.github.zsoltk.pokedex.core.database.converts

import androidx.room.TypeConverter
import com.github.zsoltk.pokedex.core.database.DatabaseJson.dbJson
import com.github.zsoltk.pokedex.domain.model.Stat
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
private data class StatDb(val name: String, val value: Int)

class PokemonDetailTypeConverters {

    @TypeConverter
    fun listStringToText(value: List<String>?): String =
        dbJson.encodeToString(ListSerializer(kotlinx.serialization.serializer()), value ?: emptyList())

    @TypeConverter
    fun textToListString(value: String): List<String> =
        if (value.isBlank()) emptyList()
        else dbJson.decodeFromString(ListSerializer(kotlinx.serialization.serializer()), value)

    @TypeConverter
    fun listStatToText(value: List<Stat>?): String {
        val payload = (value ?: emptyList()).map { StatDb(it.name, it.value) }
        return dbJson.encodeToString(ListSerializer(StatDb.serializer()), payload)
    }

    @TypeConverter
    fun textToListStat(value: String): List<Stat> {
        if (value.isBlank()) return emptyList()
        val payload = dbJson.decodeFromString(ListSerializer(StatDb.serializer()), value)
        return payload.map { Stat(name = it.name, value = it.value) }
    }
}
