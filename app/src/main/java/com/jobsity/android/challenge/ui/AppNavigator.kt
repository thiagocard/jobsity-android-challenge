package com.jobsity.android.challenge.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppNavigator {

    private val _sharedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigate(route: String) {
        _sharedFlow.tryEmit(route)
    }

}