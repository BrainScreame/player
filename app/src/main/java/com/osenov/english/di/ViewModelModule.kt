package com.osenov.english.di

import androidx.lifecycle.ViewModel
import com.osenov.english.data.VideoRepository
import com.osenov.english.ui.select.FileSelectionViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {
    @IntoMap
    @ViewModelKey(FileSelectionViewModel::class)
    @Provides
    fun provideCityListViewModel(
        repository: VideoRepository
    ): ViewModel {
        return FileSelectionViewModel(repository)
    }

}