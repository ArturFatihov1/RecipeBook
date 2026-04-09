package com.example.recipebook.detail.core

import kotlinx.coroutines.CoroutineScope

interface RunAsync {

    fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    )
}
