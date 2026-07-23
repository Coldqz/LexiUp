package com.coldzz.lexiup.features.stats.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun WordsCountLabelComponent(
    title: String,
    number: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .sizeIn(
                    minHeight = 55.dp,
                    minWidth = 85.dp
                )
                .padding(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                text = title.uppercase()
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                text = NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
            )
        }
    }
}

@Preview
@Composable
private fun WordsCountLabelComponentPreview() {
    LexiUpTheme {
        Row {
            WordsCountLabelComponent(
                title = "Current",
                number = 1240,
            )
            Spacer(Modifier.size(16.dp))
            WordsCountLabelComponent(
                title = "Remaining",
                number = 3760,
            )
        }
    }
}