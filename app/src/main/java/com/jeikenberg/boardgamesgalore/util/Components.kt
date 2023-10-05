package com.jeikenberg.boardgamesgalore.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeikenberg.boardgamesgalore.R
import com.jeikenberg.boardgamesgalore.ui.theme.BoardGamesGaloreTheme
import com.jeikenberg.boardgamesgalore.ui.theme.GreenColorStops

@Composable
fun AlertDialogComponent(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    showDialog: Boolean,
    modifier: Modifier
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            containerColor = MaterialTheme.colorScheme.errorContainer,
            titleContentColor = MaterialTheme.colorScheme.onErrorContainer,
            textContentColor = MaterialTheme.colorScheme.onErrorContainer,
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                )
            },
            text = {
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = modifier
                    )
                }
            },
            modifier = modifier
        )
    }
}

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
        textStyle = MaterialTheme.typography.titleLarge,
        colors = TextFieldDefaults.colors(
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
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(40.dp),
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(colorStops = GreenColorStops),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
    )
}

@Composable
fun EmptyIconImage(
    modifier: Modifier
) {
    Image(
        painter = painterResource(R.drawable.token),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, backgroundColor = 0xFF777680)
@Composable
fun GameTextFieldPreview() {
    val (focusRequester) = FocusRequester.createRefs()
    BoardGamesGaloreTheme {
        GameTextField(
            value = "Test",
            onValueChange = {},
            label = "Place Text Here",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester.requestFocus() }
            ),
            modifier = Modifier
        )
    }
}