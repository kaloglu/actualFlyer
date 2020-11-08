package com.kaloglu.actualflyer.domain.database

import com.kaloglu.actualflyer.domain.model.User
import com.kaloglu.actualflyer.domain.model.Configuration
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    val user: Flow<User?>
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
    suspend fun deleteUser()
    fun getConfiguration(): Configuration?
    fun saveConfiguration(configuration: Configuration)
}