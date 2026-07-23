package com.coldzz.lexiup.features.blocks.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.components.LexiUpErrorDialog
import com.coldzz.lexiup.core.components.LoadingStateIndicator
import com.coldzz.lexiup.features.blocks.presentation.components.BlockCreatingScreenComponent
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.BlockCreatingViewModel
import com.coldzz.lexiup.features.words.presentation.components.WordListComponentMode

@Composable
fun BlockCreatingScreen(
    blockCreatingViewModel: BlockCreatingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val blockCreatingUiState by blockCreatingViewModel.blockCreatingUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        blockCreatingViewModel.events.collect { event ->
            when (event) {
                is BlockCreatingEvent.ShowToast -> {
                    Toast.makeText(
                        context, event.message, Toast.LENGTH_SHORT
                    ).show()
                }

                BlockCreatingEvent.CloseScreen -> {
                    navController.popBackStack()
                }
            }
        }
    }

    when(blockCreatingUiState) {
        ResultUiState.Loading -> {
            LoadingStateIndicator()
        }
        is ResultUiState.Success -> {
            val uiState = (blockCreatingUiState as ResultUiState.Success<BlockCreatingUiState>).data
            val mode = WordListComponentMode.MultiSelectMode(
                checkedList = uiState.checkedList,
                onSelectedChange = { wordId ->
                    blockCreatingViewModel.onSelectedChange(wordId)
                }
            )
            BlockCreatingScreenComponent(
                wordsList = uiState.wordsList,
                searchBarList = uiState.searchBarList,
                isCreateButtonLoading = uiState.isCreateButtonLoading,
                listMode = mode,
                searchBarListMode = mode,
                actionOnElementClick = { wordId ->
                    blockCreatingViewModel.onSelectedChange(wordId)
                },
                actionOnSuggestWordsButton = { blockCreatingViewModel.suggestWords() },
                actionOnCreateButton = { blockCreatingViewModel.createBlock() }
            )
        }
        is ResultUiState.Error -> {
            val throwable = (blockCreatingUiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { blockCreatingViewModel.loadData() },
                onExit = { blockCreatingViewModel.closeScreen() }
            )
        }
    }
}