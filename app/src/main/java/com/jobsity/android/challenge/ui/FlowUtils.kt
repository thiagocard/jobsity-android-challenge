package com.jobsity.android.challenge.ui

import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<ViewState<T>>.resultToViewState(result: Result<T>) {
    if (result.isSuccess) {
        emit(ViewState.Loaded(result.getOrThrow()))
    } else {
        emit(ViewState.Error(result.exceptionOrNull()!!))
    }
}