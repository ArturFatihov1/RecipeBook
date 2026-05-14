package com.example.recipebook.recipelist.presentation

import android.view.LayoutInflater
import com.example.recipebook.core.AbstractDialogFragment
import com.example.recipebook.databinding.DialogErrorBinding

class ErrorDialogFragment : AbstractDialogFragment<DialogErrorBinding>() {
    override fun inflate(inflater: LayoutInflater): DialogErrorBinding = DialogErrorBinding.inflate(inflater)

    fun setText(title: String, body: String) {
        with(binding) {
            errorTitle.text = title
            errorBody.text = body
        }
    }
}