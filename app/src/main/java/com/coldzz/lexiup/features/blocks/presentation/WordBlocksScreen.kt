package com.coldzz.lexiup.features.blocks.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator
import com.coldzz.lexiup.features.blocks.presentation.components.ActiveBlocksCounter
import com.coldzz.lexiup.features.blocks.presentation.components.ActiveWordBlockComponent
import com.coldzz.lexiup.features.blocks.presentation.components.BlockCategoryDivider
import com.coldzz.lexiup.features.blocks.presentation.components.LearnedWordBlockComponent
import com.coldzz.lexiup.features.blocks.presentation.components.PlannedWordBlockComponent
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.WordBlockViewModel
import com.coldzz.lexiup.features.navigation.NavRoutes
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun WordBlockScreen(
    wordBlockViewModel: WordBlockViewModel = hiltViewModel(),
    navController: NavController
) {
    val blocksList by wordBlockViewModel.blocksList.collectAsState()
    WordBlocksScreenContent(
        actionOnInfoButton = {},
        actionOnBookmarkButton = {
            navController.navigate(NavRoutes.ReviewBlock)
        },
        actionOnPlannedIconButton = {
            navController.navigate(NavRoutes.BlockCreatingScreen)
        },
        blocksList = blocksList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WordBlocksScreenContent(
    actionOnInfoButton: () -> Unit,
    actionOnBookmarkButton: () -> Unit,
    actionOnPlannedIconButton: () -> Unit,
    blocksList: BlockItemUiModelFormattedList,
    modifier: Modifier = Modifier
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
                            contentDescription = "Information icon"
                        )
                    }
                    IconButton(
                        onClick = actionOnInfoButton
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Information icon"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier.padding(innerPadding)
        ) {
            // TODO: refactor this for performance
            item {
                BlockCategoryDivider(
                    title = "Active blocks",
                    actionOnIconClick = {},
                    counter = ActiveBlocksCounter(3, 4)
                )
            }
            items(blocksList.activeBlocks) { block ->
                ActiveWordBlockComponent(
                    title = "Word Block ${block.blockNumber}",
                    learningLevelIndicator = LearningLevelIndicator.One,
                    // TODO: check if is it dangerous to use !! here
                    availableAt = block.availableAt!!,
                    onActionButtonClick = { },
                )
            }
            item {
                BlockCategoryDivider(
                    title = "Planned blocks",
                    actionOnIconClick = actionOnPlannedIconButton,
                    counter = null
                )
            }
            items(blocksList.plannedBlocks) { block ->
                PlannedWordBlockComponent(
                    title = "Word Block ${block.blockNumber}",
                    learningLevelIndicator = LearningLevelIndicator.One,
                    onActionButtonClick = { },
                )
            }
            if (blocksList.learnedBlocks.isNotEmpty()) {
                item {
                    BlockCategoryDivider(
                        title = "Learned blocks",
                        counter = null
                    )
                }
                items(blocksList.learnedBlocks) { block ->
                    LearnedWordBlockComponent(
                        title = "Word Block ${block.blockNumber}",
                        // TODO: check if is it dangerous to use !! here
                        completedAt = block.completedAt,
                        onActionButtonClick = { },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WordBlocksScreenContentPreview() {
    LexiUpTheme {
        WordBlocksScreenContent(
            actionOnBookmarkButton = {},
            actionOnInfoButton = {},
            actionOnPlannedIconButton = {},
            blocksList = BlockItemUiModelFormattedList.formattedList(FakeDataSamples.fakeBlocksList)
        )
    }
}