package com.jeikenberg.boardgamesgalore.ui.gameselection

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.ui.theme.GreenGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.GreenGradiantBackgroundStop
import com.jeikenberg.boardgamesgalore.util.Log
import java.io.InputStream

// THIS IS A TEST!!!

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSelectionScreen(
    searchText: String,
    gameList: List<Game>,
    isSearching: Boolean = false,
    onValueChange: (String) -> Unit,
    onAddGameClicked: () -> Unit,
    modifier: Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddGameClicked
                },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 5.dp
                ),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }

        }
    ) { innerPadding ->
        if (gameList.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = "No Games In Database. \nPlease add a game.",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                GameSearchBar(
                    searchText = searchText,
                    onValueChange = onValueChange,
                    modifier = modifier
                )
                Spacer(modifier = modifier.height(16.dp))
                if (isSearching) {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = modifier
                            .padding(innerPadding)
                    ) {
                        items(items = gameList, key = { it.gameId }) { game ->
                            GameSearchItem(onGameClicked = {}, game = game, modifier = modifier)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameSearchItem(
    onGameClicked: (Long) -> Unit,
    game: Game,
    modifier: Modifier
) {
    val colorStops = arrayOf(
        0.0f to GreenGradiantBackgroundStart,
        1.0f to GreenGradiantBackgroundStop
    )
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .clickable {
                        onGameClicked
                    }
            ) {
                // Use GlideImage when ready to view on phone
                if (checkUri(LocalContext.current, game.gameIconUri)) {
                    GlideImage(
                        model = Uri.parse(game.gameIconUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.token),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = game.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                    )
                    Text(
                        text = "By: ${game.maker}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                    )
                }

            }
        }
    }
}

private fun checkUri(context: Context, uri: String): Boolean {
    var inputStr: InputStream? = null
    try {
        inputStr = context.contentResolver.openInputStream(Uri.parse(uri))
    } catch (e: Exception) {
        Log.d("TAG", "Exception $e")
    }

    val exists = inputStr != null

    inputStr?.close()

    return exists
}

fun getFakeGame(): Game {
    return Game(
        0L,
        "GloomHaven",
        "GloomTeam",
        8.6,
        3.6,
        "1-4",
        "60-120",
        "Fun campaign game.",
        "content://media/external/images/media/31"
    )
}

fun getFakeGameList(): List<Game> {
    val games = ArrayList<Game>()

    val game1 = Game(
        0L,
        "GloomHaven",
        "GloomTeam",
        8.6,
        3.6,
        "1-4",
        "60-120",
        "Fun campaign game.",
        "content://media/external/images/media/31"
    )

    val game2 = Game(
        1L,
        "Terraforming Mars",
        "FryxGames",
        8.4,
        3.26,
        "1-5",
        "120",
        "Work to terraform mars.",
        "content://media/external/images/media/31"
    )

    val game3 = Game(
        2L,
        "Dominion",
        "Rio Grande Games",
        7.6,
        2.35,
        "2-4",
        "30",
        "Deck building game.",
        "content://media/external/images/media/31"
    )

    val game4 = Game(
        3L,
        "Catan",
        "KOSMOS",
        7.1,
        2.3,
        "3-4",
        "60-120",
        "Trade and build cities.",
        "content://media/external/images/media/31"
    )

    val game5 = Game(
        4L,
        "Beyond the Sun",
        "Rio Grande Games",
        8.0,
        3.12,
        "2-4",
        "60-120",
        "Tech tree and planet colonization.",
        "content://media/external/images/media/31"
    )

    games.add(game1)
    games.add(game2)
    games.add(game3)
    games.add(game4)
    games.add(game5)

    return games
}

@Preview(showBackground = true)
@Composable
fun GameSearchEmptyPreview() {
    GameSelectionScreen(
        searchText = "",
        gameList = listOf(),
        onValueChange = {},
        onAddGameClicked = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GameSelectionScreenAllGamesPreview() {
    val games = getFakeGameList()
    GameSelectionScreen(
        searchText = "",
        gameList = games,
        onValueChange = {},
        onAddGameClicked = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true, widthDp = 320, heightDp = 80)
@Composable
fun GameSearchItemPreview() {
    GameSearchItem(
        game = getFakeGame(),
        onGameClicked = {},
        modifier = Modifier.fillMaxWidth()
    )
}

