package com.github.pokemon.pokedex.core.di

import android.app.Application
import com.github.pokemon.pokedex.core.network.BaseUrlProvider
import com.github.pokemon.pokedex.core.network.RetrofitFactory
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.core.network.utils.OnlineNetworkMonitor
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NAMED_BASE_URL_KEY = "default_base_url"
const val NAMED_IO_DISPATCHER_KEY = "io_dispatcher"

val networkModule = module {

    single<String> { BaseUrlProvider.getBaseUrl() } withOptions {
        named(NAMED_BASE_URL_KEY)
    }

    single<CoroutineDispatcher> { Dispatchers.IO } withOptions {
        named(NAMED_IO_DISPATCHER_KEY)
    }

    single<NetworkMonitor> {
        OnlineNetworkMonitor(
            context = get<Application>(),
        )
    }

    //Api
    factory<PokemonApi> {
        RetrofitFactory.createService(
            baseUrl = get(qualifier = named(NAMED_BASE_URL_KEY)),
            klass = PokemonApi::class.java,
        )
    }
}
