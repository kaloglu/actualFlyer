package com.kaloglu.actualflyer.presentation.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.kaloglu.actualflyer.domain.LoginControlUseCase
import com.kaloglu.actualflyer.presentation.base.BaseViewModel
import com.kaloglu.actualflyer.util.SimpleEventEmitter
import com.kaloglu.actualflyer.util.SimpleEventSource
import com.kaloglu.actualflyer.util.onError
import com.kaloglu.actualflyer.util.onSuccess
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthenticationViewModel @ViewModelInject constructor(
    private val loginControlUseCase: LoginControlUseCase
) : BaseViewModel() {

    private val _eventNavigateMain = SimpleEventEmitter()
    val eventNavigateMain: SimpleEventSource get() = _eventNavigateMain

    private val _eventError = EventEmitter<Exception>()
    val eventError: EventSource<Exception> get() = _eventError


    fun fetchToken() = viewModelScope.launch {
        loginControlUseCase()
                .onSuccess {
                    _eventNavigateMain.emit()
                }.onError {
                    _eventError.emit(it)
                }



    }

}