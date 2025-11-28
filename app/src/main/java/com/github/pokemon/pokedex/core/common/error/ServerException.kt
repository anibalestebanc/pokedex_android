package com.github.pokemon.pokedex.core.common.error

class ServerException (
    override val message: String? = "Internal server error",
    override val cause: Throwable? = null
) : PokeException(message, cause)
