package com.coldzz.lexiup.features.blocks.presentation

sealed interface ReviewBlockEvent {
    data object CloseScreen : ReviewBlockEvent
}