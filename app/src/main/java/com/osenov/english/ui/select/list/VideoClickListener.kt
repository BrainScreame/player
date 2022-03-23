package com.osenov.english.ui.select.list

import com.osenov.english.data.entities.Video

data class VideoClickListener(val clickListener: (video: Video) -> Unit)