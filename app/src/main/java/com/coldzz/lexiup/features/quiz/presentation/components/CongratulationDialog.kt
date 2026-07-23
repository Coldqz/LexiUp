package com.coldzz.lexiup.features.quiz.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.components.CoreDialogComponent

@Composable
fun CongratulationDialog(
    onButtonClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        CoreDialogComponent(
            title = stringResource(R.string.congratulations_you_passed),
            subTitle = stringResource(R.string.you_ve_successfully_completed_the_quiz),
            iconRes = R.drawable.ic_cheer,
            iconContentDescription = stringResource(R.string.applause_icon),
            buttons = {
                Button(
                    onClick = {
                        showDialog = false
                        onButtonClickAction()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.continue_string),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun CongratulationDialogPreview() {
    CongratulationDialog(
        {}
    )
}