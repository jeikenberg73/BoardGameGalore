package com.jeikenberg.boardgamesgalore.ui.gameselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStart
import com.jeikenberg.boardgamesgalore.ui.theme.BlueGradiantBackgroundStop

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
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White
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
                text = "Search"
            )
        }
    )
}