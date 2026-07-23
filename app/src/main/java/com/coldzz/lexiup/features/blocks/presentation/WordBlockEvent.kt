package com.coldzz.lexiup.features.blocks.presentation

sealed interface WordBlockEvent {
    data class ShowToast(val message: String) : WordBlockEvent
    data object CloseScreen : WordBlockEvent
}