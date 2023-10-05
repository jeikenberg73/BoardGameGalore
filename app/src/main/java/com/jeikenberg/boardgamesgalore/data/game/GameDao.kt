package com.jeikenberg.boardgamesgalore.data.game

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeikenberg.boardgamesgalore.data.game.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game_table ORDER BY name")
    fun getGames(): Flow<List<Game>>

    @Query("SELECT * FROM game_table WHERE gameId = :gameId")
    fun getGameById(gameId: Long): Game

    @Query("SELECT * FROM game_table WHERE name = :gameName")
    fun getGameByName(gameName: String): Flow<Game>

    @Insert
    suspend fun insert(game: Game)

    @Insert
    suspend fun insertAllGames(games: List<Game>)

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)
}