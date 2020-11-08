package com.kaloglu.actualflyer.presentation.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.kaloglu.actualflyer.R
import com.kaloglu.actualflyer.databinding.FragmentAuthenticationBinding
import com.kaloglu.actualflyer.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


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
        if (it) {
            viewModel.fetchToken()
        }else{
            Snackbar.make(requireView(), "An error accurred", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginCallbacks()
        initObservers()
    }

    private fun initObservers() = with(viewModel) {
        eventNavigateMain.observe {
            val action = AuthenticationFragmentDirections.fromAuthFragmentToProductsFragment()
            view?.findNavController()?.navigate(action)
        }
        eventError.observe {
            Snackbar.make(requireView(), "An error accurred ${it.localizedMessage}", Snackbar.LENGTH_LONG).show()
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