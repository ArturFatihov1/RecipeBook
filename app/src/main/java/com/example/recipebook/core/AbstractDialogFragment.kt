package com.example.recipebook.core

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class AbstractDialogFragment<B : ViewBinding> : DialogFragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected abstract fun inflate(inflater: LayoutInflater): B

    open val isCancelableDialog: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = inflate(layoutInflater)

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setCancelable(isCancelableDialog)
            .create()
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}