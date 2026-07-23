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
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun SkippedWordsDialog(
    skippedWords: List<String>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        CoreDialogComponent(
            title = stringResource(R.string.some_words_skipped),
            subTitle = stringResource(
                R.string.skipped_words_description,
                skippedWords.joinToString(", ")
            ),
            iconRes = R.drawable.ic_warning,
            iconContentDescription = stringResource(R.string.error_icon),
            buttons = {
                Button(
                    onClick = {
                        showDialog = false
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.close),
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
private fun SkippedWordsDialogPreview() {
    val skippedWords = listOf("have to", "going to", "some phrase", "other phrase", "Saturday")
    LexiUpTheme {
        SkippedWordsDialog(
            skippedWords = buildList {
                repeat(5) {
                    addAll(skippedWords)
                }
            },
            onDismiss = {}
        )
    }
}