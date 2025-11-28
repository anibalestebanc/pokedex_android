package com.github.pokemon.pokedex.utils

import com.github.pokemon.pokedex.utils.PokeTimeUtils.getNow
import java.util.concurrent.TimeUnit

val SEVEN_DAY_MILLIS: Long = TimeUnit.DAYS.toMillis(7)

object RefreshDueUtil {

    fun isRefreshDue(lastUpdate: Long, minDayInterval: Long = SEVEN_DAY_MILLIS): Boolean {
        val now = getNow()
        return now - lastUpdate >= minDayInterval
    }
}
