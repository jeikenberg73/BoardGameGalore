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
import com.jeikenberg.boardgamesgalore.ui.gamedetails.GameInfoScreen
import com.jeikenberg.boardgamesgalore.ui.gameselection.GameSelectionScreen
import com.jeikenberg.boardgamesgalore.ui.utilscreens.AddGameScreen
import com.jeikenberg.boardgamesgalore.ui.utilscreens.TakePicture
import com.jeikenberg.boardgamesgalore.ui.utilscreens.UploadImageCompose
import com.jeikenberg.boardgamesgalore.util.AlertDialogComponent
import com.jeikenberg.boardgamesgalore.util.navigateSingleTopTo
import com.jeikenberg.boardgamesgalore.util.navigateToDetails
import com.jeikenberg.boardgamesgalore.viewmodels.AddGameViewModel
import com.jeikenberg.boardgamesgalore.viewmodels.GameSelectionViewModel
import com.mr0xf00.easycrop.rememberImageCropper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun GameNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val gameSelectionViewModel: GameSelectionViewModel = hiltViewModel()
    val addGameViewModel: AddGameViewModel = hiltViewModel()
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
    var gameToEdit: Game? by rememberSaveable {
        mutableStateOf(null)
    }
    var isAddEditGameInitialized: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    NavHost(
        navController = navController,
        startDestination = GameList.route,
        modifier = modifier
    ) {
        composable(route = GameList.route) {
            // Need this to pull data from database to initialize the database.
            val gameUiState by gameSelectionViewModel.gameUiState.collectAsState()

            val searchedGames by gameSelectionViewModel.searchedGames.collectAsState()
            val searchText by gameSelectionViewModel.searchText.collectAsState()
            val isSearching by gameSelectionViewModel.isSearching.collectAsState()
            isAddEditGameInitialized = false
            GameSelectionScreen(
                searchText = searchText,
                gameList = searchedGames,
                isSearching = isSearching,
                onValueChange = gameSelectionViewModel::onSearchTextChange,
                onAddGameClicked = {
                    gameToEdit = null
                    navController.navigateSingleTopTo(AddGame.route)
                },
                onEditGameClicked = { game ->
                    gameToEdit = game
                    navController.navigateSingleTopTo(AddGame.route)
                },
                modifier = modifier
            )
        }
        composable(route = AddGame.route) {
            AddGameScreen(
                game = gameToEdit,
                isInitialized = isAddEditGameInitialized,
                onInitializing = { isInitialized ->
                    isAddEditGameInitialized = isInitialized
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
                    isAddEditGameInitialized = false
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
