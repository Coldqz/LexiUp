package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.features.words.presentation.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewBlockScreenViewModel @Inject constructor(private val blocksRepository: WordBlockRepository): ViewModel() {
    private val _wordsDetailList: MutableStateFlow<List<WordItemUiModel>> = MutableStateFlow(emptyList())
    val wordsDetailList: StateFlow<List<WordItemUiModel>> = _wordsDetailList.asStateFlow()

    init {
        viewModelScope.launch {
            blocksRepository.getWordsFromBlock().collect { wordsList ->
                _wordsDetailList.value = wordsList.map { it.toUiModel() }
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