package com.kaloglu.actualflyer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val auth: String? = null,
    val name: String? = null
)