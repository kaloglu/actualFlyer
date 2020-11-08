package com.kaloglu.actualflyer.presentation.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kaloglu.actualflyer.R
import com.kaloglu.actualflyer.databinding.FragmentProductsBinding
import com.kaloglu.actualflyer.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment :
    BaseFragment<FragmentProductsBinding>(R.layout.fragment_products) {
    override val viewModel: ProductsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}