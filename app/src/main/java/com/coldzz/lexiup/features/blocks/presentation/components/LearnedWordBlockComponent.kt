package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.Constants.DATE_FORMATTER
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.ui.theme.LexiUpTheme

// block with description and another button to open and without learning level
@Composable
fun LearnedWordBlockComponent(
    title: String,
    completedAt: String,
    onActionButtonClick: () -> Unit,
    actionOnElementClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoreWordBlockComponent(
        title = title,
        label = if (completedAt.isNotEmpty()) stringResource(R.string.completed_at, completedAt) else stringResource(
            R.string.completed
        ),
        learningLevelIndicator = null,
        actionButton = {
            OutlinedButton(
                onClick = onActionButtonClick,
                contentPadding = PaddingValues(start = 12.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_replay),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.repeat))
            }
        },
        actionOnElementClick = actionOnElementClick
    )
}

@Preview
@Composable
private fun LearnedWordBlockComponentPreview() {
    LexiUpTheme {
        LearnedWordBlockComponent(
            title = "Word Block ${FakeDataSamples.fakeBlocksList[4].id}",
            completedAt = FakeDataSamples.fakeBlocksList[5].completedAt?.format(DATE_FORMATTER)
                .orEmpty(),
            onActionButtonClick = {},
            actionOnElementClick = {},
            modifier = Modifier
        )
    }
}