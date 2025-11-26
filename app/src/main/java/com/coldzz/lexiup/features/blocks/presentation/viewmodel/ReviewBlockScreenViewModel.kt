package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewBlockScreenViewModel @Inject constructor(private val repository: WordBlockRepository): ViewModel() {
    private val _wordsDetailList: MutableStateFlow<List<OxfordWords>> = MutableStateFlow(emptyList())
    val wordsDetailList: StateFlow<List<OxfordWords>> = _wordsDetailList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWordPreviewDetailsFromBlock().collect { wordsList ->
                _wordsDetailList.value = wordsList
            }
        }

    }
}