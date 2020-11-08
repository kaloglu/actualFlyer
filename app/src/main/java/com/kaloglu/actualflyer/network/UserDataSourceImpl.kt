package com.kaloglu.actualflyer.network

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kaloglu.actualflyer.util.Result
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserDataSourceImpl @Inject constructor(
        private val firebaseAuth: FirebaseAuth
) : UserDataSource {

    override fun getCurrentUser(): Result<FirebaseUser> {
        val currentUser = firebaseAuth.currentUser ?: return Result.Error(NullPointerException())
        return Result.Success(currentUser)
    }

    override suspend fun getCurrentUserIdToken(firebaseUser: FirebaseUser): Result<String> =
            suspendCoroutine { continuation ->
                firebaseUser.getIdToken(true).addOnCompleteListener { task ->
                    val idToken: String? = task.result?.token
                    if (task.isSuccessful) {
                        if (idToken.isNullOrBlank())
                            continuation.resume(Result.Error(NullPointerException()))
                        continuation.resume(Result.Success(idToken!!))
                    } else {
                        continuation.resume(
                                Result.Error(
                                        task.exception ?: UserNotAuthenticatedException()
                                )
                        )
                    }
                }.addOnFailureListener {
                    continuation.resume(Result.Error(it))
                }
            }


}