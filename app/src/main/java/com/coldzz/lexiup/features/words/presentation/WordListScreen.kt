package com.coldzz.lexiup.features.words.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.features.words.domain.model.LevelCerf
import com.coldzz.lexiup.features.words.domain.model.OxfordWords
import com.coldzz.lexiup.features.words.presentation.components.MySearchBar
import com.coldzz.lexiup.features.words.presentation.components.WordsListElement
import com.coldzz.lexiup.features.words.presentation.viewmodel.WordsListViewModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import kotlin.random.Random

@Composable
fun WordListScreen(wordsListViewModel: WordsListViewModel = hiltViewModel()) {
    /*
    * since our screen composable is stateless for testing purposes and preview
    * we create viewmodel in separate composable and just pass the data to the stateless one
    */

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
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                MySearchBar(
                    onSearch = {},
                    dataForSearch = wordsList
                )

            }

        }
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues)
        ) {
            items(
                wordsList,
                key = { word ->
                    word.id
                }
            ) { word ->
                WordsListElement(
                    title = word.word,
                    level = word.level,
                    partOfSpeech = word.partOfSpeech
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WordListScreenPreview() {
    val fakeWordsList = mutableListOf(
        OxfordWords(
            id = Random.nextInt(),
            word = "discover",
            partOfSpeech = "noun",
            level = LevelCerf.A2
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "swim",
            partOfSpeech = "verb",
            level = LevelCerf.A1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "run",
            partOfSpeech = "verb",
            level = LevelCerf.A2
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "funny",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "dummy",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "window",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "fun",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "dog",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "rabbit",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "running",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
        OxfordWords(
            id = Random.nextInt(),
            word = "sand",
            partOfSpeech = "adjective",
            level = LevelCerf.C1
        ),
    )
    LexiUpTheme {
        WordListScreenContent(fakeWordsList)
    }
}