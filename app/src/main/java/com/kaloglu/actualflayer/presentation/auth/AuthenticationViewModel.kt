package com.kaloglu.actualflayer.presentation.auth

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.kaloglu.actualflayer.domain.GetUserUseCase
import com.kaloglu.actualflayer.domain.LoginControlUseCase
import com.kaloglu.actualflayer.presentation.base.BaseViewModel
import com.kaloglu.actualflayer.util.SimpleEventEmitter
import com.kaloglu.actualflayer.util.SimpleEventSource
import com.kaloglu.actualflayer.util.onError
import com.kaloglu.actualflayer.util.onSuccess
import kotlinx.coroutines.launch

class AuthenticationViewModel @ViewModelInject constructor(
    private val loginControlUseCase: LoginControlUseCase,
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel() {

    private val _navigateToProductsEvent = SimpleEventEmitter()
    val navigateToProductsEvent: SimpleEventSource get() = _navigateToProductsEvent

    private val _navigateToLoginEvent = SimpleEventEmitter()
    val navigateToLoginEvent: SimpleEventSource get() = _navigateToLoginEvent

    init {
        onLoginControl()
    }

    private fun onLoginControl() = viewModelScope.launch {
        loginControlUseCase()
            .onSuccess {
                Log.e("Utku", "onSuccess")
                fetchUser()
                _navigateToProductsEvent.emit()
            }.onError {
                Log.e("Utku", "onError")
                _navigateToLoginEvent.emit()
            }
    }

    private fun fetchUser() = viewModelScope.launch {
        Log.e("Utku", "girdi")
        getUserUseCase().onSuccess {
            Log.e("Utku", "User OnSucces " + it.name)
        }.onError {
            Log.e("Utku", "User Error " + it.message)
        }
    }

    fun fetchToken() {
        //TODO
    }

}