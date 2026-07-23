package com.coldzz.lexiup.features.quiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun QuizProgressBarComponent(
    currentValue: Int,
    maxValue: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.quiz_progress, currentValue, maxValue),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        LinearProgressIndicator(
            progress = {
                currentValue.toFloat() / maxValue.toFloat()
            },
            gapSize = 0.dp,
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun QuizProgressBarComponentPreview() {
    LexiUpTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            QuizProgressBarComponent(
                2, 10
            )
        }
    }
}