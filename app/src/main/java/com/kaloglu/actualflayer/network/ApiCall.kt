package com.kaloglu.actualflayer.network

import com.kaloglu.actualflayer.di.IoDispatcher
import com.kaloglu.actualflayer.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCall @Inject constructor(
    private val json: Json,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun <T> invoke(
        call: suspend () -> T,
    ): Result<T?> = withContext(ioDispatcher) {
        try {
            val response = call()
            Result.Success(response)
        } catch (exception: HttpException) {
            val errorBody = exception.response()?.errorBody()?.string()
                ?: return@withContext Result.Error(exception)
            return@withContext Result.Error(Exception(errorBody))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}
