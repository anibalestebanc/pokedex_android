package com.github.pokemon.pokedex.utils

import java.util.concurrent.TimeUnit

@Suppress("MagicNumber")
val SEVEN_DAY_MILLIS: Long = TimeUnit.DAYS.toMillis(7)

interface RefreshDueUtil {
    fun isRefreshDue(lastUpdate: Long, minDayInterval: Long = SEVEN_DAY_MILLIS): Boolean
}

class DefaultRefreshDueUtil(private val pokeTimeUtil: PokeTimeUtil) : RefreshDueUtil {

    override fun isRefreshDue(lastUpdate: Long, minDayInterval: Long): Boolean {
        val now = pokeTimeUtil.now()
        return now - lastUpdate >= minDayInterval
    }
}
