package com.github.zsoltk.pokedex.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val name: String,
    val value: Int,
)
