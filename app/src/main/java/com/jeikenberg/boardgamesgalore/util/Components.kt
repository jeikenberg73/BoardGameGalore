package com.jeikenberg.boardgamesgalore.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.text.style.TextAlign
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.crop


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
                        text = "Ok",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = modifier
                    )
                }
            },
            modifier = modifier
        )
    }
}

//suspend fun cropImage(
//    imageUri: Uri,
//    onPictureCancel: () -> Unit,
//    onImageFile: (bitmap: Bitmap) -> Unit,
//    context: Context
//) {
//    when (val result: CropResult =
//        imageCropper.crop(imageUri, context)) {
//        CropError.LoadingError -> {}
//        CropError.SavingError -> {}
//        CropResult.Cancelled -> onPictureCancel()
//        is CropResult.Success -> {
//            onImageFile(result.bitmap.asAndroidBitmap())
//        }
//    }
//}


//private val CropperDialogProperties = @OptIn(ExperimentalComposeUiApi::class) (DialogProperties(
//    usePlatformDefaultWidth = false,
//    dismissOnBackPress = false,
//    dismissOnClickOutside = false
//))
//@Composable
//fun ImageCropperDialog(
//    state: CropState,
//    style: CropperStyle = DefaultCropperStyle,
//    dialogProperties: DialogProperties = CropperDialogProperties,
//    dialogPadding: PaddingValues = PaddingValues(16.dp),
//    dialogShape: Shape = RoundedCornerShape(8.dp),
//    topBar: @Composable (CropState) -> Unit = { DefaultTopBar(it) },
//    cropControls: @Composable BoxScope.(CropState) -> Unit = { DefaultControls(it) }
//) {
//    CompositionLocalProvider(LocalCropperStyle provides style) {
//        Dialog(
//            onDismissRequest = { state.done(accept = false) },
//            properties = dialogProperties,
//        ) {
//            Surface(
//                modifier = Modifier.padding(dialogPadding),
//                shape = dialogShape,
//            ) {
//                Column {
//                    topBar(state)
//                    Box(
//                        modifier = Modifier
//                            .weight(1f)
//                            .clipToBounds()
//                    ) {
//                        CropperPreview(state = state, modifier = Modifier.fillMaxSize())
//                        cropControls(state)
//                    }
//                }
//            }
//        }
//    }
//}