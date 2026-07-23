package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
private fun DayStatePlaceholderCore(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(16.dp)
        ) {
            icon()

            Spacer(
                modifier = Modifier.size(16.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun NewBlockDailyLimitPlaceholder(modifier: Modifier = Modifier) {
    DayStatePlaceholderCore(
        title = "You’re done for today.",
        subtitle = "You’ve already learned your new block for today, " +
                "and no repetition blocks are available right now. Come back tomorrow for more.",
        icon = {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(16))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_celebration),
                    contentDescription = "New blocks limit reached message",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                )
            }
        }
    )
}

@Composable
fun DailyLimitPlaceholder(modifier: Modifier = Modifier) {
    DayStatePlaceholderCore(
        title = "That’s enough for today",
        subtitle = "You’ve reached today’s learning limit. " +
                "Take a break and continue tomorrow to keep your progress steady.",
        icon = {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(16))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_coffee),
                    contentDescription = "Limit reached message",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                )
            }
        }
    )
}

@Composable
fun RestPlaceholder(modifier: Modifier = Modifier) {
    DayStatePlaceholderCore(
        title = "Time to rest",
        subtitle = "Give your memory time to make new connections.",
        icon = {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(16))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_psychology),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                )
            }
        }
    )
}

@Preview
@Composable
private fun DailyLimitPlaceholderPreview() {
    LexiUpTheme {
        DailyLimitPlaceholder()
    }
}

@Preview
@Composable
private fun RestPlaceholderPreview() {
    LexiUpTheme {
        RestPlaceholder()
    }
}

@Preview
@Composable
private fun NewBlockDailyLimitPlaceholderPreview() {
    LexiUpTheme {
        NewBlockDailyLimitPlaceholder()
    }
}