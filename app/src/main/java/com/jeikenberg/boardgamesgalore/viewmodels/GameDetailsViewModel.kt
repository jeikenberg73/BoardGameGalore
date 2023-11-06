package com.jeikenberg.boardgamesgalore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.data.game.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val gameRepository: GameRepository/*,
    gameImageRepository: GameImageRepository*/
) : ViewModel() {

    fun getGameById(gameId: Long): Game? {
        var game: Game? = null
        viewModelScope.launch(Dispatchers.IO) {
            game = gameRepository.getGameById(gameId)
        }
        return game
    }
}
