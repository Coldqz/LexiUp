package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.ui.theme.LexiUpTheme


/*
* divider for blocks categories, optional parameter counter when we need to show count constraints
* enablePlusButton for add button
* */
@Composable
fun BlockCategoryDivider(
    title: String,
    counter: ActiveBlocksCounter?,
    modifier: Modifier = Modifier,
    actionOnIconClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            counter?.let {
                Text(
                    text = "${counter.currentNumber}/${counter.totalNumber}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            actionOnIconClick?.let {
                IconButton(
                    onClick = actionOnIconClick
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

data class ActiveBlocksCounter(
    val currentNumber: Int,
    val totalNumber: Int
)

@Preview
@Composable
private fun BlockCategoryDividerPreview() {
    LexiUpTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BlockCategoryDivider(
                title = "Active blocks",
                actionOnIconClick = {},
                counter = ActiveBlocksCounter(3, 4)
            )
            BlockCategoryDivider(
                title = "Custom blocks",
                actionOnIconClick = {},
                counter = null
            )
            BlockCategoryDivider(
                title = "Active blocks",
                actionOnIconClick = null,
                counter = null
            )
        }
    }
}