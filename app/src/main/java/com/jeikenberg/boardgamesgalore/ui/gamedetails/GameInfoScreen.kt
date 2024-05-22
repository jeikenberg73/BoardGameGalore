package com.jeikenberg.boardgamesgalore.ui.gamedetails

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.navigation.GameDestination
import com.jeikenberg.boardgamesgalore.navigation.GameInfo
import com.jeikenberg.boardgamesgalore.navigation.gameTabRowScreens
import com.jeikenberg.boardgamesgalore.ui.components.GameBottomNavBar
import com.jeikenberg.boardgamesgalore.ui.theme.GreenGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.GreenGradiantBackgroundStop
import com.jeikenberg.boardgamesgalore.util.DetailsTopBar
import com.jeikenberg.boardgamesgalore.viewmodels.AddEditGameViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameInfoScreen(
    gameId: Long?,
    onTabSelection: (GameDestination, Long) -> Unit,
    onBackPress: () -> Unit,
    modifier: Modifier
) {
    val colorStops = arrayOf(
        0.0f to GreenGradiantBackgroundStart,
        1.0f to GreenGradiantBackgroundStop
    )
    var game: Game? = null
    gameId?.let { id ->
        val viewModel: AddEditGameViewModel = hiltViewModel()
        game = viewModel.getGameById(id)
    }
    game?.let { localGame ->
        Scaffold(
            topBar = {
                DetailsTopBar(
                    onBackPress = { onBackPress() },
                    gameDestination = GameInfo,
                    modifier = modifier
                )
            },
            bottomBar = {
                GameBottomNavBar(
                    allScreens = gameTabRowScreens,
                    onTabSelected = { newScreen ->
                        onTabSelection(newScreen, localGame.gameId)
                    },
                    currentScreen = GameInfo
                )
            },
            modifier = modifier
        ) {innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
            ) {
                Card(
                    modifier = modifier
                ) {
                    // Originally was an Image but just trying GlideImage to see if it might help
                    // improve performance.
                    GlideImage(
                        model = Uri.parse(game?.gameIconUri),
                        contentDescription = null)
                }
            }
        }
    } ?: run {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxSize()
        ) {
            Text(
                style = MaterialTheme.typography.displayLarge,
                text = stringResource(R.string.game_detail_game_not_available)
            )
        }
    }

}