package com.coldzz.lexiup.features.words.data.local.projection

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock

data class BlockWordsListData(
    val block: WordBlock,
    val words: List<WordsWithReviewBlockIndicator>
)