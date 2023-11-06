package com.jeikenberg.boardgamesgalore.ui.gamedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jeikenberg.boardgamesgalore.navigation.GameDescription
import com.jeikenberg.boardgamesgalore.navigation.GameDestination
import com.jeikenberg.boardgamesgalore.navigation.gameTabRowScreens
import com.jeikenberg.boardgamesgalore.ui.components.GameBottomNavBar

@Composable
fun GameDescriptionScreen(
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
                currentScreen = GameDescription
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
                text = "Game Description"
            )
        }
    }
}