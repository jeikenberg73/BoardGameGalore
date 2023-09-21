package com.jeikenberg.boardgamesgalore.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.asAndroidBitmap
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.ImageCropper
import com.mr0xf00.easycrop.crop
import java.io.InputStream

fun checkUri(context: Context, uri: String): Boolean {
    var inputStr: InputStream? = null
    try {
        inputStr = context.contentResolver.openInputStream(Uri.parse(uri))
    } catch (e: Exception) {
        Log.d("TAG", "Exception $e")
    }

    val exists = inputStr != null

    inputStr?.close()

    return exists
}