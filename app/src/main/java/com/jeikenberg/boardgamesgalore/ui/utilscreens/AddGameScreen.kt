package com.jeikenberg.boardgamesgalore.ui.utilscreens

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.data.game.Game
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStop
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
import com.jeikenberg.boardgamesgalore.ui.theme.GreenColorStops
import com.jeikenberg.boardgamesgalore.ui.theme.InterFontFamily
import com.jeikenberg.boardgamesgalore.util.AlertDialogComponent
import com.jeikenberg.boardgamesgalore.viewmodels.AddGameViewModel
import kotlinx.coroutines.runBlocking

const val GAME_NAME = "Game Name"
const val GAME_MAKER = "Game Maker"
const val GAME_RATING = "Game Rating"
const val GAME_WEIGHT = "Game Weight"
const val GAME_NUMBER_OF_PLAYERS = "Game Number Of Players"
const val GAME_PLAY_TIME = "Game Play Time"
const val GAME_DESCRIPTION = "Game Description"
const val GAME_ICON_IMAGE_URI = "Game Icon Image URI"


@ExperimentalPermissionsApi
@ExperimentalCoilApi
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AddGameScreen(
    takePictureClick: () -> Unit,
    uploadPictureClick: () -> Unit,
    bitmap: Bitmap? = null,
    setHasTakenPicture: (Boolean) -> Unit,
    hasTakenPicture: Boolean = false,
    onGameSaved: () -> Unit,
    onCancel: () -> Unit,
    onTakePictureFailed: (title: String, message: String) -> Unit,
    modifier: Modifier
) {
    val viewModel: AddGameViewModel = hiltViewModel()
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
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
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
            TopBar(
                title = titleValue,
                message = messageValue,
                onDismissRequest = {
                    showDialog = false
                },
                showDialog = showDialog,
                saveButtonClick = {
                    val didSave = saveGame(
                        gameStats[GAME_NAME]!!,
                        gameStats[GAME_MAKER]!!,
                        gameStats[GAME_RATING]!!,
                        gameStats[GAME_WEIGHT]!!,
                        gameStats[GAME_NUMBER_OF_PLAYERS]!!,
                        gameStats[GAME_PLAY_TIME]!!,
                        gameStats[GAME_DESCRIPTION]!!,
                        gameStats[GAME_ICON_IMAGE_URI]!!,
                        errorPopup = { title, message ->
                            showDialog = true
                            titleValue = title
                            messageValue = message
                        },
                        viewModel
                    )
                    if(didSave) {
                        onGameSaved()
                    }
                },
                saveButtonEnable = saveButtonEnable,
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Name",
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Maker",
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Rating",
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Weight",
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Number Of Players (e.g. 1-4 or 2)",
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Play Time (Min) (e.g. 60-120 or 30)",
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
                        gameIconUri = gameIconUri.toString()
                    )
                },
                label = "Game Description",
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
                text = "Game Icon Upload",
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
                        text = "Upload Icon",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = modifier.width(16.dp))
                Button(
                    onClick = {
                        if (gameName.isEmpty() || gameName.isBlank()) {
                            onTakePictureFailed(
                                "No Game Name",
                                "The game needs a name before taking a picture."
                            )
                        } else {
                            isUploadingImage = false
                            takePictureClick()
                        }
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
                        text = "Take Picture",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            if (bitmap != null) {
                if (isUploadingImage) {
                    if (!hasTakenPicture) {
                        gameIconUri = viewModel.saveImage(
                            LocalContext.current.contentResolver,
                            gameName,
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
                            gameIconUri = gameIconUri.toString()
                        )
                        setHasTakenPicture(true)
                    }
                    GlideImage(
                        model = bitmap,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                } else if (gameName != "") {
                    if (!hasTakenPicture) {
                        gameIconUri =
                            viewModel.saveImage(
                                LocalContext.current.contentResolver,
                                gameName,
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
                            gameIconUri = gameIconUri.toString()
                        )
                        setHasTakenPicture(true)
                    }
                    gameIconUri?.let { localGameUri ->
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
                }
            }
        }
    }
}

private fun checkSaveButtonEnable(
    name: String,
    maker: String,
    rating: String,
    weight: String,
    numberOfPlayers: String,
    playTime: String,
    description: String,
    gameIconUri: String,
): Boolean {
    val shouldShow =
        !(name.isBlank() ||
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
                gameIconUri.isBlank() ||
                gameIconUri.isEmpty() ||
                gameIconUri == "null")

    return shouldShow
}

@Composable
private fun EmptyIconImage(
    modifier: Modifier
) {
    Image(
        painter = painterResource(R.drawable.token),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
private fun PermissionRequestDialog(modifier: Modifier) {
    val context = LocalContext.current
    Column(modifier) {
        Text("No Permission")
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .apply {
                            data = Uri.fromParts(
                                "package",
                                context.packageName,
                                null
                            )
                        })
            }) {
            Text("Open Settings")
        }
    }
}

@Composable
private fun Rational(
    text: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Don't */ },
        title = {
            Text(text = "Permission request")
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(
                onClick = onRequestPermission
            ) {
                Text("Ok")
            }
        }
    )
}

@Composable
fun TopBar(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    showDialog: Boolean,
    saveButtonClick: () -> Unit,
    saveButtonEnable: Boolean,
    onCancel: () -> Unit,
    modifier: Modifier
) {
    val colorStops = arrayOf(
        0.0f to BlueGradiantBackgroundStart,
        1.0f to BlueGradiantBackgroundStop
    )

    Row(
        modifier = modifier
            .background(Brush.verticalGradient(colorStops = colorStops))
    ) {
        IconButton(
            onClick = onCancel,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp)
            )
        }
        Text(
            text = "Add Game",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        TextButton(
            onClick = saveButtonClick,
            enabled = saveButtonEnable,
            modifier = modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Save",
                color = Color.White,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp)
            )
        }
        AlertDialogComponent(
            title = title,
            message = message,
            onDismissRequest = onDismissRequest,
            showDialog = showDialog,
            modifier = modifier
        )
    }
}

private fun saveGame (
    name: String,
    maker: String,
    rating: String,
    weight: String,
    numberOfPlayers: String,
    playTime: String,
    description: String,
    gameIcon: String,
    errorPopup: (errorTitle: String, errorMessage: String) -> Unit,
    viewModel: AddGameViewModel
): Boolean {
    if (name.isEmpty() || name.isBlank()) {
        errorPopup("Name Not Correct", "The game needs a correct name.")
        return false
    }

    if (maker.isEmpty() || maker.isBlank()) {
        errorPopup("Maker Not Correct", "The game needs a correct maker.")
        return false
    }

    val ratingNumber =
        if (rating.isEmpty() || rating.isBlank()) {
            errorPopup("Rating Not Correct", "The game needs a correct rating.")
            return false
        } else {
            rating.toDoubleOrNull() ?: run {
                errorPopup(
                    "Rating Not Correct",
                    "Please check the rating and try again."
                )
                return false
            }
        }

    val weightNumber =
        if (weight.isEmpty() || weight.isBlank()) {
            errorPopup("Weight Not Correct", "The game needs a correct weight.")
            return false
        } else {
            weight.toDoubleOrNull() ?: run {
                errorPopup(
                    "Weight Not Correct",
                    "Please check the weight and try again."
                )
                return false
            }
        }

    val numberOfPlayersValue = if (numberOfPlayers.isEmpty() || numberOfPlayers.isBlank()) {
        errorPopup(
            "Number Of Players Not Correct",
            "The game needs a number of players"
        )
        return false
    } else {
        val regex = Regex(
            pattern = "^[0-9]+(-[0-9]+)",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        if (regex.matches(numberOfPlayers.trim())) {
            numberOfPlayers
        } else {
            errorPopup(
                "Number Of Players Not Correct",
                "Please check the number of players. It needs to use the format X-Y where X is the least amount of players and Y is the most amount of players (e.g. 1-4)."
            )
            return false
        }
    }

    val playTimeValue = if (playTime.isEmpty() || playTime.isBlank()) {
        errorPopup(
            "Play Time Not Correct",
            "The game needs an amount of play time."
        )
        return false
    } else {
        val regex = Regex(
            pattern = "^[0-9]+(-*[0-9]*)",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        if (regex.matches(playTime.trim())) {
            playTime
        } else {
            errorPopup(
                "Play Time Not Correct",
                "Please check the play time. It needs to use the format X-Y where X it the shortest play time in minutes and Y is the longest play time in minutes (e.g. 60-120)"
            )
            return false
        }
    }

    if (description.isEmpty() || description.isBlank()) {
        errorPopup(
            "Description Not Correct",
            "The game needs a description."
        )
        return false
    }

    val game = Game(
        name,
        maker,
        ratingNumber,
        weightNumber,
        numberOfPlayersValue,
        playTimeValue,
        description,
        gameIcon
    )
    runBlocking {
        viewModel.insertGame(game)
    }

    return true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier
) {
    TextField(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(colorStops = GreenColorStops),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.titleLarge,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = modifier
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true, widthDp = 380, heightDp = 80)
@Composable
fun TopBarPreview() {
    BoardGamesGaloreTheme {
        TopBar(
            title = "Error",
            message = "Error Message",
            onDismissRequest = {},
            showDialog = false,
            saveButtonClick = {},
            saveButtonEnable = true,
            onCancel = {},
            modifier = Modifier
        )
    }
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