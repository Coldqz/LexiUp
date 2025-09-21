package com.coldzz.lexiup.features.words.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.words.domain.model.LevelCerf
import com.coldzz.lexiup.features.words.domain.model.OxfordWords
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

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