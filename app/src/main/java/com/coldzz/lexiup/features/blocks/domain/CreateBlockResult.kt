package com.coldzz.lexiup.features.blocks.domain

sealed class CreateBlockResult {
    object Success: CreateBlockResult()
    object MinWordsNotReached: CreateBlockResult()
}