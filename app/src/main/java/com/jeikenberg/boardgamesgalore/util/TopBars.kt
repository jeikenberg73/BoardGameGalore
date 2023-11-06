package com.jeikenberg.boardgamesgalore.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.navigation.GameDestination
import com.jeikenberg.boardgamesgalore.navigation.GameInfo
import com.jeikenberg.boardgamesgalore.ui.gameselection.GameSearchBar
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStop
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
import com.jeikenberg.boardgamesgalore.ui.theme.InterFontFamily


@Composable
fun TopSearchBar(
    searchText: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean = false,
    modifier: Modifier
) {
    if (isVisible) {
        GameSearchBar(
            searchText = searchText,
            onValueChange = onValueChange,
            modifier = modifier
        )
    }
}

@Composable
fun AddEditTopBar(
    title: String,
    message: String,
    onNotInitialize: (Boolean) -> Unit,
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
            onClick = {
                onNotInitialize(false)
                onCancel()
            },
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
            text = stringResource(R.string.add_edit_game_top_bar_title),
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
                text = stringResource(R.string.save),
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

@Composable
fun DetailsTopBar(
    onBackPress: () -> Unit,
    gameDestination: GameDestination,
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
            onClick = { onBackPress() },
            modifier = modifier
                .align(CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = stringResource(R.string.details_top_bar_go_back_text)
            )
        }
        Text(
            text = gameDestination.label,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .align(CenterVertically)
                .weight(1f)
        )
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 80)
@Composable
fun DetailsTopBarPreview() {
    BoardGamesGaloreTheme {
        DetailsTopBar(onBackPress = {}, gameDestination = GameInfo, modifier = Modifier)
    }
}



//@Preview(showBackground = true, widthDp = 380, heightDp = 80)
//@Composable
//private fun TopBarPreview() {
//    BoardGamesGaloreTheme {
//        AddEditTopBar(
//            title = stringResource(R.string.error),
//            message = stringResource(R.string.error_message),
//            onDismissRequest = {},
//            showDialog = false,
//            saveButtonClick = {},
//            saveButtonEnable = true,
//            onCancel = {},
//            modifier = Modifier
//        )
//    }
//}