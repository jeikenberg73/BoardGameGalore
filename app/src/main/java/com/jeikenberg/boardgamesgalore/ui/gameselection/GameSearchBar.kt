package com.jeikenberg.boardgamesgalore.ui.gameselection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStop
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme

@Composable
fun GameSearchBar(
    searchText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    val colorStops = arrayOf(
        0.0f to BlueGradiantBackgroundStart,
        1.0f to BlueGradiantBackgroundStop
    )
    TextField(
        value = searchText,
        onValueChange = onValueChange,
        colors = TextFieldDefaults. colors(
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            disabledTextColor = MaterialTheme.colorScheme.onSecondary,
            errorTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedLabelColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.White,
                contentDescription = null,
                modifier = modifier
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(colorStops = colorStops)),
        placeholder = {
            Text(
                text = stringResource(R.string.search)
            )
        }
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun GameSearchBarPreview() {
    BoardGamesGaloreTheme {
        GameSearchBar(
            searchText = stringResource(R.string.search),
            onValueChange = {},
            modifier = Modifier
        )
    }
}