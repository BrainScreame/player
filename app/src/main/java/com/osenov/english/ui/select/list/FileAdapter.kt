package com.osenov.english.ui.select.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osenov.english.databinding.ItemVideoBinding
import com.osenov.english.Video

class FileAdapter(private val videoClickListener : VideoClickListener) :
    PagingDataAdapter<Video, VideoViewHolder>(FileDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position), videoClickListener.clickListener)
    }
}


class VideoViewHolder(private val viewBinding: ItemVideoBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(video: Video?, clickListener: (Video) -> Unit) {

        val time = (video?.duration ?: 0) / 1000
        val timeMinute = time / 60
        val timeSeconds = time - (timeMinute * 60)
        val timeStr = "$timeMinute:${if (timeSeconds < 10) "0$timeSeconds" else "$timeSeconds"}"

        var size = (video?.size ?: 0) / (1024 * 1024)

        with(viewBinding) {
            textName.text = video?.name
            textInfo.text = size.toString() + "MB"
            textTimeVideo.text = timeStr
            textFolderName.text = video?.folderName


            Glide.with(viewBinding.root)
                .load(video?.uri)
                .centerCrop()
                .into(imageVideo)

        }

        viewBinding.root.setOnClickListener{
            if (video != null) {
                clickListener(video)
            }
        }
    }

}

private object FileDiffItemCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

}