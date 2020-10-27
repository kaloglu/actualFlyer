package com.kaloglu.actualflayer.util

sealed class Result<out T> {
    data class Success<T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val exception: Exception,
    ) : Result<Nothing>()
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val <T> Result<T>.exception: Exception?
    get() = (this as? Result.Error)?.exception

inline fun <T> Result<T>.onSuccess(block: (data: T) -> Unit): Result<T> = also {
    data?.let { block(it) }
}

inline fun <T> Result<T>.onError(block: (Exception) -> Unit): Result<T> = also {
    exception?.let { block(it) }
}

inline fun <T, R> Result<T>.mapData(transform: (value: T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
}


fun <T> Result<T>.toUnit(): Result<Unit> = mapData { Unit }