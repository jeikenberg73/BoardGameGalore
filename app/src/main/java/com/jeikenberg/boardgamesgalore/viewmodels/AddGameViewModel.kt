package com.jeikenberg.boardgamesgalore.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jeikenberg.boardgamesgalore.data.ImagePersistenceRepository
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.data.game.GameRepository
import com.jeikenberg.boardgamesgalore.util.MediaStoreImage
import com.mr0xf00.easycrop.ImageCropper
import com.mr0xf00.easycrop.crop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val imagePersistenceRepository: ImagePersistenceRepository,
    private val app: Application
) : AndroidViewModel(app) {

    val imageCropper = ImageCropper()

    suspend fun insertGame(game: Game) {
        CoroutineScope(viewModelScope.coroutineContext).launch {
            gameRepository.insertGame(game)
        }
    }

    suspend fun updateGame(game: Game) {
        CoroutineScope(viewModelScope.coroutineContext).launch {
            gameRepository.updateGame(game)
        }
    }

    fun saveImage(contentResolver: ContentResolver, fileName: String, bitmap: Bitmap): Uri {
        return imagePersistenceRepository.saveImage(contentResolver, fileName, bitmap)
    }

    fun deleteImage(contentResolver: ContentResolver, imageUri: Uri) {
        imagePersistenceRepository.removeImage(contentResolver, imageUri)
    }

    fun getImageByGame(contentResolver: ContentResolver, game: Game): MediaStoreImage? {
        return imagePersistenceRepository.retrieveImage(
            contentResolver,
            game.name,
            game.gameIconUri
        )
    }

    fun getImageByName(
        contentResolver: ContentResolver,
        gameName: String,
        gameIconUri: String
    ): MediaStoreImage? {
        return imagePersistenceRepository.retrieveImage(contentResolver, gameName, gameIconUri)
    }

    fun setSelectedImage(uri: Uri) {
        viewModelScope.launch {
            val result = imageCropper.crop(uri, app)
        }
    }
}