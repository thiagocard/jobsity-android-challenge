package com.jobsity.android.challenge.ui

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
            ?: throw IllegalStateException("ViewState couldn't either get data nor error exception")
}