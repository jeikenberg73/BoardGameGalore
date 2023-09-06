package com.jeikenberg.boardgamesgalore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.data.game.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val gameRepository: GameRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLS = 5000L
    }

    val gameUiState: StateFlow<GameUiState> =
        gameRepository.getGamesStream()
            .map { GameUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = GameUiState()
            )


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchedGames = MutableStateFlow(gameUiState.value.games)
    val searchedGames = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_searchedGames) { text, _ ->
            val games = gameUiState.value.games
            if (text.isBlank()) {
                games
            } else {
                games.filter { nextGame ->
                    nextGame.doesMatchSearchQuery(text)
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

    suspend fun insertGame(game: Game) {
        CoroutineScope(viewModelScope.coroutineContext).launch {
            gameRepository.insertGame(game)
        }
    }
}

data class GameUiState(val games: List<Game> = listOf())