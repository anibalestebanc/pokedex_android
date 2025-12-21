package com.github.pokemon.pokedex.utils

import android.util.Log

interface LoggerError {
    fun logError(error: Exception)
    fun logError(message: String, error: Exception)
    fun logError(message: String, error: Exception, params: Map<String, Any>)
}

class DefaultLoggerError : LoggerError {

    override fun logError(error: Exception) {
        Log.d(error.javaClass.simpleName, "${error.message}")
    }

    override fun logError(message: String, error: Exception) {
        Log.d(error.javaClass.simpleName, "$message -> ${error.message}")
    }

    override fun logError(message: String, error: Exception, params: Map<String, Any>) {
        Log.d(error.javaClass.simpleName, "$message -> ${error.message}")
    }
}
