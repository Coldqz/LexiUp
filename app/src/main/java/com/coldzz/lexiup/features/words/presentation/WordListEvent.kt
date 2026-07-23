package com.coldzz.lexiup.features.words.presentation

sealed interface WordListEvent {
    data object CloseScreen : WordListEvent
}