package com.osenov.english

import android.app.Application
import android.content.Context
import com.osenov.english.di.AppComponent
import com.osenov.english.di.DaggerAppComponent

class ApplicationVideo : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        _appComponent = DaggerAppComponent.builder()
            .application(applicationVideo = this)
            .build()

        super.onCreate()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is ApplicationVideo -> appComponent
        else -> (applicationContext as ApplicationVideo).appComponent
    }
