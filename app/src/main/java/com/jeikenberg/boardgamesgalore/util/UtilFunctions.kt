package com.jeikenberg.boardgamesgalore.util

import android.content.Context
import android.net.Uri
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