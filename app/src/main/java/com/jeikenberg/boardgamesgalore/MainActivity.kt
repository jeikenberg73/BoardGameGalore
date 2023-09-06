package com.jeikenberg.boardgamesgalore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.navigation.GameNavHost
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
import com.jeikenberg.boardgamesgalore.viewmodels.GameSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoardGamesGaloreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GameNavHost()
                }
            }
        }
    }
}

@Composable
fun GameList(gameList: List<Game>, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier) {
        items(items = gameList, key = { it.gameId}) {game ->
            GameListItem(game = game, modifier = modifier)
        }
    }
}

@Composable
fun GameListItem(game: Game, modifier: Modifier) {
    Card(
       modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = modifier
        ) {
            Text(text = game.name)
            Spacer(Modifier.weight(1f))
            Text(text = game.maker)
        }
    }
}