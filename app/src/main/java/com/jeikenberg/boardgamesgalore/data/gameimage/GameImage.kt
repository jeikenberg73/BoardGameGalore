package com.jeikenberg.boardgamesgalore.data.gameimage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jeikenberg.boardgamesgalore.data.game.Game

//@Entity(
//    tableName = "game_image_table",
//    foreignKeys = [
//        ForeignKey(entity = Game::class, parentColumns = ["id"], childColumns = ["game_id"])
//    ],
//    indices = [Index("game_id")]
//)
//data class GameImage(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    var imageId: Long = 0,
//    @ColumnInfo(name = "game_id")
//    val gameId: Long,
//    val imageDescription: String,
//    val imageName: String = ""
//
//) {
//}