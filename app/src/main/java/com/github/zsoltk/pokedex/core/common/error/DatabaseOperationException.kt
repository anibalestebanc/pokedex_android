package com.github.zsoltk.pokedex.core.common.error

class DatabaseOperationException(
    override val message: String? = "Error to execute operation in database",
    override val cause: Throwable? = null,
) : PokeException()
