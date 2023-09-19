package com.jeikenberg.boardgamesgalore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jeikenberg.boardgamesgalore.navigation.GameNavHost
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
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

//@Composable
//fun GameList(gameList: List<Game>, modifier: Modifier = Modifier){
//    LazyColumn(modifier = modifier) {
//        items(items = gameList, key = { it.gameId}) {game ->
//            GameListItem(game = game, modifier = modifier)
//        }
//    }
//}
//
//@Composable
//fun GameListItem(game: Game, modifier: Modifier) {
//    Card(
//       modifier = modifier,
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = modifier
//        ) {
//            Text(text = game.name)
//            Spacer(Modifier.weight(1f))
//            Text(text = game.maker)
//        }
//    }
//}