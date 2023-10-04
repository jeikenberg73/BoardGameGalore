package com.jeikenberg.boardgamesgalore.ui.gamedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.navigation.GameDestination
import com.jeikenberg.boardgamesgalore.navigation.GameInfo
import com.jeikenberg.boardgamesgalore.navigation.gameTabRowScreens
import com.jeikenberg.boardgamesgalore.ui.components.GameBottomNavBar
import com.jeikenberg.boardgamesgalore.util.navigateSingleTopTo

@Composable
fun GameInfoScreen(
    gameId: Long?,
    onTabSelection: (GameDestination, Long) -> Unit,
    modifier: Modifier
) {
    Scaffold(
        bottomBar = {
            GameBottomNavBar(
                allScreens = gameTabRowScreens,
                onTabSelected = { newScreen ->
                    onTabSelection(newScreen, gameId!!)
                },
                currentScreen = GameInfo
            )
        },
        modifier = modifier
    ) {innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                style = MaterialTheme.typography.displayLarge,
                text = "Game Info"
            )
        }
    }
}