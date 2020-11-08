package com.kaloglu.actualflyer.database.source

import com.kaloglu.actualflyer.domain.model.User
import com.kaloglu.actualflyer.database.KeyValueStorage
import com.kaloglu.actualflyer.di.IoDispatcher
import com.kaloglu.actualflyer.domain.database.UserLocalDataSource
import com.kaloglu.actualflyer.domain.model.Configuration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val keyValueStorage: KeyValueStorage,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : UserLocalDataSource {
    private val userStateFlow = MutableStateFlow(keyValueStorage.get(USER_KEY, User.serializer()))

    override val user: Flow<User?> get() = userStateFlow

    override suspend fun getUser(): User? = userStateFlow.value

    override suspend fun saveUser(user: User) = with(ioDispatcher) {
        keyValueStorage.put(USER_KEY, user, User.serializer())
        userStateFlow.value = user
    }

    override suspend fun deleteUser() = with(ioDispatcher) {
        keyValueStorage.remove(USER_KEY)
        userStateFlow.value = null
    }

    override fun getConfiguration(): Configuration? =
        keyValueStorage.get(CONFIGURATION_KEY, Configuration.serializer())

    override fun saveConfiguration(configuration: Configuration) = keyValueStorage.remove(USER_KEY)

    companion object {
        private const val USER_KEY = "user_key"
        private const val CONFIGURATION_KEY = "configuration_key"
    }
}