package com.jeikenberg.boardgamesgalore.data.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeikenberg.boardgamesgalore.util.GAME_DATABASE_GAME_TABLE_NAME

@Entity(tableName = GAME_DATABASE_GAME_TABLE_NAME)
data class Game(
    var name: String,
    var maker: String,
    var rating: Double,
    var weight: Double,
    var numberOfPlayers: String,
    var playTime: String,
    var description: String,
    var gameIconUri: String
) {
    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}