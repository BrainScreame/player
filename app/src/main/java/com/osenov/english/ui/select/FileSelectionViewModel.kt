package com.osenov.english.ui.select

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.osenov.english.data.VideoRepository
import com.osenov.english.ui.select.list.FilesPagingSource


class FileSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VideoRepository(context = getApplication())

    val flow = Pager(
        PagingConfig(pageSize = 50, enablePlaceholders = true)
    ) {
        FilesPagingSource(repository)
    }.flow
        .cachedIn(viewModelScope)


} 