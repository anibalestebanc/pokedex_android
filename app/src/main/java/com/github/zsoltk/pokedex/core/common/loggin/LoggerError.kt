package com.github.zsoltk.pokedex.core.common.loggin

import android.util.Log

//TODO implement remote system Error
object LoggerError {

    fun logError(error: Exception) {
        Log.d(error.javaClass.simpleName, "${error.message}")
    }

    fun logError(message: String, error: Exception) {
        Log.d(error.javaClass.simpleName, "$message -> ${error.message}")
    }

    fun logError(message: String, error: Exception, params: Map<String, Any>) {
        Log.d(error.javaClass.simpleName, "$message -> ${error.message}")
    }
}
