package com.osenov.english.ui.select.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.osenov.english.data.entities.Video
import com.osenov.english.data.VideoRepository

class FilesPagingSource(private val repository: VideoRepository) : PagingSource<Int, Video>() {

    // the initial load size for the first page may be different from the requested size
    private var initialLoadSize: Int = 0

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        if (params is LoadParams.Prepend) {
            return LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null
            )
        }
        try {
            val nextPageNumber = params.key ?: 1

            if (params.key == null) {
                initialLoadSize = params.loadSize
            }

            val offsetCalc = {
                if (nextPageNumber == 2)
                    initialLoadSize
                else
                    ((nextPageNumber - 1) * params.loadSize) + (initialLoadSize - params.loadSize)
            }
            val offset = offsetCalc.invoke()
            val videos = repository.getVideos(params.loadSize, offset)
            val count = videos.size

            return LoadResult.Page(
                data = videos,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = if (count < params.loadSize) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}