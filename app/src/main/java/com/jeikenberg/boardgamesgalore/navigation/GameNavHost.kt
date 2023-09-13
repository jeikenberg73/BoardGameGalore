@file:Suppress("UNUSED_VARIABLE")

package com.jeikenberg.boardgamesgalore.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jeikenberg.boardgamesgalore.ui.gamedetails.GameInfoScreen
import com.jeikenberg.boardgamesgalore.ui.gameselection.GameSelectionScreen
import com.jeikenberg.boardgamesgalore.ui.utilscreens.AddGameScreen
import com.jeikenberg.boardgamesgalore.ui.utilscreens.TakePicture
import com.jeikenberg.boardgamesgalore.util.navigateSingleTopTo
import com.jeikenberg.boardgamesgalore.util.navigateToAddGame
import com.jeikenberg.boardgamesgalore.util.navigateToDetails
import com.jeikenberg.boardgamesgalore.viewmodels.GameSelectionViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun GameNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = GameList.route,
        modifier = modifier
    ) {
        composable(route = GameList.route) {
            val viewModel: GameSelectionViewModel = hiltViewModel()

            // Need this to pull data from database to initialize the database.
            val gameUiState by viewModel.gameUiState.collectAsState()

            val searchedGames by viewModel.searchedGames.collectAsState()
            val searchText by viewModel.searchText.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()
            GameSelectionScreen(
                searchText = searchText,
                gameList = searchedGames,
                isSearching = isSearching,
                onValueChange = viewModel::onSearchTextChange,
                onAddGameClicked = {
                    navController.navigateSingleTopTo(AddGame.route)
                },
                modifier = modifier
            )
        }
        composable(route = AddGame.route) {navBackResult ->
            val viewModel: GameSelectionViewModel = hiltViewModel()
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            navBackResult.savedStateHandle.get<Uri>("imageUri")?.let {
                imageUri = it
            }
            AddGameScreen(
                takePictureClick = {
                    navController.navigate(TakePicture.route)
                },
                uploadPictureClick = viewModel::uploadPicture,
                imageUri = imageUri,
                modifier = modifier
            )
        }
        composable(route = TakePicture.route) {
           TakePicture(
                    modifier = modifier,
                    onPictureTaken = {imageUri ->
                        navController.popBackStack()
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("imageUri", imageUri)
                    }
                )
        }
        composable(
            route = GameInfo.routeWithArgs,
            arguments = GameInfo.arguments
        ) { navBackStackEntry ->
            GameInfoScreen(
                gameType = navBackStackEntry.arguments?.getLong(GameInfo.gameTypeArgs),
                onClickDescription = { gameId ->
                    navController.navigateToDetails(GameDescription.route, gameId)
                },
                onClickImages = { gameId ->
                    navController.navigateToDetails(GameImages.route, gameId)
                },
                modifier = modifier
            )
        }
        composable(
            route = GameInfo.routeWithArgs,
            arguments = GameInfo.arguments
        ) { navBackStackEntry ->
            val gameType =
                navBackStackEntry.arguments?.getLong(GameInfo.gameTypeArgs) ?: 0
            GameInfoScreen(
                gameType = gameType,
                onClickDescription = {},
                onClickImages = {},
                modifier = modifier
            )
        }
    }
}