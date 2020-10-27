package com.kaloglu.actualflayer.network

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.kaloglu.actualflayer.User
import com.kaloglu.actualflayer.util.Result
import com.kaloglu.actualflayer.util.mapData
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val userApi: UserApi,
    private val apiCall: ApiCall,
) : UserDataSource {

    override fun getCurrentUser(): Result<FirebaseUser> {
        val currentUser = firebaseAuth.currentUser ?: return Result.Error(NullPointerException())
        return Result.Success(currentUser)
    }

    override suspend fun getCurrentUserIdToken(firebaseUser: FirebaseUser): Result<String> =
        suspendCoroutine { continuation ->
            firebaseUser.getIdToken(true).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result?.token
                    if (idToken.isNullOrBlank())
                        continuation.resume(Result.Error(NullPointerException()))
                    Log.e("Utku", "idTokenGeldi: $idToken")
                    continuation.resume(Result.Success(idToken!!))
                } else {
                    continuation.resume(
                        Result.Error(
                            task.exception ?: UserNotAuthenticatedException()
                        )
                    )
                }
            }
        }

    override suspend fun getUser(auth: String): Result<User> {
        return apiCall { userApi.getUser(auth) }.mapData {
            it!!
        }
    }

}