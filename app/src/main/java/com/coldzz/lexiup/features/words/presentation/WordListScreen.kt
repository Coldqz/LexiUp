package com.coldzz.lexiup.features.words.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.features.words.presentation.components.WordListScreenComponent
import com.coldzz.lexiup.features.words.presentation.viewmodel.WordsListViewModel

private const val TAG = "WordListScreen"

@Composable
fun WordListScreen(wordsListViewModel: WordsListViewModel = hiltViewModel()) {
    /*
    * since our screen composable is stateless for testing purposes and preview
    * we create viewmodel in separate composable and just pass the data to the stateless one
    */

    val wordsList by wordsListViewModel.wordsList.collectAsState()
    WordListScreenComponent(
        wordsList = wordsList,
        enableSearchBar = true
    ) { wordId -> wordsListViewModel.addWordToReviewBlock(wordId) }
}