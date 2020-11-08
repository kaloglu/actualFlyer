package com.kaloglu.actualflyer.domain.model

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String? = null,
    val email: String? = null,
    val photoUri: String? = null,
    val auth: String? = null,
    val isAdmin: Boolean? = false
)