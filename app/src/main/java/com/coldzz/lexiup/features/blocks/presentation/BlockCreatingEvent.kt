package com.coldzz.lexiup.features.blocks.presentation

sealed interface BlockCreatingEvent {
    data class ShowToast(val message: String) : BlockCreatingEvent
    data object CloseScreen: BlockCreatingEvent
}