package com.jeikenberg.boardgamesgalore.di

import com.jeikenberg.boardgamesgalore.data.game.GameDao
import javax.inject.Inject

class GameRepositoryImp @Inject constructor(
    private val gameDao: GameDao
) {
}