package com.coldzz.lexiup.features.words.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.features.words.domain.model.LevelCerf
import com.coldzz.lexiup.features.words.domain.model.OxfordWords
import com.coldzz.lexiup.features.words.presentation.viewmodel.WordsListViewModel

@Composable
fun WordListScreen(wordsListViewModel: WordsListViewModel = hiltViewModel()) {
    val wordsList by wordsListViewModel.wordsList.collectAsState()
    WordListScreenContent(wordsList)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WordListScreenContent(
    wordsList: List<OxfordWords>
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Oxford 5000", style = MaterialTheme.typography.titleLarge)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues)
        ) {
            items(wordsList) { element ->
                WordsListElement(
                    title = element.word,
                    level = element.level,
                    partOfSpeech = element.partOfSpeech
                )
            }
        }
    }
}

@Preview
@Composable
private fun WordListScreenPreview() {
    val fakeWordsList = listOf(
        OxfordWords(word = "discover", partOfSpeech = "noun", level = LevelCerf.A2),
        OxfordWords(word = "swim", partOfSpeech = "verb", level = LevelCerf.A1),
        OxfordWords(word = "run", partOfSpeech = "verb", level = LevelCerf.A2),
        OxfordWords(word = "funny", partOfSpeech = "adjective", level = LevelCerf.C1),
    )
    WordListScreenContent(fakeWordsList)
}