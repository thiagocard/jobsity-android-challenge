package com.jobsity.android.challenge.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class ViewModelTest {

    @Before
    open fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }

}
