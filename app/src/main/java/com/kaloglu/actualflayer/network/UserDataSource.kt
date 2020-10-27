package com.kaloglu.actualflayer.network

import com.google.firebase.auth.FirebaseUser
import com.kaloglu.actualflayer.User
import com.kaloglu.actualflayer.util.Result

interface UserDataSource {

    fun getCurrentUser(): Result<FirebaseUser>
    suspend fun getCurrentUserIdToken(firebaseUser: FirebaseUser): Result<String>
    suspend fun getUser(auth: String): Result<User>
}