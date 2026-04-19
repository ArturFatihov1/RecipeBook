package com.example.recipebook.core

import junit.framework.TestCase

class FakeClearViewModel : ClearViewModel {
    private var actual: Class<out BaseViewModel<*>>? = null

    override fun clear(viewModelClass: Class<out BaseViewModel<*>>) {
        actual = viewModelClass
    }

    fun assertClearCalled(expected: Class<out BaseViewModel<*>>) {
        TestCase.assertEquals(expected, actual)
    }
}