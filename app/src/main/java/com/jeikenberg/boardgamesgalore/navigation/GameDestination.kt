package com.jeikenberg.boardgamesgalore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface GameDestination {
    val icon: ImageVector
    val route: String
    val label: String
}

object GameList : GameDestination {
    override val icon = Icons.Filled.List
    override val route = "gameList"
    override val label = "Game List"
}

object AddGame : GameDestination {
    override val icon = Icons.Filled.Add
    override val route = "addGame"
    override val label = "Add Game"
//    const val gameIdArgs = "game_id"
//    val routeWithArgs = "${route}/{${gameIdArgs}}"
//    val arguments = listOf(
//        navArgument(gameIdArgs) { type = NavType.LongType }
//    )
}

object GameInfo : GameDestination {
    override val route = "gameInfo"
    override val icon = Icons.Filled.Info
    override val label = "Info"
    const val gameIdArgs = "game_id"
    val routeWithArgs = "${route}/{${gameIdArgs}}"
    val arguments = listOf(
        navArgument(gameIdArgs) { type = NavType.LongType }
    )
}

object GameDescription : GameDestination {
    override val route = "gameDescription"
    override val icon = Icons.Filled.Feed
    override val label = "Description"
    const val gameIdArgs = "game_id"
    val routeWithArgs = "${route}/{${gameIdArgs}}"
    val arguments = listOf(
        navArgument(gameIdArgs) { type = NavType.LongType }
    )
}

object GameImages : GameDestination {
    override val route = "gameImages"
    override val icon = Icons.Filled.Image
    override val label = "Images"
    const val gameIdArgs = "game_id"
    val routeWithArgs = "${route}/{${gameIdArgs}}"
    val arguments = listOf(
        navArgument(gameIdArgs) { type = NavType.LongType }
    )
}

object UploadImage : GameDestination {
    override val icon = Icons.Filled.Image
    override val route = "uploadImage"
    override val label = "Upload Image"
}

object GameImage : GameDestination {
    override val icon = Icons.Filled.Image
    override val route = "gameImage"
    override val label = "Game Image"
}

object TakePicture : GameDestination {
    override val icon = Icons.Filled.Image
    override val route = "takePicture"
    override val label = "Take Picture"
}

object AddImage : GameDestination {
    override val icon = Icons.Filled.Add
    override val route = "addImage"
    override val label = "Add Image"
}

val gameTabRowScreens = listOf(GameInfo, GameDescription, GameImages)