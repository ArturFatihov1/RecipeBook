package com.example.recipebook.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class AbstractFragment<UiState : Any, B : ViewBinding, VM : BaseViewModel.Abstract<UiState>> : Fragment() {

    private var _binding: B? = null

    protected val binding get() = _binding!!

    protected lateinit var viewModel: VM

    protected abstract fun update(): (UiState) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflate(inflater, container)
        return binding.root
    }

    protected abstract fun inflate(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update())
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }
}