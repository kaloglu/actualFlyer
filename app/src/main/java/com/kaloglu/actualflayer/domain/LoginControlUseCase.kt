package com.kaloglu.actualflayer.domain

import com.kaloglu.actualflayer.domain.database.UserLocalDataSource
import com.kaloglu.actualflayer.domain.model.Configuration
import com.kaloglu.actualflayer.network.UserDataSource
import com.kaloglu.actualflayer.util.Result
import com.kaloglu.actualflayer.util.onSuccess
import com.kaloglu.actualflayer.util.toUnit
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