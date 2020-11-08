package com.kaloglu.actualflayer.domain.database

import com.kaloglu.actualflayer.domain.model.User
import com.kaloglu.actualflayer.domain.model.Configuration
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    val user: Flow<User?>
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
    suspend fun deleteUser()
    fun getConfiguration(): Configuration?
    fun saveConfiguration(configuration: Configuration)
}