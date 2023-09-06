package com.jeikenberg.boardgamesgalore.data.gameimage

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class GameImageRepository @Inject constructor(
//    val gameImageDao: GameImageDao
//) {
//    companion object {
//        @Volatile
//        var instance: GameImageRepository? = null
//
//        fun getInstance(gameImageDao: GameImageDao) =
//            instance ?: synchronized(this) {
//                instance ?: GameImageRepository(gameImageDao).also { instance = it }
//            }
//    }
//
//    fun getGameImages() : Flow<List<GameImage>> = gameImageDao.getGameImages()
//
//    fun getGameImage(gameId: Long) : Flow<GameImage> = gameImageDao.getGameImageById(gameId)
//
//    suspend fun insertGameImage(gameImage: GameImage) = gameImageDao.insertGameImage(gameImage)
//
//    suspend fun deleteGameImage(gameImage: GameImage) = gameImageDao.deleteGameImage(gameImage)
//
//    suspend fun updateGameImage(gameImage: GameImage) = gameImageDao.updateGameImage(gameImage)
//}