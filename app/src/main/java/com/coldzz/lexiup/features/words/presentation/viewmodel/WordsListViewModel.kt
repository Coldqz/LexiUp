package com.coldzz.lexiup.features.words.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsListViewModel @Inject constructor(private val wordsRepository: WordRepository, private val blocksRepository: WordBlockRepository) : ViewModel() {
    private val _wordsList: MutableStateFlow<List<WordsWithReviewBlockIndicator>> = MutableStateFlow(emptyList())
    val wordsList: StateFlow<List<WordsWithReviewBlockIndicator>> = _wordsList.asStateFlow()

    init {
        viewModelScope.launch {
            wordsRepository.getWordsAndReviewBlockIndicator(blocksRepository.getCachedReviewBlockId()).collect { wordsList ->
                _wordsList.value = wordsList
            }
        }
    }

    fun addWordToReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blocksRepository.addWordToReviewBlock(wordId)
        }
    }

    // TODO: for debug only, delete it after
    fun testWork() {
        Log.d(TAG, repository.toString())
        viewModelScope.launch {
            repository.addWord(
                OxfordWords(
                    word = "test",
                    level = LevelCerf.A1
                )
            )
        }
    }

    companion object {
        const val TAG = "WordsViewModel"
    }
}