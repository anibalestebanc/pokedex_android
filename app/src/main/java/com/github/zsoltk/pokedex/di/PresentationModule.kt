package com.github.zsoltk.pokedex.di

import com.github.zsoltk.pokedex.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    //View models
    viewModelOf(::HomeViewModel)
}
