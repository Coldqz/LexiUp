package com.coldzz.lexiup.features.blocks.data.local.projection

import com.coldzz.lexiup.features.words.data.local.entities.WordDetails
import com.coldzz.lexiup.features.words.data.local.entities.WordMeaning

class WordDetailWithMeanings (
    val details: WordDetails,
    val meanings: List<WordMeaning>
)