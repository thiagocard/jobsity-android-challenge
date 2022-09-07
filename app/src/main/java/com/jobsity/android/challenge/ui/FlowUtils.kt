package com.jobsity.android.challenge.ui

import com.jobsity.android.challenge.ext.exception
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<ViewState<T>>.resultToViewState(result: Result<T>) {
    if (result.isSuccess) {
        emit(ViewState.Loaded(result.getOrThrow()))
    } else {
        emit(ViewState.Error(result.exception()))
    }
}