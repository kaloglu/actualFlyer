package com.kaloglu.actualflyer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.kaloglu.actualflyer.R
import com.kaloglu.actualflyer.util.dataBinding
import com.kaloglu.actualflyer.util.observe
import com.zhuinden.eventemitter.EventSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes val contentLayoutId: Int) :
    Fragment() {

    protected val binding by dataBinding<DB>(R.id.content_view)
    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentLayoutId, container, false).apply {
            id = R.id.content_view
        }
    }


    protected fun <T> Flow<T>.observe(action: suspend (T) -> Unit): Job {
        return onEach(action)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner, action)
    }

    protected fun <T> EventSource<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner, action)
    }

}