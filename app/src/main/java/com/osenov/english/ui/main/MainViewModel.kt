package com.osenov.english.ui.main

import android.app.Application
import androidx.lifecycle.*

class MainViewModel(application: Application) : AndroidViewModel(application) {


   /* private val repository = VideoRepository(getApplication())

    private val videos: MutableLiveData<List<Video>> by lazy {
        MutableLiveData<List<Video>>().also {
            loadVideos()
        }
    }

    fun getVideos(): LiveData<List<Video>> {
        return videos
    }

    private fun loadVideos() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getAllVideo()
            }
            if (response.isNotEmpty()) {
                videos.value = response
                Log.d("TAG VIdeo", response.size.toString())
            } else {
                Log.d("TAG VIdeo", "null video")
            }

        }
    }
*/
}