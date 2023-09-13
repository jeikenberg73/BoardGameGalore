package com.jeikenberg.boardgamesgalore.ui.gamedetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jeikenberg.boardgamesgalore.navigation.GameInfo
import com.jeikenberg.boardgamesgalore.navigation.gameTabRowScreens
import com.jeikenberg.boardgamesgalore.ui.components.GameBottomNavTab
import com.jeikenberg.boardgamesgalore.util.navigateSingleTopTo


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
