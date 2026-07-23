package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.ui.theme.LexiUpTheme


@Composable
        /**
         * Divider for blocks categories. Optional parameter [counter] is to show block number restrictions.
         *
         * Component have icon before the text keep [icon] null to turn it off.
         *
         * Also, this component has plus icon button, [actionOnAddIconClick] parameter can be set to null the add button.
         *
         * [titleTextStyle] parameter is to set title text style.
         * */
fun BlockCategoryDivider(
    title: String,
    counter: ActiveBlocksCounterModel?,
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    icon: @Composable (() -> Unit)? = null,
    actionOnAddIconClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.invoke()
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = titleTextStyle,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            counter?.let {
                Text(
                    text = "${counter.currentNumber}/${counter.totalNumber}",
                    style = titleTextStyle,
                    fontWeight = FontWeight.Bold
                )
            }
            actionOnAddIconClick?.let {
                IconButton(
                    onClick = actionOnAddIconClick
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

data class ActiveBlocksCounterModel(
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
                actionOnAddIconClick = {},
                counter = ActiveBlocksCounterModel(3, 4)
            )
            BlockCategoryDivider(
                title = "Custom blocks",
                actionOnAddIconClick = {},
                counter = null
            )
            BlockCategoryDivider(
                title = "Active blocks",
                actionOnAddIconClick = null,
                counter = null
            )
        }
    }
}