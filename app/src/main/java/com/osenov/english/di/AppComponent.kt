package com.osenov.english.di

import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    //fun inject(): Context
}