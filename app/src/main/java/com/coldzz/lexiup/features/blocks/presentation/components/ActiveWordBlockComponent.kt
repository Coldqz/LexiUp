package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.ui.theme.LexiUpTheme

/**
 * Leave dropDownItems empty if you want to turn it off*/
@Composable
fun ActiveWordBlockComponent(
    title: String,
    learningLevelIndicator: LearningLevelIndicator,
    availableAt: String,
    onActionButtonClick: () -> Unit,
    isActionButtonActive: Boolean,
    actionOnElementClick: () -> Unit,
    modifier: Modifier = Modifier,
    dropdownItems: List<DropDownItemModel> = emptyList()
) {
    val iconRes = if (isActionButtonActive) R.drawable.ic_bolt else R.drawable.ic_lock_clock

    CoreWordBlockComponent(
        title = title,
        learningLevelIndicator = learningLevelIndicator,
        label = availableAt,
        actionButton = {
            Button(
                onClick = onActionButtonClick,
                enabled = isActionButtonActive,
                contentPadding = PaddingValues(start = 12.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = "Lock icon",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.start))
            }
        },
        actionOnElementClick = actionOnElementClick,
        dropDownItems = dropdownItems,
        modifier = modifier
    )
}

@Preview
@Composable
private fun ActiveWordBlockComponentPreview() {
    val dropDownItems = listOf(
        DropDownItemModel(
            "Deactivate"
        ) {},
        DropDownItemModel(
            "Delete"
        ) {}
    )

    LexiUpTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActiveWordBlockComponent(
                title = "Word block 1",
                learningLevelIndicator = LearningLevelIndicator.Four,
                availableAt = FakeDataSamples.fakeBlocksList[0].availableAt.toString(),
                onActionButtonClick = {},
                actionOnElementClick = {},
                isActionButtonActive = false,
                dropdownItems = dropDownItems
            )
            ActiveWordBlockComponent(
                title = "Word block 1",
                learningLevelIndicator = LearningLevelIndicator.Four,
                availableAt = FakeDataSamples.fakeBlocksList[1].availableAt.toString(),
                onActionButtonClick = {},
                isActionButtonActive = true,
                actionOnElementClick = {},
                dropdownItems = dropDownItems
            )
        }
    }
}