package com.jobsity.android.challenge.ui

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Loaded<T>(val data: T) : ViewState<T>()
    data class Error(val throwable: Throwable) : ViewState<Nothing>()

    fun isLoaded() = this is Loaded
    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun errorMessage() = if (this is Error) this.throwable.message else null
}