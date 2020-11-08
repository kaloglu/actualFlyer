package com.kaloglu.actualflyer.network

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kaloglu.actualflyer.util.Result
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
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

    override suspend fun getIsAdminCurrentUser(email: String?): Result<Boolean> =
        suspendCoroutine {
            if (email.isNullOrEmpty()) {
                it.resume(Result.Success(false))
            } else {
                firebaseFirestore.collection("user")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            if (document.data["isAdmin"] as Boolean) {
                                it.resume(Result.Success(true))
                            } else {
                                it.resume(Result.Success(false))
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        it.resume(Result.Error(exception))
                        Log.e("Fatih", "Error getting documents.", exception)
                    }
            }
        }
}