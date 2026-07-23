package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import javax.inject.Inject

 /**
 * Pick random word from wordsBank, also ensure that it won't be already answered one
 * */
class PickRandomWordUseCase @Inject constructor() {
    operator fun invoke(
        wordsBank: List<PickQuizWordsData>,
        correctlyAnsweredWordsBank: List<Int>,
    ): PickQuizWordsData {
        return wordsBank.filterNot {
            correctlyAnsweredWordsBank.contains(it.id)
        }.random()
    }
}