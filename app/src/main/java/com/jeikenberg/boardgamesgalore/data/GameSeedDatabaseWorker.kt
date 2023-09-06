package com.jeikenberg.boardgamesgalore.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.util.GAME_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class GameSeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(GAME_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use {jsonReader ->
                    val gameType = object : TypeToken<List<Game>>() {}.type
                    val games: List<Game> = Gson().fromJson(jsonReader, gameType)
                    val database = GameDatabase.getInstance(applicationContext)
                    database.gameDao().insertAllGames(games)
                    Result.success()
                }
            }
        } catch(ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}