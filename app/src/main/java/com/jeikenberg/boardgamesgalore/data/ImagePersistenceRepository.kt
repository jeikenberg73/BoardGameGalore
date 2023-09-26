package com.jeikenberg.boardgamesgalore.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import androidx.core.net.toFile
import com.jeikenberg.boardgamesgalore.util.GAME_SAVE_DIRECTORY
import com.jeikenberg.boardgamesgalore.util.MediaStoreImage
import java.io.File
import java.io.IOException
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.annotation.Nonnull


class ImagePersistenceRepository {
    companion object {
        @Volatile
        private var INSTANCE: ImagePersistenceRepository? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ImagePersistenceRepository().also { INSTANCE = it }
            }
    }

    fun saveImage(
        @Nonnull contentResolver: ContentResolver,
        @Nonnull imageName: String, bitmap: Bitmap
    ): Uri {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES +
                        File.separator + GAME_SAVE_DIRECTORY
            )
        }

        var uri: Uri? = null

        return kotlin.runCatching {
            with(contentResolver) {
                insert(Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
                    uri = it

                    openOutputStream(it)?.use { stream ->
                        if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream))
                            throw IOException("Failed to save bitmap.")
                    } ?: throw IOException("Failed to open output stream.")
                } ?: throw IOException("Failed to create new MediaStore record.")
            }
        }.getOrElse {
            uri?.let { orphanUri ->
                contentResolver.delete(orphanUri, null, null)
            }
            throw it
        }
    }

    fun removeImage(
        @Nonnull contentResolver: ContentResolver,
        @Nonnull imageUri: Uri
    ) {
        contentResolver.delete(imageUri, null)
//        imageUri.toFile().delete()
    }

    fun retrieveImage(
        @Nonnull contentResolver: ContentResolver,
        @Nonnull gameName: String,
        @Nonnull gameIconUri: String,
    ): MediaStoreImage? {
        var image: MediaStoreImage? = null

        val projection = arrayOf(
            Images.Media._ID,
            Images.Media.DISPLAY_NAME,
            Images.Media.DATE_ADDED
        )

        val selection = "${Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf("$gameName.jpg")

        contentResolver.query(
            Uri.parse(Uri.decode(gameIconUri)),
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(Images.Media._ID)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(Images.Media.DATE_ADDED)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(Images.Media.DISPLAY_NAME)

            cursor.moveToFirst()
            val id = cursor.getLong(idColumn)
            val dateModified = Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
            val displayName = cursor.getString(displayNameColumn)

            val contentUri = ContentUris.withAppendedId(
                Images.Media.EXTERNAL_CONTENT_URI,
                id
            )

            image = MediaStoreImage(id, displayName, dateModified, contentUri)

        }
        return image
    }
}