package com.kaloglu.actualflyer.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaloglu.actualflyer.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    protected fun <T> launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        call: suspend CoroutineScope.() -> Result<T>
    ): Job {
        return viewModelScope.launch(context, start) {
            try {
                call()
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }
    }

}