package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import javax.inject.Inject

/**
 * Answer options are created from the remaining unanswered words,
 * we shuffle them, add correct answer and shuffle again.
 * Normally we need 3 options and fourth one is our correct option.
 *
 *
 * If there are less then 3 options to choose we pick random ones from already answered.
 * */
class GenerateOptionsUseCase @Inject constructor() {
    operator fun invoke(
        wordsBank: List<PickQuizWordsData>,
        correctlyAnsweredWordsBank: List<Int>,
        currentWord: PickQuizWordsData,
        optionsCount: Int,
    ): List<PickQuizWordsData> {

        val remainingWordsToAnswerList = wordsBank.filterNot {
            correctlyAnsweredWordsBank.contains(it.id)
        }

        // here we remove current word so that it would not appear in options
        val remainingWordsToAnswerWithoutCurrent = remainingWordsToAnswerList - currentWord


        return if (remainingWordsToAnswerWithoutCurrent.size >= optionsCount) {
            // if there are enough not answered words to create options than we just use them
            remainingWordsToAnswerWithoutCurrent
                .shuffled()
                .take(optionsCount)
                .plus(currentWord)
                .shuffled()
        } else {
            // if there are not enough we shuffle in some of already answered
            remainingWordsToAnswerWithoutCurrent.plus(
                (wordsBank - currentWord - remainingWordsToAnswerWithoutCurrent)
                    .shuffled()
                    .take(optionsCount - remainingWordsToAnswerWithoutCurrent.size)
            ).plus(currentWord).shuffled()
        }
    }
}