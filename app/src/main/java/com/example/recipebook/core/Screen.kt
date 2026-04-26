package com.example.recipebook.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {
    fun show(containerId: Int, fragmentManager: FragmentManager)

    object Empty: Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) = Unit
    }

    object Back : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.popBackStack()
        }
    }

    abstract class Replace(private val fragment: Class<out Fragment>) : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(containerId, newFragment())
                .addToBackStack(null)
                .commit()
        }

        protected open fun newFragment(): Fragment = fragment.getDeclaredConstructor().newInstance()
    }
}