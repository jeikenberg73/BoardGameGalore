@file:Suppress("UNUSED_VARIABLE")

package com.jeikenberg.boardgamesgalore.navigation

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.ui.addedit.AddGameScreen
import com.jeikenberg.boardgamesgalore.ui.addedit.TakePicture
import com.jeikenberg.boardgamesgalore.ui.addedit.UploadImageCompose
import com.jeikenberg.boardgamesgalore.ui.gamedetails.GameDescriptionScreen
import com.jeikenberg.boardgamesgalore.ui.gamedetails.GameImagesScreen
import com.jeikenberg.boardgamesgalore.ui.gamedetails.GameInfoScreen
import com.jeikenberg.boardgamesgalore.ui.gameselection.GameSelectionScreen
import com.jeikenberg.boardgamesgalore.util.AlertDialogComponent
import com.jeikenberg.boardgamesgalore.util.navigateSingleTopTo
import com.jeikenberg.boardgamesgalore.util.navigateToDetails
import com.jeikenberg.boardgamesgalore.viewmodels.AddEditGameViewModel
import com.jeikenberg.boardgamesgalore.viewmodels.GameSelectionViewModel
import com.mr0xf00.easycrop.rememberImageCropper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun GameNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val gameSelectionViewModel: GameSelectionViewModel = hiltViewModel()
    val addEditGameViewModel: AddEditGameViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imageCropper = rememberImageCropper()
    var uploadedImageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }
    var showAlertDialogDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var hasTakenPicture: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var alertDialogTitle: String by remember {
        mutableStateOf("")
    }
    var alertDialogMessage: String by remember {
        mutableStateOf("")
    }
    var game: Game? by rememberSaveable {
        mutableStateOf(null)
    }
    NavHost(
        navController = navController,
        startDestination = GameList.route,
        modifier = modifier
    ) {
        composable(route = GameList.route) {
            // Need this to pull data from database to initialize the database.
            val gameUiState by gameSelectionViewModel.gameUiState.collectAsStateWithLifecycle()

            val searchedGames by gameSelectionViewModel.searchedGames.collectAsStateWithLifecycle()
            val searchText by gameSelectionViewModel.searchText.collectAsStateWithLifecycle()
            val isSearching by gameSelectionViewModel.isSearching.collectAsStateWithLifecycle()
            GameSelectionScreen(
                viewModel = gameSelectionViewModel,
                searchText = searchText,
                gameList = searchedGames,
                isSearching = isSearching,
                onValueChange = gameSelectionViewModel::onSearchTextChange,
                onAddGameClicked = {
                    game = null
                    navController.navigateSingleTopTo(AddGame.route)
                },
                onEditGameClicked = { retrievedGame ->
                    game = retrievedGame
                    navController.navigateSingleTopTo(AddGame.route)
                },
                onGameClicked = { gameId ->
                    game = null
                    navController.navigateToDetails(GameInfo.route, gameId)
                },
                modifier = modifier
            )
        }
        composable(
            route = AddGame.route
        ) {
            val games by gameSelectionViewModel.searchedGames.collectAsState()
            AddGameScreen(
                viewModel = addEditGameViewModel,
                existingGame = game,
                games = games,
                updateGame = { updatedGame ->
                    game = updatedGame
                },
                takePictureClick = {
                    navController.navigate(TakePicture.route)
                },
                uploadPictureClick = {
                    navController.navigate(UploadImage.route)
                },
                bitmap = bitmap,
                setHasTakenPicture = { hasTakenPicture = it },
                hasTakenPicture = hasTakenPicture,
                onGameSaved = {
                    bitmap = null
                    imageUri = null
                    hasTakenPicture = false
                    navController.navigate(GameList.route)
                },
                onGameNotSaved = {
                    bitmap = null
                    imageUri = null
                    hasTakenPicture = false
                    navController.navigate(GameList.route)
                },
                onCancel = {
                    navController.navigate(GameList.route)
                },
                onTakePictureFailed = { title, message ->
                    alertDialogTitle = title
                    alertDialogMessage = message
                    showAlertDialogDialog = true
                },
                modifier = modifier
            )
        }
        composable(route = TakePicture.route) {
            TakePicture(
                imageCropper = imageCropper,
                onPictureTaken = { localBitmap ->
                    bitmap = localBitmap
                    navController.popBackStack()
                },
                onPictureCancel = {
                    hasTakenPicture = false
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            route = UploadImage.route
        ) {
            UploadImageCompose(
                onImageChosen = { localBitmap ->
                    bitmap = localBitmap
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            route = GameInfo.routeWithArgs,
            arguments = GameInfo.arguments
        ) { navBackStackEntry ->
            GameInfoScreen(
                gameId = navBackStackEntry.arguments?.getLong(GameInfo.gameIdArgs),
                onTabSelection = { gameDestination, gameId ->
                    navController.navigateToDetails(gameDestination.route, gameId)
                },
                modifier = modifier
            )
        }
        composable(
            route = GameDescription.routeWithArgs,
            arguments = GameDescription.arguments
        ) { navBackStackEntry ->
            GameDescriptionScreen(
                gameId = navBackStackEntry.arguments?.getLong(GameDescription.gameIdArgs),
                onTabSelection = { gameDestination, gameId ->
                    navController.navigateToDetails(gameDestination.route, gameId)
                },
                modifier = modifier
            )
        }
        composable(
            route = GameImages.routeWithArgs,
            arguments = GameImages.arguments
        ) { navBackStackEntry ->
            GameImagesScreen(
                gameId = navBackStackEntry.arguments?.getLong(GameImages.gameIdArgs),
                onTabSelection = { gameDestination, gameId ->
                    navController.navigateToDetails(gameDestination.route, gameId)
                },
                modifier = modifier
            )
        }
    }
    AlertDialogComponent(
        title = alertDialogTitle,
        message = alertDialogMessage,
        onDismissRequest = {
            alertDialogTitle = ""
            alertDialogMessage = ""
            showAlertDialogDialog = false
        },
        showDialog = showAlertDialogDialog,
        modifier = modifier
    )
}
