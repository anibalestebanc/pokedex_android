package com.github.pokemon.pokedex.domain.model

enum class PokemonStatType(val key: String, val label: String) {
    HP("hp", "HP"),
    ATTACK("attack", "Attack"),
    DEFENSE("defense", "Defense"),
    SPECIAL_ATTACK("special-attack", "Sp. Atk"),
    SPECIAL_DEFENSE("special-defense", "Sp. Def"),
    SPEED("speed", "Speed")
}
