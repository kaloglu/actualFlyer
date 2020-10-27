package com.kaloglu.actualflayer.presentation.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kaloglu.actualflayer.R
import com.kaloglu.actualflayer.databinding.FragmentProductsBinding
import com.kaloglu.actualflayer.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment :
    BaseFragment<FragmentProductsBinding>(R.layout.fragment_products) {
    override val viewModel: ProductsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}