package com.github.pokemon.pokedex.core.common.error

class NotFoundException(
    override val message: String? = "Item not found",
    override val cause: Throwable? = null,
) : PokeException(message, cause)
