package com.kaloglu.actualflyer.domain.usecases

import android.util.Log
import com.kaloglu.actualflyer.domain.database.UserLocalDataSource
import com.kaloglu.actualflyer.domain.model.Configuration
import com.kaloglu.actualflyer.domain.model.User
import com.kaloglu.actualflyer.network.UserDataSource
import com.kaloglu.actualflyer.util.Result
import com.kaloglu.actualflyer.util.onError
import com.kaloglu.actualflyer.util.onSuccess
import com.kaloglu.actualflyer.util.toUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginControlUseCase @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    suspend operator fun invoke(): Result<Unit> {
        return when (val currentUserResult = userDataSource.getCurrentUser()) {
            is Result.Success -> {
                lateinit var user: User
                userDataSource.getCurrentUserIdToken(currentUserResult.data).onSuccess { auth ->
                    userLocalDataSource.saveConfiguration(
                        Configuration(
                            auth,
                            currentUserResult.data.uid
                        )
                    )
                    userDataSource.getIsAdminCurrentUser(userLocalDataSource.getUser()?.email)
                        .onSuccess {
                            with(currentUserResult.data){
                                user = User(
                                    name = this.displayName,
                                    auth = auth,
                                    email = this.email,
                                    photoUri = this.photoUrl.toString(),
                                    isAdmin = it
                                )
                            }
                        }.onError {
                            with(currentUserResult.data){
                                user = User(
                                    name = this.displayName,
                                    auth = auth,
                                    email = this.email,
                                    photoUri = this.photoUrl.toString(),
                                    isAdmin = false
                                )
                            }
                        }
                    userLocalDataSource.saveUser(user)
                }.toUnit()
            }
            is Result.Error -> currentUserResult
        }
    }
}