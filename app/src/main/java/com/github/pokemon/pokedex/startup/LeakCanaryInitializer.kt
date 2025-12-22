package com.github.pokemon.pokedex.startup

import com.github.pokemon.pokedex.BuildConfig
import leakcanary.LeakCanary

class LeakCanaryInitializer : AppInitializer {
    override fun invoke() {
        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(
                retainedVisibleThreshold = 3,
            )
        }
    }
}
