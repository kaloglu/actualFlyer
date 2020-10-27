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
import com.google.firebase.auth.FirebaseAuth
import com.kaloglu.actualflayer.R
import com.kaloglu.actualflayer.databinding.FragmentAuthenticationBinding
import com.kaloglu.actualflayer.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding>(R.layout.fragment_authentication) {
    override val viewModel: AuthenticationViewModel by viewModels()

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    private val loginContract = registerForActivityResult(LoginContract()) { isLoginSucces ->
        viewModel.fetchToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()


        /*
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result?.token
                    if (idToken.isNullOrBlank()) navigateToGoogleAuth()
                    Log.e("Utku", idToken.toString())
                } else {
                    Log.e("Utku", task.exception?.message.toString())
                    navigateToGoogleAuth()
                    // Handle error -> task.getException();
                }
            }

         */


    }

    private fun observeViewModel() = with(viewModel) {
        navigateToLoginEvent.observe { navigateToGoogleAuth() }
        navigateToProductsEvent.observe {
            //TODO navigate to products
        }
    }

    private fun navigateToGoogleAuth() {
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        loginContract.launch(intent)
    }

    private class LoginContract : ActivityResultContract<Intent, Boolean>() {

        override fun createIntent(context: Context, intent: Intent): Intent {
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return when (resultCode) {
                Activity.RESULT_OK -> {
                    val response = IdpResponse.fromResultIntent(intent) ?: return false
                    Log.e("Utku", "idp " + response.idpToken.toString())
                    return true
                }
                else -> false
            }
        }
    }
}