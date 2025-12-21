package com.github.pokemon.pokedex.core.network

import com.github.pokemon.pokedex.domain.exception.PokeException.NetworkException
import com.github.pokemon.pokedex.domain.exception.PokeException.NotFoundException
import com.github.pokemon.pokedex.domain.exception.PokeException.ServerException
import com.github.pokemon.pokedex.domain.exception.PokeException.UnknownException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        when (e) {
            is CancellationException -> throw e
            is IOException -> throw NetworkException(cause = e)
            is HttpException -> when (e.code()) {
                404 -> throw NotFoundException(cause = e)
                in 500..599 -> throw ServerException(cause = e)
                else -> throw UnknownException(message = "HTTP ${e.code()}", cause = e)
            }

            else -> throw UnknownException(cause = e)
        }
    }
}
