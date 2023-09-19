package com.jeikenberg.boardgamesgalore.data

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.util.GAME_SAVE_DIRECTORY
import com.jeikenberg.boardgamesgalore.util.MediaStoreImage
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.annotation.Nonnull
import javax.annotation.Nullable

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
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
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

    fun retrieveImage(
        @Nonnull contentResolver: ContentResolver,
        @Nonnull game: Game
    ): MediaStoreImage? {
        var image: MediaStoreImage? = null

//        withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf("${game.name}.jpg")

        contentResolver.query(
            Uri.parse(Uri.decode(game.gameIconUri)),
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            cursor.moveToFirst()
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)


            val id = cursor.getLong(idColumn)
            val dateModified = Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
            val displayName = cursor.getString(displayNameColumn)

            val contentUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )

            image = MediaStoreImage(id, displayName, dateModified, contentUri)

        }
//        }
        return image
    }
}