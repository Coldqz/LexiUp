package com.coldzz.lexiup.features.blocks.presentation.components

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
import com.coldzz.lexiup.ui.theme.LexiUpTheme

// block without description and with another button to open
@Composable
fun PlannedWordBlockComponent(
    title: String,
    actionOnElementClick: () -> Unit,
    actionOnDeleteButton: () -> Unit,
    actionOnActivateButton: () -> Unit,
    enableActivateButton: Boolean,
    modifier: Modifier = Modifier
) {

    CoreWordBlockComponent(
        title = title,
        label = null,
        learningLevelIndicator = null,
        actionButton = {
            Button(
                enabled = enableActivateButton,
                onClick = actionOnActivateButton,
                contentPadding = PaddingValues(start = 12.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play_circle),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.activate))
            }
        },
        dropDownItems = listOf(
            DropDownItemModel(
                text = stringResource(R.string.delete),
                action = actionOnDeleteButton
            )
        ),
        actionOnElementClick = actionOnElementClick,
        modifier = modifier
    )
}

@Preview
@Composable
private fun PlannedWordBlockComponentPreview() {
    LexiUpTheme {
        PlannedWordBlockComponent(
            title = "Traveling",
            actionOnElementClick = {},
            actionOnDeleteButton = {},
            actionOnActivateButton = {},
            enableActivateButton = true,
            modifier = Modifier,
        )
    }
}
