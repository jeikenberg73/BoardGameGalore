package com.jeikenberg.boardgamesgalore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeikenberg.boardgamesgalore.navigation.GameDescription
import com.jeikenberg.boardgamesgalore.navigation.GameDestination
import com.jeikenberg.boardgamesgalore.navigation.GameImages
import com.jeikenberg.boardgamesgalore.navigation.GameInfo
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme

@Composable
fun GameBottomNavBar(
    allScreens: List<GameDestination>,
    onTabSelected: (GameDestination) -> Unit,
    currentScreen: GameDestination
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    allScreens.forEachIndexed { index, navigationItem ->
        if (navigationItem == currentScreen) {
            selectedItem = index
        }
    }

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
            .height(70.dp)
    ) {
        allScreens.forEachIndexed { index, item ->
            BottomNavigationItem(
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.White,
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onTabSelected(item)
                }
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF777680)
@Composable
fun GameBottomNavBarPreview() {
    BoardGamesGaloreTheme {
        Scaffold(
            bottomBar = {
                GameBottomNavBar(
                    allScreens = listOf(GameInfo, GameDescription, GameImages),
                    onTabSelected = {},
                    currentScreen = GameInfo
                )
            }
        ) { innerPadding ->
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
}