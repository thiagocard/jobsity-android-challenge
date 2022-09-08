package com.jobsity.android.challenge.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.jobsity.android.challenge.ui.common.Loading

sealed class ViewState<out T> {
    object Idle : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Loaded<T>(val data: T) : ViewState<T>()

    data class Error(val throwable: Throwable) : ViewState<Nothing>()

    fun isLoaded() = this is Loaded
    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun errorMessage() = if (this is Error) this.throwable.message else null
    fun dataOrThrow() =
        if (this is Loaded) this.data else (this as? Error)?.throwable?.let { throw it }
            ?: error("ViewState couldn't either get data nor error exception")

    fun dataOrNull() = if (this is Loaded) this.data else null
}

@Composable
fun <T> ViewStateHandler(
    viewState: ViewState<T>,
    onLoading: (@Composable () -> Unit)? = null,
    onError: @Composable () -> Unit,
    content: @Composable (T) -> Unit,
) {
    when (viewState) {
        is ViewState.Error -> onError()
        ViewState.Idle -> Box {}
        is ViewState.Loaded -> content(viewState.dataOrThrow())
        ViewState.Loading -> onLoading?.invoke() ?: Loading()
    }
}