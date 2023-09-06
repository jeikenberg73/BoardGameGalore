package com.jeikenberg.boardgamesgalore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface GameDestination {
    val route: String
}

object GameList: GameDestination {
    override val route = "gameList"
}

object GameInfo: GameDestination {
    override val route = "gameInfo"
    val icon = Icons.Filled.Info
    const val gameTypeArgs = "game_type"
    val routeWithArgs = "${route}/{${gameTypeArgs}}"
    val arguments = listOf(
        navArgument(gameTypeArgs) { type = NavType.LongType }
    )
}

object GameDescription: GameDestination {
    override val route = "gameDescription"
    val icon = Icons.Filled.Feed
    const val gameTypeArgs = "game_type"
    val routeWithArgs = "${route}/{${gameTypeArgs}}"
    val arguments = listOf(
        navArgument(gameTypeArgs) { type = NavType.LongType}
    )
}

object GameImages: GameDestination {
    override val route = "gameImages"
    val icon = Icons.Filled.Image
    const val gameTypeArgs = "game_type"
    val routeWithArgs = "${route}/{${gameTypeArgs}}"
    val arguments = listOf(
        navArgument(gameTypeArgs) { type = NavType.LongType }
    )
}

object GameImage: GameDestination {
    override val route = "gameImage"
}

object AddGame: GameDestination {
    override val route = "addGame"
}

object AddImage: GameDestination {
    override val route = "addImage"
}

val gameTabRowScreens = listOf(GameInfo, GameDescription, GameImages)