package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator

fun OxfordWords.toUiModel(): WordItemUiModel {
    return WordItemUiModel(
        id = id,
        word = word,
        partOfSpeech = partOfSpeech,
        level = level,
        isLearned = isLearned,
        isInReviewBlock = null
    )
}

fun WordsWithReviewBlockIndicator.toUiModel(): WordItemUiModel {
    return WordItemUiModel(
        id = id,
        word = word,
        partOfSpeech = partOfSpeech,
        level = level,
        isLearned = isLearned,
        isInReviewBlock = isInReviewBlock
    )
}