package com.jeikenberg.boardgamesgalore.ui.gamedetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jeikenberg.boardgamesgalore.navigation.GameDescription
import com.jeikenberg.boardgamesgalore.navigation.GameDestination
import com.jeikenberg.boardgamesgalore.navigation.GameInfo
import com.jeikenberg.boardgamesgalore.navigation.GameList
import com.jeikenberg.boardgamesgalore.navigation.gameTabRowScreens
import com.jeikenberg.boardgamesgalore.navigation.navigateSingleTopTo
import com.jeikenberg.boardgamesgalore.ui.components.GameBottomNavTab


@Composable
fun GameDetailsScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = gameTabRowScreens.find { it.route == currentDestination?.route } ?: GameInfo
    Scaffold(
        bottomBar = {
            GameBottomNavTab(
                allScreens = gameTabRowScreens,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen
            )
        },
        modifier = modifier
    ) {
        GameInfoScreen(onClickDescription = {}, onClickImages = {}, modifier = Modifier.padding(it))
    }
}
