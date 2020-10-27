package com.kaloglu.actualflayer

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val auth: String? = null,
    val name: String? = null
)