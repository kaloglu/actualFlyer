package com.kaloglu.actualflyer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val auth: String,
    val uId: String
)