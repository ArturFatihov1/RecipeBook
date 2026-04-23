package com.example.recipebook.detail.core

import com.example.recipebook.core.RunAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

class FakeRunAsync : RunAsync {
    private var result: Any? = null
    private var ui: (Any) -> Unit = {}

    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        result = heavyOperation.invoke()
        ui = uiUpdate as (Any) -> Unit
    }

    fun returnResult() {
        ui.invoke(result!!)
    }
}