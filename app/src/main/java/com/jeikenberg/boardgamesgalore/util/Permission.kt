package com.jeikenberg.boardgamesgalore.util

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.jeikenberg.boardgamesgalore.R

@ExperimentalPermissionsApi
@Composable
fun Permission(
    permission: String = android.Manifest.permission.CAMERA,
    rational: String = stringResource(R.string.camera_permission_rational_text),
    permissionNotAvailableContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val permissionState = rememberPermissionState(permission)
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            Rational(
                text = rational,
                onRequestPermission = {
                    permissionState.launchPermissionRequest()
                })
        },
        permissionNotAvailableContent = permissionNotAvailableContent,
        content = content
    )
}

@Composable
private fun Rational(
    text: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Don't */ },
        title = {
            Text(text = stringResource(R.string.camera_permission_request_title))
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(
                onClick = onRequestPermission
            ) {
                Text(stringResource(id = R.string.ok))
            }
        }
    )
}