package com.github.pokemon.pokedex.core.common.error

class WorkerException(
    override val message: String? = "Error executing worker",
    override val cause: Throwable? = null,
) : Exception(message, cause)
