package com.kaloglu.actualflayer.presentation.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.kaloglu.actualflayer.R
import com.kaloglu.actualflayer.databinding.FragmentAuthenticationBinding
import com.kaloglu.actualflayer.presentation.base.BaseFragment
import com.kaloglu.actualflayer.util.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding>(R.layout.fragment_authentication) {
    override val viewModel: AuthenticationViewModel by viewModels()

    private val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
    )
    private val firebaseSignInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

    private val loginContract = registerForActivityResult(LoginContract()) {
        if (it){
            Log.e("Fatih", "Auth True ")
            viewModel.fetchToken()
        }else{
            Log.e("Fatih", "Auth False ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginCallbacks()
        initObservers()
    }

    private fun initObservers()= with(viewModel) {
        eventNavigateMain.observe {
            Log.e("Fatih Observe Navigate", "Navigated to mainpage")
        }
        eventError.observe {
             Log.d("Fatih Observe Exception",it.toString())
        }
    }


    private fun initLoginCallbacks() {
        binding.buttonGoogleSignIn.setOnClickListener {
            loginContract.launch(firebaseSignInIntent)
        }
    }

    private class LoginContract : ActivityResultContract<Intent, Boolean>() {

        override fun createIntent(context: Context, intent: Intent): Intent {
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return when (resultCode) {
                Activity.RESULT_OK -> {
                    val response = IdpResponse.fromResultIntent(intent)
                    Log.e("Fatih Auth", response.toString())
                    return true
                }
                else -> false
            }
        }
    }
}