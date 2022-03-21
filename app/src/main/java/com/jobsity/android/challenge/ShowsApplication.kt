package com.jobsity.android.challenge

import android.app.Application
import appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShowsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShowsApplication)
            modules(appModules)
        }
    }

}