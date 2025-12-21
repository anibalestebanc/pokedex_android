package com.github.pokemon.pokedex.core.di

import android.app.Application
import com.github.pokemon.pokedex.core.network.BaseUrlProvider
import com.github.pokemon.pokedex.core.network.RetrofitFactory
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.core.network.utils.OnlineNetworkMonitor
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_URL_KEY = "default_base_url"
const val IO_DISPATCHER_KEY = "io_dispatcher"
const val MAIN_DISPATCHER_KEY = "main_dispatcher"
const val DEFAULT_DISPATCHER_KEY = "default_dispatcher"

val networkModule = module {

    single<String>(named(BASE_URL_KEY)) { BaseUrlProvider.getBaseUrl() }

    single<CoroutineDispatcher>(named(IO_DISPATCHER_KEY)) { Dispatchers.IO }

    single<CoroutineDispatcher>(named(MAIN_DISPATCHER_KEY)) { Dispatchers.Main }

    single<CoroutineDispatcher>(named(DEFAULT_DISPATCHER_KEY)) { Dispatchers.Default }

    single<NetworkMonitor> {
        OnlineNetworkMonitor(
            context = get<Application>(),
        )
    }

    //Api
    factory<PokemonApi> {
        RetrofitFactory.createService(
            baseUrl = get(qualifier = named(BASE_URL_KEY)),
            klass = PokemonApi::class.java,
        )
    }
}
