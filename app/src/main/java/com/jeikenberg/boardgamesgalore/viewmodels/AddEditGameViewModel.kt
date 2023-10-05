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
import com.jeikenberg.boardgamesgalore.util.WHILE_SUBSCRIBE_TIMEOUT_MILLS
import com.mr0xf00.easycrop.ImageCropper
import com.mr0xf00.easycrop.crop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditGameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val imagePersistenceRepository: ImagePersistenceRepository,
    private val app: Application
) : AndroidViewModel(app) {

    private val imageCropper = ImageCropper()

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

    fun deleteImage(contentResolver: ContentResolver/*, imageName: String*/, imageUri: Uri): Boolean {
        return imagePersistenceRepository.removeImage(contentResolver/*, imageName*/, imageUri)
    }

    fun getImageByGame(contentResolver: ContentResolver, game: Game): MediaStoreImage? {
        return imagePersistenceRepository.retrieveImage(
            contentResolver,
            game.gameId,
            game.gameIconUri
        )
    }

    fun getImageByName(
        contentResolver: ContentResolver,
        gameId: Long,
        gameIconUri: String
    ): MediaStoreImage? {
        return imagePersistenceRepository.retrieveImage(contentResolver, gameId, gameIconUri)
    }

    fun setSelectedImage(uri: Uri) {
        viewModelScope.launch {
            val result = imageCropper.crop(uri, app)
        }
    }

    fun getGameById(gameId: Long): Game? {
        var game: Game? = null
        viewModelScope.launch(Dispatchers.IO) {
            game = gameRepository.getGameById(gameId)
        }
        return game
    }

    data class GameState(val game: Game)
}