package com.kaloglu.actualflyer.domain

import android.util.Log
import com.kaloglu.actualflyer.domain.database.UserLocalDataSource
import com.kaloglu.actualflyer.domain.model.Configuration
import com.kaloglu.actualflyer.network.UserDataSource
import com.kaloglu.actualflyer.util.Result
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
                userDataSource.getCurrentUserIdToken(currentUserResult.data).onSuccess { auth ->
                    Log.e("Fatih", auth.toString())
                    userLocalDataSource.saveConfiguration(
                            Configuration(
                                    auth,
                                    currentUserResult.data.uid
                            )
                    )
                }.toUnit()
            }
            is Result.Error -> currentUserResult
        }
    }
}