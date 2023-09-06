package com.jeikenberg.boardgamesgalore.data.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeikenberg.boardgamesgalore.util.GAME_DATABASE_GAME_TABLE_NAME

@Entity(tableName = GAME_DATABASE_GAME_TABLE_NAME)
data class Game(
    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0,
    val name: String,
    val maker: String,
    val rating: Double,
    val weight: Double,
    val numberOfPlayers: String,
    val playTime: String,
    val description: String,
    val gameIconUri: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}