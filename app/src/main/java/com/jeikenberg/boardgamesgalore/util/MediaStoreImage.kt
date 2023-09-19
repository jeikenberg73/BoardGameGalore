package com.jeikenberg.boardgamesgalore.util

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.Date

data class MediaStoreImage(
    val id: Long,
    val imageName: String,
    val dateAdded: Date,
    val contentUri: Uri
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreImage>() {
            override fun areItemsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ) = oldItem == newItem
        }
    }
}