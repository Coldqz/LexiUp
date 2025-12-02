package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewBlockScreenViewModel @Inject constructor(private val blocksRepository: WordBlockRepository): ViewModel() {
    private val _wordsDetailList: MutableStateFlow<List<WordsWithReviewBlockIndicator>> = MutableStateFlow(emptyList())
    val wordsDetailList: StateFlow<List<WordsWithReviewBlockIndicator>> = _wordsDetailList.asStateFlow()

    init {
        viewModelScope.launch {
            blocksRepository.getWordsFromBlock().collect { wordsList ->
                _wordsDetailList.value = wordsList
            }
        }
    }

    fun addWordToReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blocksRepository.addWordToReviewBlock(wordId)
        }
    }

    fun removeWordFromReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blocksRepository.removeWordFromReviewBlock(wordId)
        }
    }
}