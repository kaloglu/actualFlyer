package com.kaloglu.actualflyer.network

import com.google.firebase.auth.FirebaseUser
import com.kaloglu.actualflyer.util.Result

interface UserDataSource {

    fun getCurrentUser(): Result<FirebaseUser>
    suspend fun getCurrentUserIdToken(firebaseUser: FirebaseUser): Result<String>

}