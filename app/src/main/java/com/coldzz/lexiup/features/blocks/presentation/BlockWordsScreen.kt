package com.coldzz.lexiup.features.blocks.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.components.LexiUpErrorDialog
import com.coldzz.lexiup.core.components.LoadingStateIndicator
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.blocks.presentation.components.BlockWordsListComponent
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.BlockWordsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockWordsScreen(
    navController: NavController,
    blockWordsListViewModel: BlockWordsListViewModel = hiltViewModel()
) {

    val uiState by blockWordsListViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        blockWordsListViewModel.events.collect { events ->
            when(events) {
                BlockWordsListEvents.CloseScreen -> navController.popBackStack()
            }
        }
    }

    when(val state = uiState) {
        is ResultUiState.Success -> {
            BlockWordsListComponent(
                blockNumber = state.data.blockNumber,
                wordsList = state.data.words,
                onWordClickAction = { wordId ->
                    navController.navigate(NavRoutes.WordDetailsScreen(wordId))
                },
                onBackButtonAction = { blockWordsListViewModel.closeScreen() }
            )
        }
        ResultUiState.Loading -> {
            LoadingStateIndicator()
        }
        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { blockWordsListViewModel.loadData() },
                onExit = { blockWordsListViewModel.closeScreen() }
            )
        }
    }
}