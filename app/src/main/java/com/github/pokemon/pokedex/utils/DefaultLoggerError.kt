package com.github.pokemon.pokedex.utils

import android.util.Log

interface LoggerError {
    operator fun invoke(message: String, error: Throwable)
    fun logError(message: String, error: Throwable, params: Map<String, Any>)
}

class DefaultLoggerError : LoggerError {

    override operator fun invoke(message: String, error: Throwable) {
        Log.d(error.javaClass.simpleName, "$message -> ${error.message}")
    }

    override fun logError(message: String, error: Throwable, params: Map<String, Any>) {
        Log.d(error.javaClass.simpleName, "$message -> ${error.message}")
    }
}
