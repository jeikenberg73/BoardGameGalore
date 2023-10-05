package com.jeikenberg.boardgamesgalore.ui.addedit

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.ui.theme.GreenColorStops
import com.jeikenberg.boardgamesgalore.util.AddEditTopBar
import com.jeikenberg.boardgamesgalore.util.AlertDialogComponent
import com.jeikenberg.boardgamesgalore.util.EmptyIconImage
import com.jeikenberg.boardgamesgalore.util.GAME_DESCRIPTION
import com.jeikenberg.boardgamesgalore.util.GAME_ICON_IMAGE_URI
import com.jeikenberg.boardgamesgalore.util.GAME_MAKER
import com.jeikenberg.boardgamesgalore.util.GAME_NAME
import com.jeikenberg.boardgamesgalore.util.GAME_NUMBER_OF_PLAYERS
import com.jeikenberg.boardgamesgalore.util.GAME_PLAY_TIME
import com.jeikenberg.boardgamesgalore.util.GAME_RATING
import com.jeikenberg.boardgamesgalore.util.GAME_WEIGHT
import com.jeikenberg.boardgamesgalore.util.GameTextField
import com.jeikenberg.boardgamesgalore.viewmodels.AddEditGameViewModel
import kotlinx.coroutines.runBlocking

private const val TEMP_IMAGE_NAME = "TEMPIMAGE"

@ExperimentalPermissionsApi
@ExperimentalCoilApi
@OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AddGameScreen(
    viewModel: AddEditGameViewModel,
    existingGame: Game? = null,
    games: List<Game>,
    updateGame: (Game) -> Unit,
    takePictureClick: () -> Unit,
    uploadPictureClick: () -> Unit,
    bitmap: Bitmap? = null,
    setHasTakenPicture: (Boolean) -> Unit,
    hasTakenPicture: Boolean = false,
    onGameSaved: () -> Unit,
    onGameNotSaved: () -> Unit,
    onCancel: () -> Unit,
    onTakePictureFailed: (title: String, message: String) -> Unit,
    modifier: Modifier
) {
    var gameName: String by rememberSaveable {
        mutableStateOf("")
    }
    var gameMaker: String by rememberSaveable {
        mutableStateOf("")
    }
    var gameRating: String by rememberSaveable {
        mutableStateOf("")
    }
    var gameWeight: String by rememberSaveable {
        mutableStateOf("")
    }
    var gamePlayTime: String by rememberSaveable {
        mutableStateOf("")
    }
    var gameNumberOfPlayers: String by rememberSaveable {
        mutableStateOf("")
    }
    var gameDescription: String by rememberSaveable {
        mutableStateOf("")
    }
    var gameIconUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }
    var tempImageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }
    var saveButtonEnable: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var titleValue: String by rememberSaveable {
        mutableStateOf("")
    }
    var messageValue: String by rememberSaveable {
        mutableStateOf("")
    }
    var isUploadingImage: Boolean by rememberSaveable {
        mutableStateOf(true)
    }
    var isEditing: Boolean by rememberSaveable {
        mutableStateOf(true)
    }
    var createdNewGame: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var hasInitializedGame: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var gameId: Long by rememberSaveable {
        mutableLongStateOf(-1)
    }
    var hasInitialized: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

    if (existingGame == null) {
        var largestLong = -1L
        games.forEach { nextGame ->
            if (nextGame.gameId > largestLong) {
                largestLong = nextGame.gameId
            }
        }

        val game = Game(
            largestLong + 1,
            "",
            "",
            0.0,
            0.0,
            "",
            "",
            "",
            ""
        )
        updateGame(game)
        createdNewGame = true
        isEditing = false
    } else {
        gameId = existingGame.gameId
        createdNewGame = false
        if (!hasInitialized) {
            gameName = existingGame.name
            gameMaker = existingGame.maker
            val ratingString =
                if (existingGame.rating <= 0.0) {
                    ""
                } else {
                    existingGame.rating.toString()
                }
            gameRating = ratingString
            val weightString =
                if (existingGame.weight <= 0.0) {
                    ""
                } else {
                    existingGame.weight.toString()
                }
            gameWeight = weightString
            gameNumberOfPlayers = existingGame.numberOfPlayers
            gamePlayTime = existingGame.playTime
            gameDescription = existingGame.description
            gameIconUri = Uri.parse(existingGame.gameIconUri)
            hasInitialized = true
        }
    }

    if (createdNewGame) {
        return
    }
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val gameStats = mapOf(
        Pair(GAME_NAME, gameName),
        Pair(GAME_MAKER, gameMaker),
        Pair(GAME_RATING, gameRating),
        Pair(GAME_WEIGHT, gameWeight),
        Pair(GAME_NUMBER_OF_PLAYERS, gameNumberOfPlayers),
        Pair(GAME_PLAY_TIME, gamePlayTime),
        Pair(GAME_DESCRIPTION, gameDescription),
        Pair(GAME_ICON_IMAGE_URI, gameIconUri.toString())
    )
    Scaffold(
        topBar = {
            AddEditTopBar(
                title = titleValue,
                message = messageValue,
                onDismissRequest = {
                    showDialog = false
                },
                showDialog = showDialog,
                saveButtonClick = {
                    val didSave = saveGame(
                        context = context,
                        id = gameId,
                        name = gameStats[GAME_NAME]!!,
                        maker = gameStats[GAME_MAKER]!!,
                        rating = gameStats[GAME_RATING]!!,
                        weight = gameStats[GAME_WEIGHT]!!,
                        numberOfPlayers = gameStats[GAME_NUMBER_OF_PLAYERS]!!,
                        playTime = gameStats[GAME_PLAY_TIME]!!,
                        description = gameStats[GAME_DESCRIPTION]!!,
                        errorPopup = { title, message ->
                            showDialog = true
                            titleValue = title
                            messageValue = message
                        },
                        isEditing = isEditing,
                        viewModel = viewModel,
                        onUpdateFail = {
                            AlertDialogComponent(
                                title = stringResource(R.string.add_edit_game_game_update_failed_title),
                                message = stringResource(R.string.add_edit_game_game_update_failed_message),
                                onDismissRequest = { this.dismiss() },
                                showDialog = true,
                                modifier = Modifier
                            )
                        },
                        gameBeingEdited = existingGame!!,
                        tempImageUri = tempImageUri!!,
                        bitmap = bitmap!!,
                        contentResolver = contentResolver
                    )
                    if (didSave) {
                        hasInitialized = false
                        onGameSaved()
                    } else { // Error is taken care of in the saveGame function.
                        hasInitialized = false
                        onGameNotSaved()
                    }
                },
                saveButtonEnable = saveButtonEnable,
                onNotInitialize = {
                    hasInitializedGame = it
                },
                onCancel = onCancel,
                modifier = modifier
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .fillMaxSize()
        ) {
            // Game Name
            GameTextField(
                value = gameName,
                onValueChange = {
                    gameName = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_name),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Maker
            GameTextField(
                value = gameMaker,
                onValueChange = {
                    gameMaker = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_maker),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Rating
            GameTextField(
                value = gameRating,
                onValueChange = {
                    gameRating = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_rating),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Weight
            GameTextField(
                value = gameWeight,
                onValueChange = {
                    gameWeight = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_weight),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Number of Players
            GameTextField(
                value = gameNumberOfPlayers,
                onValueChange = {
                    gameNumberOfPlayers = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_number_players),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Play Time
            GameTextField(
                value = gamePlayTime,
                onValueChange = {
                    gamePlayTime = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_play_time),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Description
            GameTextField(
                value = gameDescription,
                onValueChange = {
                    gameDescription = it
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                },
                label = stringResource(R.string.add_edit_game_field_description),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                singleLine = false,
                maxLines = 4,
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.add_edit_game_game_icon_uploaded),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier
                    .padding(top = 16.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        isUploadingImage = true
                        uploadPictureClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = modifier
                        .background(
                            brush = Brush.verticalGradient(colorStops = GreenColorStops),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.add_edit_game_upload_icon),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = modifier.width(16.dp))
                Button(
                    onClick = {
                        isUploadingImage = false
                        takePictureClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = modifier
                        .background(
                            brush = Brush.verticalGradient(colorStops = GreenColorStops),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.add_edit_game_take_picture),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            if (bitmap != null) {
                if (!hasTakenPicture) {
                    tempImageUri = viewModel.saveImage(
                        LocalContext.current.contentResolver,
                        TEMP_IMAGE_NAME,
                        bitmap
                    )
                    saveButtonEnable = checkSaveButtonEnable(
                        name = gameName,
                        maker = gameMaker,
                        rating = gameRating,
                        weight = gameWeight,
                        numberOfPlayers = gameNumberOfPlayers,
                        playTime = gamePlayTime,
                        description = gameDescription,
                        tempUri = tempImageUri,
                        bitmap = bitmap
                    )
                    setHasTakenPicture(true)
                }
                tempImageUri?.let { localGameUri ->
                    GlideImage(
                        model = Uri.parse(localGameUri.toString()),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                } ?: run {
                    EmptyIconImage(
                        modifier = modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
            } else if (gameIconUri != null) {
                if (isEditing) {
                    GlideImage(
                        model = Uri.parse(gameIconUri.toString()),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

private fun getBitmapFromUri(imageUri: Uri, contentResolver: ContentResolver): Bitmap {
    return ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, imageUri))
}

private fun checkSaveButtonEnable(
    name: String,
    maker: String,
    rating: String,
    weight: String,
    numberOfPlayers: String,
    playTime: String,
    description: String,
    tempUri: Uri?,
    bitmap: Bitmap?
): Boolean {
    return !(name.isBlank() ||
            name.isEmpty() ||
            maker.isBlank() ||
            maker.isEmpty() ||
            rating.isBlank() ||
            rating.isEmpty() ||
            weight.isBlank() ||
            weight.isEmpty() ||
            numberOfPlayers.isBlank() ||
            numberOfPlayers.isEmpty() ||
            playTime.isBlank() ||
            playTime.isEmpty() ||
            description.isBlank() ||
            description.isEmpty() ||
            tempUri == null ||
            bitmap == null
            )
}

private fun saveGame(
    context: Context,
    id: Long,
    name: String,
    maker: String,
    rating: String,
    weight: String,
    numberOfPlayers: String,
    playTime: String,
    description: String,
    errorPopup: (errorTitle: String, errorMessage: String) -> Unit,
    viewModel: AddEditGameViewModel,
    isEditing: Boolean,
    onUpdateFail: @Composable AlertDialog.() -> Unit,
    gameBeingEdited: Game,
    tempImageUri: Uri,
    bitmap: Bitmap,
    contentResolver: ContentResolver
): Boolean {
    var isGood = true
    if (id < 0) {
        errorPopup(
            "ID is Incorrect",
            "The id is less than 0."
        )
        isGood = false
    }
    if (name.isEmpty() || name.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_name_not_correct_title),
            context.getString(
                R.string.add_edit_game_name_not_correct_message
            )
        )
        isGood = false
    } else if (maker.isEmpty() || maker.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_maker_not_correct_title),
            context.getString(
                R.string.add_edit_game_maker_not_correct_message
            )
        )
        isGood = false
    } else if (rating.isEmpty() || rating.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_rating_not_correct_title),
            context.getString(
                R.string.add_edit_game_rating_not_correct_message
            )
        )
        isGood = false
    } else if (weight.isEmpty() || weight.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_weight_not_correct_title),
            context.getString(
                R.string.add_edit_game_weight_not_correct_message
            )
        )
        isGood = false
    } else if (numberOfPlayers.isEmpty() || numberOfPlayers.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_number_of_players_not_correct_title),
            context.getString(R.string.add_edit_game_number_of_players_not_correct_message)
        )
        isGood = false
    } else if (playTime.isEmpty() || playTime.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_play_time_not_correct_title),
            context.getString(R.string.add_edit_game_play_time_not_correct_message)
        )
        isGood = false
    } else if (description.isEmpty() || description.isBlank()) {
        errorPopup(
            context.getString(R.string.add_edit_game_description_not_correct_title),
            context.getString(R.string.add_edit_game_description_not_correct_message)
        )
        isGood = false
    }

    if (isGood) {
        val ratingNumber =
            rating.toDoubleOrNull() ?: run {
                errorPopup(
                    context.getString(R.string.add_edit_game_rating_parsing_not_correct_title),
                    context.getString(R.string.add_edit_game_rating_parsing_not_correct_message)
                )
                isGood = false
                0.0
            }

        val weightNumber =
            weight.toDoubleOrNull() ?: run {
                errorPopup(
                    context.getString(R.string.add_edit_game_weight_parsing_not_correct_title),
                    context.getString(R.string.add_edit_game_weight_parsing_not_correct_message)
                )
                isGood = false
                0.0
            }

        var regex = Regex(
            pattern = "^[0-9]+(-[0-9]+)",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        val numberOfPlayersValue =
            if (regex.matches(numberOfPlayers.trim())) {
                numberOfPlayers
            } else {
                errorPopup(
                    context.getString(R.string.add_edit_game_number_of_players_parsing_not_correct_title),
                    context.getString(R.string.add_edit_game_number_of_players_parsing_not_correct_message)
                )
                isGood = false
                ""
            }


        regex = Regex(
            pattern = "^[0-9]+(-*[0-9]*)",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        val playTimeValue =
            if (regex.matches(playTime.trim())) {
                playTime
            } else {
                errorPopup(
                    context.getString(R.string.add_edit_game_play_time_parsing_not_correct_title),
                    context.getString(R.string.add_edit_game_play_time_parsing_not_correct_message)
                )
                isGood = false
                ""
            }

        if (isGood) {
            runBlocking {
                gameBeingEdited.gameId = id
                gameBeingEdited.name = name
                gameBeingEdited.maker = maker
                gameBeingEdited.rating = ratingNumber
                gameBeingEdited.weight = weightNumber
                gameBeingEdited.numberOfPlayers = numberOfPlayersValue
                gameBeingEdited.playTime = playTimeValue
                gameBeingEdited.description = description
                gameBeingEdited.gameIconUri = viewModel
                    .saveImage(contentResolver, gameBeingEdited.gameId.toString(), bitmap)
                    .toString()

                viewModel.deleteImage(contentResolver, tempImageUri)

                if (isEditing) {
                    viewModel.updateGame(gameBeingEdited)
                } else {
                    viewModel.insertGame(gameBeingEdited)
                }
            }
        }
    }

    return isGood
}

//@Preview(showBackground = false)
//@Composable
//fun ErrorPopupPreview() {
//    BoardGamesGaloreTheme {
//        AlertDialogComponent(
//            title = "Title Test",
//            message = "Message Test",
//            showDialog = true,
//            onDismissRequest = {},
//            modifier = Modifier
//        )
//    }
//}

//@Preview(showBackground = false, widthDp = 344, heightDp = 60)
//@Composable
//fun GameTextFieldDefaultTextPreview() {
//    BoardGamesGaloreTheme {
//        var value by remember {
//            mutableStateOf("")
//        }
//
//        GameTextField(
//            value = value,
//            onValueChange = { value = it },
//            label = "Name",
//            modifier = Modifier
//        )
//    }
//}

//@Preview(showBackground = false, widthDp = 344, heightDp = 60)
//@Composable
//fun GameTextFieldHasTextPreview() {
//    BoardGamesGaloreTheme {
//        var value by remember {
//            mutableStateOf("")
//        }
//
//        GameTextField(
//            value = "GloomHaven",
//            onValueChange = { value = it },
//            label = "Name",
//            modifier = Modifier
//        )
//    }
//}

//@ExperimentalCoilApi
//@ExperimentalPermissionsApi
//@Preview
//@Composable
//fun AddGameScreenPreview() {
//    val context = LocalContext.current
//    val bitmap = Media.getBitmap(
//        context.contentResolver,
//        Uri.fromFile(File("content://media/external/images/media/31"))
//    )
//    BoardGamesGaloreTheme {
//        AddGameScreen(
//            takePictureClick = {},
//            uploadPictureClick = {},
//            bitmap = bitmap,
//            setHasTakenPicture = {},
//            hasTakenPicture = false,
//            modifier = Modifier
//        )
//    }
//}