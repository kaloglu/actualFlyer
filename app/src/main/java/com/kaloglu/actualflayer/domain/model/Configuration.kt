package com.kaloglu.actualflayer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val auth: String,
    val uId: String
)