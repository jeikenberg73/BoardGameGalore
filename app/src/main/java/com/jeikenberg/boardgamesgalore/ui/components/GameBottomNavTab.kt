package com.jeikenberg.boardgamesgalore.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeikenberg.boardgamesgalore.navigation.GameDestination

@Composable
fun GameBottomNavTab(
    allScreens: List<GameDestination>,
    onTabSelected: (GameDestination) -> Unit,
    currentScreen: GameDestination
) {

}