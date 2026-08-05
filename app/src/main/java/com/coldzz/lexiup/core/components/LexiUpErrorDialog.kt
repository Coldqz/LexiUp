package com.coldzz.lexiup.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.isNetworkError
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import retrofit2.HttpException

/**
 * A reusable error dialog component that adapts its content based on the type of [error] provided.
 * It handles both network-related errors and general exceptions by showing appropriate titles,
 * descriptions, and icons.
 *
 * @param error The [Throwable] that triggered the error state.
 */
@Composable
fun LexiUpErrorDialog(
    error: Throwable,
    onRetry: () -> Unit,
    onExit: () -> Unit,
) {
    val isNetworkError = error.isNetworkError()
    val isServerError = error is HttpException && error.code() in 500..599

    val title = when {
        isNetworkError -> stringResource(R.string.connection_failed)
        isServerError -> stringResource(R.string.server_unavailable)
        else -> stringResource(R.string.oops_something_went_wrong)
    }

    val subTitle = when {
        isNetworkError -> stringResource(R.string.please_check_your_internet_connection_and_try_again)
        isServerError -> stringResource(R.string.server_error_description)
        else -> stringResource(R.string.an_unexpected_error_occurred_please_try_again_later)
    }

    val iconRes = when {
        isNetworkError -> R.drawable.ic_wifi_off
        isServerError -> R.drawable.ic_dangerous // Or a server specific icon if available
        else -> R.drawable.ic_dangerous
    }

    val iconDescription = when {
        isNetworkError -> stringResource(R.string.connection_error_icon)
        else -> stringResource(R.string.error_icon)
    }

    val primaryButtonText = when {
        isNetworkError || isServerError -> stringResource(R.string.retry)
        else -> stringResource(R.string.try_again)
    }

    val secondaryButtonText = when {
        isNetworkError || isServerError -> stringResource(R.string.exit)
        else -> stringResource(R.string.close)
    }

    CoreDialogComponent(
        title = title,
        subTitle = subTitle,
        iconRes = iconRes,
        iconContentDescription = iconDescription,
        isErrorDialog = true,
        buttons = {
            Column {
                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = primaryButtonText,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    onClick = onExit,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = secondaryButtonText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun DialogComponentPreview() {
    LexiUpTheme {
        var showDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Show Connection Error")
            }

            if (showDialog) {
                LexiUpErrorDialog(
                    error = java.io.IOException(),
                    onRetry = { showDialog = false },
                    onExit = { showDialog = false }
                )
            }
        }
    }
}

@Preview
@Composable
private fun GeneralErrorDialogPreview() {
    LexiUpTheme {
        var showDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Show General Error")
            }

            if (showDialog) {
                LexiUpErrorDialog(
                    error = Exception(),
                    onRetry = { showDialog = false },
                    onExit = { showDialog = false }
                )
            }
        }
    }
}