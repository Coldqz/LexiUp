package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.blocks.presentation.dashedBorder
import com.coldzz.lexiup.ui.theme.LexiUpTheme


/**
 * Core placeholders components. Make [subtitle] null to disable it.
 * */
@Composable
private fun CoreCategoryPlaceholder(
    title: String,
    subtitle: String?,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onElementClick: (() -> Unit)? = null
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .dashedBorder(
                brush = SolidColor(
                    value = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(16.dp),
            )
            // Conditionally add clickable modifier if an action is provided
            .then(
                if (onElementClick != null) {
                    Modifier.clickable { onElementClick() }
                } else {
                    Modifier
                }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
        ) {
            icon()
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            subtitle?.let {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun LearnNowCategoryPlaceholder() {
    CoreCategoryPlaceholder(
        title = stringResource(R.string.no_active_blocks),
        subtitle = stringResource(R.string.activate_some_to_start),
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_opened_book),
                contentDescription = stringResource(R.string.learn_now_icon)
            )
        },
    )
}

@Composable
fun UpcomingCategoryPlaceholder() {
    CoreCategoryPlaceholder(
        title = stringResource(R.string.queue_is_empty),
        subtitle = stringResource(R.string.next_sessions_appear_here),
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_event_upcoming),
                contentDescription = stringResource(R.string.upcoming_blocks_icon)
            )
        },
    )
}

@Composable
fun PlannedCategoryPlaceholder() {
    CoreCategoryPlaceholder(
        title = "Future blocks",
        subtitle = stringResource(R.string.tap_to_add_a_new_block),
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_calendar_today),
                contentDescription = stringResource(R.string.planned_blocks_icon)
            )
        },
        onElementClick = null
    )
}

@Composable
fun LearnedCategoryPlaceholder() {
    CoreCategoryPlaceholder(
        title = stringResource(R.string.finish_a_block_to_see_it_here),
        subtitle = null,
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_medal),
                contentDescription = stringResource(R.string.learned_blocks_icon)
            )
        },
    )
}

@Preview
@Composable
private fun CoreCategoryPlaceholderPreview() {
    LexiUpTheme {
        Scaffold { paddingValues ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                LearnNowCategoryPlaceholder()

                UpcomingCategoryPlaceholder()

                PlannedCategoryPlaceholder()

                LearnedCategoryPlaceholder()
            }
        }
    }
}