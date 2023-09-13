package com.jeikenberg.boardgamesgalore.ui.utilscreens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStop
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
import com.jeikenberg.boardgamesgalore.ui.theme.GreenColorStops
import com.jeikenberg.boardgamesgalore.ui.theme.InterFontFamily


@ExperimentalCoilApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(
    takePictureClick: () -> Unit,
    uploadPictureClick: () -> Unit,
    imageUri: Uri? = null,
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
    Scaffold(
        topBar = {
            TopBar(
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
                onValueChange = { gameName = it },
                label = "Name",
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Maker
            GameTextField(
                value = gameMaker,
                onValueChange = { gameMaker = it },
                label = "Maker",
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Rating
            GameTextField(
                value = gameRating,
                onValueChange = { gameRating = it },
                label = "Rating",
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Weight
            GameTextField(
                value = gameWeight,
                onValueChange = { gameWeight = it },
                label = "Weight",
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Number of Players
            GameTextField(
                value = gameNumberOfPlayers,
                onValueChange = { gameNumberOfPlayers = it },
                label = "Number Of Players (e.g. 1-4 or 2)",
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Play Time
            GameTextField(
                value = gamePlayTime,
                onValueChange = { gamePlayTime = it },
                label = "Play Time (Min) (e.g. 60-120 or 30)",
                modifier = modifier
                    .padding(top = 8.dp)
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            )
            // Game Description
            GameTextField(
                value = gameDescription,
                onValueChange = { gameDescription = it },
                label = "Game Description",
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
                    onClick = uploadPictureClick,
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
                    onClick = takePictureClick,
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
            if(imageUri != null) {
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = "Captured image",
                    modifier = modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun TopBar(
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
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        )
        Text(
            text = "Add Game",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    maxLines: Int = 1,
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
        }
    )
}

@Preview(showBackground = true, widthDp = 380, heightDp = 80)
@Composable
fun TopBarPreview() {
    BoardGamesGaloreTheme {
        TopBar(modifier = Modifier)
    }
}

@Preview(showBackground = false, widthDp = 344, heightDp = 60)
@Composable
fun GameTextFieldDefaultTextPreview() {
    BoardGamesGaloreTheme {
        var value by remember {
            mutableStateOf("")
        }

        GameTextField(
            value = value,
            onValueChange = { value = it },
            label = "Name",
            modifier = Modifier
        )
    }
}

@Preview(showBackground = false, widthDp = 344, heightDp = 60)
@Composable
fun GameTextFieldHasTextPreview() {
    BoardGamesGaloreTheme {
        var value by remember {
            mutableStateOf("")
        }

        GameTextField(
            value = "GloomHaven",
            onValueChange = { value = it },
            label = "Name",
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun AddGameScreenPreview() {
    BoardGamesGaloreTheme {
        AddGameScreen(
            takePictureClick = {},
            uploadPictureClick = {},
            modifier = Modifier
        )
    }
}