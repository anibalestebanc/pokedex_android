package com.github.zsoltk.pokedex.core.common.error

open class PokeException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause)
