package com.jeikenberg.boardgamesgalore.viewmodels

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeikenberg.boardgamesgalore.data.ImagePersistenceRepository
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.data.game.GameRepository
import com.jeikenberg.boardgamesgalore.util.MediaStoreImage
import com.jeikenberg.boardgamesgalore.util.WHILE_SUBSCRIBE_TIMEOUT_MILLS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class GameSelectionViewModel @Inject constructor(
    gameRepository: GameRepository,
    private val imagePersistenceRepository: ImagePersistenceRepository
) : ViewModel() {

    val gameUiState: StateFlow<GameUiState> =
        gameRepository.getGamesStream()
            .map {listOfGame ->
                _searchedGames.emit(listOfGame)
                GameUiState(listOfGame)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBE_TIMEOUT_MILLS),
                initialValue = GameUiState()
            )

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _searchedGames = MutableStateFlow(gameUiState.value.games)
    val searchedGames = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_searchedGames) { text, _ ->
            val games = _searchedGames.value
            if (text.isBlank()) {
                games
            } else {
                val filteredList = games.filter { nextGame ->
                    nextGame.doesMatchSearchQuery(text)
                }
                filteredList.ifEmpty {
                    games
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchedGames.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getImageByGame(contentResolver: ContentResolver, game: Game): MediaStoreImage? {
       return imagePersistenceRepository.retrieveImage(contentResolver, game.gameId, game.gameIconUri)
    }

    fun getImageById(contentResolver: ContentResolver, gameId: Long, gameIconUri: String): MediaStoreImage? {
        return imagePersistenceRepository.retrieveImage(contentResolver, gameId, gameIconUri)
    }
}

data class GameUiState(val games: List<Game> = listOf())

data class GameIdState(val ids: List<Long> = listOf())