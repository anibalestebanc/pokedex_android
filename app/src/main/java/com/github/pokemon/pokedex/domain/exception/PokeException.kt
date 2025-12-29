package com.github.pokemon.pokedex.domain.exception

sealed class PokeException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {

    class NetworkException(message: String? = null, cause: Throwable? = null) : PokeException(message, cause)

    class ServerException(message: String? = null, cause: Throwable? = null) :
        PokeException(message, cause)


    class DatabaseException(message: String? = null, cause: Throwable? = null) :
        PokeException(message, cause)

    class NotFoundException(message: String? = null, cause: Throwable? = null) :
        PokeException(message, cause)

    class WorkException(message: String? = null, cause: Throwable? = null) :
        PokeException(message, cause)

    class UnknownException(message: String? = null, cause: Throwable? = null) : PokeException(message, cause)
}
