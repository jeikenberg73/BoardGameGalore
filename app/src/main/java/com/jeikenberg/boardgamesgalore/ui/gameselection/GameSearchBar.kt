package com.jeikenberg.boardgamesgalore.ui.gameselection
//
//import android.content.Context
//import android.net.Uri
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.Icon
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
//import com.bumptech.glide.integration.compose.GlideImage
//import com.jeikenberg.boardgamesgalore.R
//import com.jeikenberg.boardgamesgalore.data.game.Game
//import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
//import com.jeikenberg.boardgamesgalore.ui.theme.GreenGradiantBackgroundStart
//import com.jeikenberg.boardgamesgalore.ui.theme.GreenGradiantBackgroundStop
//import com.jeikenberg.boardgamesgalore.util.Log
//import com.jeikenberg.boardgamesgalore.viewmodels.GameSelectionViewModel
//import java.io.InputStream
//
//@Composable
//fun GameSearchBar(
//    searchText: String,
//    games: List<Game>,
//    isSearching: Boolean,
//    modifier: Modifier,
//    onValueChange: () -> Unit
//) {
////    val searchText by viewModel.searchText.collectAsState()
////    val games by viewModel.searchedGames.collectAsState()
////    val isSearching by viewModel.isSearching.collectAsState()
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        TextField(
//            value = searchText,
//            onValueChange = onValueChange,
//            modifier = modifier.fillMaxWidth(),
//            placeholder = { Text(text = "Search") }
//        )
//        Spacer(modifier = modifier.height(16.dp))
//        games?.let { localGames ->
//            if(isSearching) {
//                Box(modifier = modifier.fillMaxSize()) {
//                    CircularProgressIndicator(
//                        modifier =  modifier.align(Alignment.Center)
//                    )
//                }
//            } else {
//                LazyColumn(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                ) {
//                    items(localGames) {game ->
//                        // Need to create list item.
//                       GameSearchItem(
//                           game = game,
//                           modifier = modifier
//                       )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//fun GameSearchItem(
//    game: Game,
//    modifier: Modifier
//) {
//    val colorStops = arrayOf(
//        0.0f to GreenGradiantBackgroundStart,
//        1.0f to GreenGradiantBackgroundStop
//    )
//    Card(
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//        modifier = modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//    ) {
//        Box(
//            modifier = modifier
//                .fillMaxSize()
//                .background(Brush.verticalGradient(colorStops = colorStops))
//        ){
//            Row(
//                modifier = modifier
//                    .fillMaxSize()
//            ) {
//                // Use GlideImage when ready to view on phone
//                if(checkUri(LocalContext.current, game.gameIconUri)){
//                    GlideImage(
//                        model = Uri.parse(game.gameIconUri),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .align(Alignment.CenterVertically)
//                            .size(50.dp)
//                            .clip(CircleShape)
//                    )
//                } else {
//                    Image(
//                        painter = painterResource(R.drawable.token),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .align(Alignment.CenterVertically)
//                            .size(50.dp)
//                            .clip(CircleShape)
//                    )
//                }
//                Column(
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                ) {
//                    Text(
//                        text = game.name,
//                        style = MaterialTheme.typography.headlineMedium,
//                        modifier = Modifier
//                    )
//                    Text(
//                        text = "By: ${game.maker}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier
//                    )
//                }
//
//            }
//        }
//    }
//}
//
//private fun checkUri(context: Context, uri: String) : Boolean {
//    var inputStr: InputStream? = null
//    try{
//        inputStr = context.contentResolver.openInputStream(Uri.parse(uri))
//    } catch (e: Exception) {
//        Log.d("TAG", "Exception $e")
//    }
//
//    val exists = inputStr != null
//
//    inputStr?.close()
//
//    return exists
//}
//
//fun getFakeGame(): Game {
//    return Game(
//        "GloomHaven",
//        "GloomTeam",
//        8.6,
//        3.6,
//        "1-4",
//        "60-120",
//        "Fun campaign game.",
//        "content://media/external/images/media/31"
//    )
//}
//
//fun getFakeGameList(): List<Game> {
//    val games = ArrayList<Game>()
//
//    games.add(getFakeGame())
//
//    return games
//}
//
//@Preview(showBackground = true, widthDp = 320, heightDp = 80)
//@Composable
//fun GameSearchBarPreview() {
//    GameSearchBar(
//        searchText = "Search",
//        games = getFakeGameList(),
//        isSearching = false,
//        modifier = Modifier
//    )
//}
//
//@Preview(showBackground = true, widthDp = 320, heightDp = 80)
//@Composable
//fun GameSearchItemPreview() {
//    BoardGamesGaloreTheme {
//        GameSearchItem(
//            game = getFakeGame(),
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}