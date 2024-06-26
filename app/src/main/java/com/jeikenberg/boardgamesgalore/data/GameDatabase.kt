package com.jeikenberg.boardgamesgalore.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.data.game.GameDao
import com.jeikenberg.boardgamesgalore.util.GAME_DATABASE_NAME
import com.jeikenberg.boardgamesgalore.util.GAME_PRE_LOAD_DATABASE_NAME

@Database(entities = [Game::class/*, GameImage::class*/], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
//    abstract fun gameImageDao(): GameImageDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            val instance = INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
            val version = instance.openHelper.readableDatabase.version
            return instance
        }

        private fun buildDatabase(context: Context): GameDatabase {
            return Room.databaseBuilder(context, GameDatabase::class.java, GAME_DATABASE_NAME)
//                .createFromAsset(GAME_PRE_LOAD_DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}