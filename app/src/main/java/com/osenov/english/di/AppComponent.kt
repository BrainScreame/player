package com.osenov.english.di

import android.content.Context
import com.osenov.english.ApplicationVideo
import com.osenov.english.ui.main.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(applicationVideo: ApplicationVideo) : Builder


        fun build() : AppComponent
    }
}