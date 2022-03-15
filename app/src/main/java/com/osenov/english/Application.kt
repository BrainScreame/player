package com.osenov.english

import android.app.Application
import com.osenov.english.di.AppComponent
import com.osenov.english.di.DaggerAppComponent

class Application : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()

    }
}
