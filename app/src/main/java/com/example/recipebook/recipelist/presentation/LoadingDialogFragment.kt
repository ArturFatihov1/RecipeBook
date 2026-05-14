package com.example.recipebook.recipelist.presentation

import android.view.LayoutInflater
import com.example.recipebook.core.AbstractDialogFragment
import com.example.recipebook.databinding.DialogLoadingBinding

class LoadingDialogFragment : AbstractDialogFragment<DialogLoadingBinding>() {
    override fun inflate(inflater: LayoutInflater): DialogLoadingBinding = DialogLoadingBinding.inflate(inflater)
}