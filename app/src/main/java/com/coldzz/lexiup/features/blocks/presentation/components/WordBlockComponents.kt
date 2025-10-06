package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun ActiveWordBlockComponent(
    title: String,
    description: String,
    learningLevelIndicator: LearningLevelIndicator,
    isActive: Boolean,
    onActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoreWordBlockComponent(
        title = title,
        learningLevelEnabled = true,
        learningLevelIndicator = learningLevelIndicator,
        description = description,
        actionButton = {
            if (isActive) {
                Button(
                    onClick = onActionButtonClick
                ) {
                    Text(
                        text = stringResource(R.string.start),

                    )
                }
            } else {
                Button(
                    onClick = onActionButtonClick,
                    enabled = false,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.start),
                        )
                        Icon(
                            imageVector = Icons.Default.Lock, "Lock icon"
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}

// block with description and another button to open and without learning level
@Composable
fun LearnedWordBlockComponent(
    title: String,
    description: String,
    onActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoreWordBlockComponent(
        title = title,
        description = description,
        learningLevelEnabled = false,
        learningLevelIndicator = LearningLevelIndicator.One,
        actionButton = {
            Button(
                onClick = onActionButtonClick
            ) {
                Text(
                    text = "Repeat",
                )
            }
        }
    )
}

// block without description and with another button to open
@Composable
fun CustomWordBlockComponent(
    title: String,
    learningLevelIndicator: LearningLevelIndicator,
    onActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoreWordBlockComponent(
        title = title,
        description = null,
        learningLevelEnabled = true,
        learningLevelIndicator = learningLevelIndicator,
        actionButton = {
            IconButton(
                onClick = onActionButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Forward arrow icon"
                )
            }
        }
    )
}

// main block component that could be changed to look like learned or custom block component
@Composable
private fun CoreWordBlockComponent(
    title: String,
    description: String?,
    learningLevelEnabled: Boolean,
    learningLevelIndicator: LearningLevelIndicator,
    actionButton: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = colorResource(R.color.light_gray),
                            shape = RoundedCornerShape(16)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_book),
                        contentDescription = stringResource(R.string.word_icon)
                    )
                }
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    description?.let {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                if (learningLevelEnabled) {
                    Icon(
                        imageVector = when (learningLevelIndicator) {
                            LearningLevelIndicator.One ->
                                ImageVector.vectorResource(LearningLevelIndicator.One.resourceId)
                            LearningLevelIndicator.Two ->
                                ImageVector.vectorResource(LearningLevelIndicator.Two.resourceId)
                            LearningLevelIndicator.Three ->
                                ImageVector.vectorResource(LearningLevelIndicator.Three.resourceId)
                            LearningLevelIndicator.Four ->
                                ImageVector.vectorResource(LearningLevelIndicator.Four.resourceId)
                        },
                        contentDescription = stringResource(R.string.learning_level_icon)
                    )
                }
            }
            actionButton()
        }
    }
}

@Preview
@Composable
private fun ActiveWordBlockComponentPreview() {
    LexiUpTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActiveWordBlockComponent(
                title = "Word block 1",
                description = "Available now",
                learningLevelIndicator = LearningLevelIndicator.Three,
                onActionButtonClick = {},
                isActive = true,
                modifier = Modifier
            )
            ActiveWordBlockComponent(
                title = "Word block 1",
                description = "Available now",
                learningLevelIndicator = LearningLevelIndicator.Three,
                onActionButtonClick = {},
                isActive = false,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun LearnedWordBlockComponentPreview() {
    LexiUpTheme {
        LearnedWordBlockComponent(
            title = "Word block 1",
            description = "Completed on 01.01.2025",
            onActionButtonClick = {},
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun CustomWordBlockComponentPreview() {
    CustomWordBlockComponent(
        title = "Traveling",
        learningLevelIndicator = LearningLevelIndicator.Three,
        onActionButtonClick = {},
        modifier = Modifier
    )
}