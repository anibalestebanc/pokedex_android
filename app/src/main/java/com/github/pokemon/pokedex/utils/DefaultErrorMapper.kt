package com.github.pokemon.pokedex.utils

import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.exception.PokeException

interface ErrorMapper {
    operator fun invoke(error: Throwable): String
}

class DefaultErrorMapper(private val stringProvider: StringProvider) : ErrorMapper {
    override operator fun invoke(error: Throwable): String = when (error) {
        is PokeException.DatabaseException -> stringProvider(R.string.error_generic_message)
        is PokeException.NetworkException -> stringProvider(R.string.error_no_internet_message)
        is PokeException.NotFoundException -> stringProvider(R.string.error_not_found_message)
        is PokeException.ServerException -> stringProvider(R.string.error_generic_message)
        is PokeException.WorkException -> stringProvider(R.string.error_generic_message)
        is PokeException.UnknownException -> stringProvider(R.string.error_generic_message)
        else -> stringProvider(R.string.error_generic_message)
    }
}

