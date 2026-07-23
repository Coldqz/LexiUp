package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.blocks.domain.AvailabilityLabels
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.blocks.domain.RawBlocksModel
import com.coldzz.lexiup.features.blocks.domain.use_case.BlocksDayStatus
import com.coldzz.lexiup.features.blocks.domain.use_case.GenerateAvailableAtLabelUseCase
import com.coldzz.lexiup.features.blocks.domain.use_case.MapRawBlocksUseCase
import com.coldzz.lexiup.features.blocks.presentation.BlocksScreenUiState
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordBlockScreenComponent(
    actionOnInfoButton: () -> Unit,
    actionOnBookmarkButton: () -> Unit,
    actionOnBlockClick:(Int) -> Unit,
    actionOnActiveStartButton: (Int) -> Unit,
    actionOnActiveBlockDeactivate: (Int) -> Unit,
    actionOnActiveBlockDelete: (Int) -> Unit,
    actionOnFloatingActionButton: () -> Unit,
    actionOnPlannedBlockDelete: (Int) -> Unit,
    actionOnPlannedBlockActivate: (Int) -> Unit,
    actionOnLearnedBlockRepeat: (Int) -> Unit,
    uiState: BlocksScreenUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.learning),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(
                        onClick = actionOnBookmarkButton
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark),
                            contentDescription = stringResource(R.string.add_to_review_block_icon)
                        )
                    }
                    IconButton(
                        onClick = actionOnInfoButton
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.information_icon)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = stringResource(R.string.create_block))
                },
                icon = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_to_review_block_icon))
                },
                onClick = actionOnFloatingActionButton,
                shape = CircleShape
            )
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                BlockCategoryDivider(
                    title = stringResource(R.string.active_blocks),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_bolt),
                            contentDescription = stringResource(R.string.active_blocks_icon)
                        )
                    },
                    actionOnAddIconClick = null,
                    counter = ActiveBlocksCounterModel(
                        currentNumber = uiState.getActiveBlocksCount,
                        totalNumber = Constants.MAX_ACTIVE_BLOCKS_COUNT
                    )
                )
            }
            item {
                BlockCategoryDivider(
                    title = stringResource(R.string.learn_now),
                    titleTextStyle = MaterialTheme.typography.bodyLarge,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_play_circle),
                            contentDescription = stringResource(R.string.learn_now_blocks_icon)
                        )
                    },
                    actionOnAddIconClick = null,
                    counter = null
                )
            }
            when (uiState.dayStatus) {
                is BlocksDayStatus.CanLearn -> {
                    if (uiState.hasLearnNowBlocks) {
                        items(uiState.learnNowBlocks) { block ->
                            ActiveWordBlockComponent(
                                title = stringResource(R.string.word_block, block.blockNumber),
                                learningLevelIndicator = block.learningLevel,
                                availableAt = when (block.availableAt) {
                                    is AvailabilityLabels.Today -> stringResource(R.string.available_now)
                                    is AvailabilityLabels.Tomorrow -> stringResource(R.string.available_tomorrow)
                                    is AvailabilityLabels.InDays -> pluralStringResource(
                                        R.plurals.available_in_days,
                                        block.availableAt.days,
                                        block.availableAt.days
                                    )

                                    is AvailabilityLabels.Empty -> ""
                                },
                                onActionButtonClick = { actionOnActiveStartButton(block.id) },
                                isActionButtonActive = block.isLearnButtonActive,
                                actionOnElementClick = { actionOnBlockClick(block.id) },
                                dropdownItems = listOf(
                                    DropDownItemModel(
                                        text = stringResource(R.string.deactivate),
                                        action = { actionOnActiveBlockDeactivate(block.id) }
                                    ),
                                    DropDownItemModel(
                                        text = stringResource(R.string.delete),
                                        action = { actionOnActiveBlockDelete(block.id) }
                                    )
                                )
                            )
                        }
                    } else {
                        item {
                            LearnNowCategoryPlaceholder()
                        }
                    }
                }

                is BlocksDayStatus.DailyLimitReached -> {
                    item {
                        DailyLimitPlaceholder()
                    }
                }

                is BlocksDayStatus.Rest -> {
                    item {
                        RestPlaceholder()
                    }
                }

                is BlocksDayStatus.NewBlockDailyLimitReached -> {
                    item {
                        NewBlockDailyLimitPlaceholder()
                    }
                }
            }
            item {
                BlockCategoryDivider(
                    title = stringResource(R.string.upcoming_blocks),
                    titleTextStyle = MaterialTheme.typography.bodyLarge,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_schedule),
                            contentDescription = stringResource(R.string.upcoming_blocks_icon)
                        )
                    },
                    actionOnAddIconClick = null,
                    counter = null
                )
            }
            if (uiState.hasUpcomingBlocks) {
                items(uiState.upcomingBlocks) { block ->
                    ActiveWordBlockComponent(
                        title = stringResource(R.string.word_block, block.blockNumber),
                        learningLevelIndicator = block.learningLevel,
                        availableAt = when (block.availableAt) {
                            is AvailabilityLabels.Today -> stringResource(R.string.available_now)

                            is AvailabilityLabels.Tomorrow -> stringResource(R.string.available_tomorrow)

                            is AvailabilityLabels.InDays -> pluralStringResource(
                                R.plurals.available_in_days,
                                block.availableAt.days,
                                block.availableAt.days
                            )

                            is AvailabilityLabels.Empty -> ""
                        },
                        onActionButtonClick = { actionOnActiveStartButton(block.id) },
                        isActionButtonActive = block.isLearnButtonActive,
                        actionOnElementClick = { actionOnBlockClick(block.id) },
                        dropdownItems = listOf(
                            DropDownItemModel(
                                text = stringResource(R.string.deactivate),
                                action = { actionOnActiveBlockDeactivate(block.id) }
                            ),
                            DropDownItemModel(
                                text = stringResource(R.string.delete),
                                action = { actionOnActiveBlockDelete(block.id) }
                            )
                        )
                    )
                }
            } else {
                item {
                    UpcomingCategoryPlaceholder()
                }
            }

            item {
                BlockCategoryDivider(
                    title = stringResource(R.string.planned_blocks),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_calendar_today),
                            contentDescription = stringResource(R.string.planned_blocks_icon)
                        )
                    },
                    counter = null
                )
            }
            if (uiState.hasPlannedBlocks) {
                items(uiState.plannedBlocks) { block ->
                    PlannedWordBlockComponent(
                        title = stringResource(R.string.word_block, block.blockNumber),
                        actionOnElementClick = { actionOnBlockClick(block.id) },
                        actionOnDeleteButton = { actionOnPlannedBlockDelete(block.id) },
                        actionOnActivateButton = { actionOnPlannedBlockActivate(block.id) },
                        enableActivateButton = uiState.getActiveBlocksCount < Constants.MAX_ACTIVE_BLOCKS_COUNT
                    )
                }
            } else {
                item {
                    PlannedCategoryPlaceholder()
                }
            }
            item {
                BlockCategoryDivider(
                    title = stringResource(R.string.learned_blocks),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_checked_circle),
                            contentDescription = stringResource(R.string.learned_blocks_icon)
                        )
                    },
                    counter = null
                )
            }
            if (uiState.hasLearnedBlocks) {
                items(uiState.learnedBlocks) { block ->
                    LearnedWordBlockComponent(
                        title = stringResource(R.string.word_block, block.blockNumber),
                        completedAt = block.completedAt,
                        onActionButtonClick = { actionOnLearnedBlockRepeat(block.id) },
                        actionOnElementClick = { actionOnBlockClick(block.id) },
                    )
                }
            } else {
                item {
                    LearnedCategoryPlaceholder()
                }
            }
        }
    }
}

@Preview
@Composable
private fun WordBlocksScreenContentPreview() {
    val mapper = MapRawBlocksUseCase(GenerateAvailableAtLabelUseCase())
    val dayStatus = BlocksDayStatus.CanLearn
    val blocksList = RawBlocksModel(
        dayStatus = dayStatus,
        learnNowBlocks = emptyList(),
        upcomingBlocks = FakeDataSamples.fakeBlocksList.take(2),
        plannedBlocks = FakeDataSamples.fakeBlocksList.take(2)
            .map { it.copy(blockType = BlockTypes.PLANNED) },
        learnedBlocks = FakeDataSamples.fakeBlocksList.take(2)
            .map { it.copy(blockType = BlockTypes.LEARNED) }
    )

    val uiState = mapper(blocksList)

    LexiUpTheme {
        WordBlockScreenComponent(
            actionOnBookmarkButton = {},
            actionOnBlockClick = {},
            actionOnInfoButton = {},
            actionOnActiveStartButton = {},
            actionOnActiveBlockDelete = {},
            actionOnActiveBlockDeactivate = {},
            actionOnFloatingActionButton = {},
            actionOnPlannedBlockDelete = {},
            actionOnPlannedBlockActivate = {},
            actionOnLearnedBlockRepeat = {},
            uiState = uiState
        )
    }
}