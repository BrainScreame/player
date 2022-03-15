package com.osenov.english.ui

import android.provider.MediaStore
import android.provider.OpenableColumns

val VIDEO_FILE = arrayOf(
    MediaStore.Video.Media._ID,
    MediaStore.Video.Media.DISPLAY_NAME,
    OpenableColumns.SIZE,
    MediaStore.Video.VideoColumns.DURATION,
    MediaStore.Video.Media.ALBUM
)