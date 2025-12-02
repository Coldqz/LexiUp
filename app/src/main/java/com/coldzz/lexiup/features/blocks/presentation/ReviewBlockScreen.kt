package com.coldzz.lexiup.features.blocks.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.ReviewBlockScreenViewModel
import com.coldzz.lexiup.features.words.presentation.components.WordListScreenComponent

@Composable
fun ReviewBlockScreen(reviewViewModel: ReviewBlockScreenViewModel = hiltViewModel()) {
    val wordsList by reviewViewModel.wordsDetailList.collectAsState()
    WordListScreenComponent(
        wordsList, false,
        actionAddToReviewBlock = { wordId -> reviewViewModel.addWordToReviewBlock(wordId) },
        actionRemoveFromReviewBlock = { wordId -> reviewViewModel.removeWordFromReviewBlock(wordId) })
}