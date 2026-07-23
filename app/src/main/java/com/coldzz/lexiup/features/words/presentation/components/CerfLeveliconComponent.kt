package com.coldzz.lexiup.features.words.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.core.common.CerfLevel
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun CerfLevelIconComponent(
    level: CerfLevel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = level.toString(),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun CerfLevelIconComponentPreview() {
    LexiUpTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CerfLevelIconComponent(CerfLevel.A1)
            CerfLevelIconComponent(CerfLevel.B1)
            CerfLevelIconComponent(CerfLevel.C1)
        }
    }
}