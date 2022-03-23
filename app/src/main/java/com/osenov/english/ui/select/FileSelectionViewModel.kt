package com.osenov.english.ui.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.osenov.english.data.VideoRepository
import com.osenov.english.ui.select.list.FilesPagingSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Provider


class FileSelectionViewModel @Inject constructor(private val repository: VideoRepository) :
    ViewModel() {
        var abc = "dsfsdf"

    val flow = Pager(
        PagingConfig(pageSize = 50, enablePlaceholders = true)
    ) {
        FilesPagingSource(repository)
    }.flow
        .cachedIn(viewModelScope)

} 