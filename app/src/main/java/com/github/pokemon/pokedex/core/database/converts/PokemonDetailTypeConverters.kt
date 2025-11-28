package com.github.pokemon.pokedex.core.database.converts

import androidx.room.TypeConverter
import com.github.pokemon.pokedex.core.database.DatabaseJson.dbJson
import com.github.pokemon.pokedex.domain.model.PokemonSprites
import com.github.pokemon.pokedex.domain.model.Stat
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
private data class StatDb(val name: String, val value: Int)

@Serializable
private data class PokemonSpritesDb(
    val dreamWorld: String? = null,
    val home: String? = null,
    val officialArtwork: String? = null,
    val fallbackFront: String? = null
)

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

    @TypeConverter
    fun spritesToText(value: PokemonSprites?): String {
        val db = PokemonSpritesDb(
            dreamWorld = value?.dreamWorld,
            home = value?.home,
            officialArtwork = value?.officialArtwork,
            fallbackFront = value?.fallbackFront
        )
        return dbJson.encodeToString(PokemonSpritesDb.serializer(), db)
    }

    @TypeConverter
    fun textToSprites(value: String): PokemonSprites {
        if (value.isBlank()) return PokemonSprites(null, null, null, null)
        val db = dbJson.decodeFromString(PokemonSpritesDb.serializer(), value)
        return PokemonSprites(
            dreamWorld = db.dreamWorld,
            home = db.home,
            officialArtwork = db.officialArtwork,
            fallbackFront = db.fallbackFront
        )
    }
}
