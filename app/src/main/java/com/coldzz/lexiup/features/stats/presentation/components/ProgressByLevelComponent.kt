package com.coldzz.lexiup.features.stats.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.stats.presentation.LevelProgressDataModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun ProgressByLevelComponent(
    levelProgressDataModel: List<LevelProgressDataModel>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 2,
        modifier = modifier.fillMaxWidth(),
    ) {
        levelProgressDataModel.forEach { element ->
            ProgressByLevelElementComponent(
                level = element.level,
                percentage = element.percentage,
                modifier = Modifier.fillMaxWidth(0.48f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProgressByLevelComponentPreview() {
    val testData = FakeDataSamples.fakeDataForCerfLevelStatistics
    LexiUpTheme {
        ProgressByLevelComponent(
            levelProgressDataModel = testData
        )
    }
}