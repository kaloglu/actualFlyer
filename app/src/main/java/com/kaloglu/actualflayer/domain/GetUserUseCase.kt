package com.kaloglu.actualflayer.domain

import com.kaloglu.actualflayer.User
import com.kaloglu.actualflayer.domain.database.UserLocalDataSource
import com.kaloglu.actualflayer.network.UserDataSource
import com.kaloglu.actualflayer.util.Result
import com.kaloglu.actualflayer.util.onSuccess
import com.kaloglu.actualflayer.util.toUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) {
    suspend operator fun invoke(): Result<User> {
        return userDataSource.getUser(userLocalDataSource.getConfiguration()?.auth ?: "asd")
    }
}