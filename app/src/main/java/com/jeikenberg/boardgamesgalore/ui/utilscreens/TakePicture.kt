package com.jeikenberg.boardgamesgalore.ui.utilscreens

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jeikenberg.boardgamesgalore.util.CapturePictureButton
import com.jeikenberg.boardgamesgalore.util.Log
import com.jeikenberg.boardgamesgalore.util.Permission
import com.jeikenberg.boardgamesgalore.util.executor
import com.jeikenberg.boardgamesgalore.util.getCameraProvider
import com.jeikenberg.boardgamesgalore.util.takePicture
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.ImageCropper
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.ui.ImageCropperDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

const val TAG: String = "Camera"

//val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun TakePicture(
    imageCropper: ImageCropper,
    onPictureTaken: (Bitmap) -> Unit,
    onPictureCancel: () -> Unit,
    modifier: Modifier
) {
    CameraCapture(
        imageCropper = imageCropper,
        modifier = modifier,
        onImageFile = { bitmap ->
            onPictureTaken(bitmap)
        },
        onPictureCancel = { onPictureCancel() }
    )
}

@Composable
private fun CameraPreview(
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onUseCase: (UseCase) -> Unit = {},
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            onUseCase(
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
            previewView
        }
    )
}

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@Composable
fun CameraCapture(
    imageCropper: ImageCropper,
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (Bitmap) -> Unit = {},
    onPictureCancel: () -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    Permission(
        permission = Manifest.permission.CAMERA,
        rational = "You need to take a picture, so I have to ask for permission.",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            })
                }) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        Box(
            modifier = modifier
        ) {
            val lifecycleOwner = LocalLifecycleOwner.current
            val coroutineScope = rememberCoroutineScope()
            var previewUseCase by remember {
                mutableStateOf<UseCase>(Preview.Builder().build())
            }
            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }
            Box {
                if (imageUri != null) {
                    imageCropper.cropState?.let { localCropState ->
                        ImageCropperDialog(localCropState)
                    }
                } else {
                    CameraPreview(
                        modifier = modifier,
                        onUseCase = {
                            previewUseCase = it
                        }
                    )
                    CapturePictureButton(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        onClick = {
                            coroutineScope.launch {
                                imageCaptureUseCase.takePicture(context.executor).let {
                                    imageUri = it.toUri()
                                    imageUri?.let { localUri ->
                                        when (val result: CropResult =
                                            imageCropper.crop(localUri, context)) {
                                            CropError.LoadingError -> {}
                                            CropError.SavingError -> {}
                                            CropResult.Cancelled -> onPictureCancel()
                                            is CropResult.Success -> {
                                                onImageFile(result.bitmap.asAndroidBitmap())
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
            LaunchedEffect(previewUseCase) {
                val cameraProvider = context.getCameraProvider()
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                    )
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to bind camera use case", ex)
                }
            }
        }
    }

}