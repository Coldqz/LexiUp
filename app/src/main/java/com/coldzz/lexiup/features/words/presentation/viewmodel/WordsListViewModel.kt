package com.coldzz.lexiup.features.words.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.words.data.local.entities.LevelCerf
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsListViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {
    private val _wordsList: MutableStateFlow<List<OxfordWords>> = MutableStateFlow(emptyList())
    val wordsList: StateFlow<List<OxfordWords>> = _wordsList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWords().collect { wordsList ->
                _wordsList.value = wordsList
            }
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