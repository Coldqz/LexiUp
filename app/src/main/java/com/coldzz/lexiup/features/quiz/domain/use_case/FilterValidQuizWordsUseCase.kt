package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import javax.inject.Inject


/**
 * Use case responsible for filtering out words that do not have meaningful definitions.
 * This ensures that the quiz only contains words that the user can actually learn from.
 */
class FilterValidQuizWordsUseCase @Inject constructor() {
    
    operator fun invoke(words: List<PickQuizWordsData>): FilterResult {
        val valid = mutableListOf<PickQuizWordsData>()
        val skipped = mutableListOf<String>()

        words.forEach { wordData ->
            // Check if there is at least one definition that isn't a "not found" placeholder from the API
            val hasValidDefinition = wordData.definition.any {
                // These specific strings are returned by the dictionary service
                // when a word entry exists but lacks content.
                it.definition != "No definition found for this word in dictionary." &&
                it.definition != "No definitions for this part of speech were found"
            }
            if (hasValidDefinition) {
                valid.add(wordData)
            } else {
                skipped.add(wordData.word)
            }
        }

        return FilterResult(valid, skipped)
    }

    data class FilterResult(
        val validWords: List<PickQuizWordsData>,
        val skippedWords: List<String>
    )
}
