package com.osenov.english.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.osenov.english.ui.VIDEO_FILE
import com.osenov.english.Video

//@Singleton
class VideoRepository /*@Inject constructor*/(private val context: Context) {


    fun getVideos(limit: Int, offset: Int): ArrayList<Video> {

        val videoList = ArrayList<Video>()
        val uriExternal = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val bundle = Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
                putString(
                    ContentResolver.QUERY_ARG_SQL_SORT_ORDER,
                    MediaStore.Video.VideoColumns.DURATION + " DESC"
                )

            }
            context.contentResolver.query(
                uriExternal,
                VIDEO_FILE,
                bundle,
                null,
            )
        } else {
            context.contentResolver.query(
                uriExternal,
                VIDEO_FILE,
                null,
                null,
                MediaStore.Video.Media.DURATION + " DESC LIMIT $limit OFFSET $offset"
            )
        }?.use {
            while (it.moveToNext()) {

                val videoId = it.getString(it.getColumnIndex(VIDEO_FILE[0]))
                val uriVideo = Uri.withAppendedPath(uriExternal, "" + videoId)

                val file = Video(
                    videoId,
                    uriVideo,
                    it.getString(it.getColumnIndex(VIDEO_FILE[1])),
                    it.getLong(it.getColumnIndex(VIDEO_FILE[2])),
                    it.getLong(it.getColumnIndex(VIDEO_FILE[3])),
                    it.getString(it.getColumnIndex(VIDEO_FILE[4]))
                )
                videoList.add(file)
            }
            it.close()
        }

        return videoList
    }


}
