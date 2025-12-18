package com.github.pokemon.pokedex.core.network

import com.github.pokemon.pokedex.core.common.error.NetworkException
import com.github.pokemon.pokedex.core.common.error.NotFoundException
import com.github.pokemon.pokedex.core.common.error.ServerException
import com.github.pokemon.pokedex.core.common.error.UnknownException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): T {
    return try {
        block()
    } catch (e: HttpException) {
        when (e.code()) {
            404 -> throw NotFoundException(cause = e)
            in 500..599 -> throw ServerException(cause = e)
            else -> throw UnknownException(message = "HTTP ${e.code()}", cause = e)
        }
    } catch (e: IOException) {
        throw NetworkException(cause = e)
    } catch (e : CancellationException){
        throw e
    }
    catch (e: Exception) {
        throw UnknownException(cause = e)
    }
}
