package com.github.zsoltk.pokedex.core.common.error

class UnknownException(
    override val message: String? = "Unknown remote exception",
    override val cause: Throwable? = null,
) : Exception(message, cause)
