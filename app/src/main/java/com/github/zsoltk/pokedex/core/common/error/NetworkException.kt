package com.github.zsoltk.pokedex.core.common.error

class NetworkException (
    override val message: String? = "Connection error",
    override val cause: Throwable? = null
) : PokeException(message, cause)
