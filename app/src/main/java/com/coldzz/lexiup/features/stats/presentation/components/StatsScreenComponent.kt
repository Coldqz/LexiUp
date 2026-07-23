package com.coldzz.lexiup.features.stats.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.stats.presentation.LevelProgressDataModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun StatsScreenComponent(
    percentage: Float,
    currentWordsNumber: Int,
    remainingWordsNumber: Int,
    totalWordsNumber: Int,
    levelProgressDataModel: List<LevelProgressDataModel>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.progress_insights),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.tracking_journey)
            )
            Spacer(Modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.overall_mastery),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        CircularProgressCardComponent(
            percentage = percentage,
            currentWordsNumber = currentWordsNumber,
            remainingWordsNumber = remainingWordsNumber,
            totalWordsNumber = totalWordsNumber,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            text = stringResource(R.string.level_breakdown),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
        )
        ProgressByLevelComponent(
            levelProgressDataModel = levelProgressDataModel,
        )
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Preview(showBackground = false, showSystemUi = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
private fun StatsScreenComponentPreview() {
    LexiUpTheme {
        StatsScreenComponent(
            percentage = 24.8f,
            currentWordsNumber = 1240,
            remainingWordsNumber = 3760,
            totalWordsNumber = 5000,
            levelProgressDataModel = FakeDataSamples.fakeDataForCerfLevelStatistics
        )
    }
}