package com.coldzz.lexiup.features.words.presentation

sealed interface WordDetailsEvent {
    data class ShowToast(val message: String) : WordDetailsEvent
    data object CloseScreen : WordDetailsEvent
}