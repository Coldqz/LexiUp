package com.coldzz.lexiup.features.stats.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.components.CircularWordsProgress
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CircularProgressCardComponent(
    percentage: Float,
    currentWordsNumber: Int,
    remainingWordsNumber: Int,
    totalWordsNumber: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(24.dp)
        ) {
            CircularWordsProgress(
                title = "$percentage%",
                subtitle = stringResource(R.string.goal_reached),
                percentageForIndicator = percentage,
                strokeWidth = 16.dp
            )
            Spacer(Modifier.size(12.dp))
            val formattedFiveThousandNumber =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(totalWordsNumber)
            Text(
                text = stringResource(R.string.word_milestone, formattedFiveThousandNumber),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.keep_learning_milestone, formattedFiveThousandNumber),
                textAlign = TextAlign.Center,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WordsCountLabelComponent(
                    title = stringResource(R.string.current_label),
                    number = currentWordsNumber,
                )
                WordsCountLabelComponent(
                    title = stringResource(R.string.remaining_label),
                    number = remainingWordsNumber,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL, showBackground = false, showSystemUi = false)
@Composable
private fun CircularProgressPreview() {
    LexiUpTheme {
        CircularProgressCardComponent(
            percentage = 24.8f,
            currentWordsNumber = 1240,
            remainingWordsNumber = 3760,
            totalWordsNumber = 5000
        )
    }
}