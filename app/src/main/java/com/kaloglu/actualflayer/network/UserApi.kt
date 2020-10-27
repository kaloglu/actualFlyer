package com.kaloglu.actualflayer.network

import com.kaloglu.actualflayer.User
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("user")
    suspend fun getUser(
        @Header("Authorization") auth: String
    ): User

}