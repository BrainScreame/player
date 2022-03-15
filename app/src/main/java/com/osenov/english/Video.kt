package com.osenov.english

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val id : String,
    val uri: Uri,
    val name: String,
    val size: Long,
    val duration: Long,
    val folderName: String
) : Parcelable