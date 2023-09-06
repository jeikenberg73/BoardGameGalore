package com.jeikenberg.boardgamesgalore.data.gameimage

//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface GameImageDao {
//    @Query("SELECT * FROM game_image_table")
//    fun getGameImages(): Flow<List<GameImage>>
//
//    @Query("SELECT * FROM game_image_table WHERE id = :imageId")
//    fun getGameImageById(imageId: Long): Flow<GameImage>
//
//    @Insert
//    fun insertGameImage(gameImage: GameImage)
//
//    @Delete
//    fun deleteGameImage(gameImage: GameImage)
//
//    @Update
//    fun updateGameImage(gameImage: GameImage)
//}