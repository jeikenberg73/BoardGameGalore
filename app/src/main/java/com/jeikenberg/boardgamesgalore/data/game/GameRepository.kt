package com.jeikenberg.boardgamesgalore.data.game

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val gameDao: GameDao
){
    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(gameDao: GameDao) =
            instance ?: synchronized(this) {
                instance ?: GameRepository(gameDao).also { instance = it }
            }
    }

    fun getGamesStream(): Flow<List<Game>> = gameDao.getGames()

    fun getGameByIdStream(gameId: Long): Flow<Game> = gameDao.getGameById(gameId)

    fun getGameByNameStream(gameName: String): Flow<Game> = gameDao.getGameByName(gameName)

    suspend fun insertGame(game: Game) = gameDao.insert(game)

    suspend fun insertGames(games: List<Game>) = gameDao.insertAllGames(games)

    suspend fun deleteGame(game: Game) = gameDao.deleteGame(game)

    suspend fun updateGame(game: Game) = gameDao.updateGame(game)
}