package com.jeikenberg.boardgamesgalore.viewmodels

import androidx.lifecycle.ViewModel
import com.jeikenberg.boardgamesgalore.data.game.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    gameRepository: GameRepository/*,
    gameImageRepository: GameImageRepository*/
) : ViewModel() {
}
