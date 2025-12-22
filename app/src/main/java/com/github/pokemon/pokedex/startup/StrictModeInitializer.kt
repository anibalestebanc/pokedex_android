package com.github.pokemon.pokedex.startup

import android.os.StrictMode
import com.github.pokemon.pokedex.BuildConfig

class StrictModeInitializer : AppInitializer {
    override fun invoke() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build(),
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build(),
            )
        }
    }
}
