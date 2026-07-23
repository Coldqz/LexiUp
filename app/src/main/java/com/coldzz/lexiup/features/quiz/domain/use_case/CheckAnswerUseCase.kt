package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import javax.inject.Inject

class CheckAnswerUseCase @Inject constructor() {
    operator fun invoke(
        answerWordId: Int,
        wordId: Int,
        correctlyAnsweredWordsBank: List<Int>,
        wordBank: List<PickQuizWordsData>
    ): CheckAnswerResult {

        val answerIsCorrect = answerWordId == wordId

        /*
        * We check if it is last word to be answered.
        * If it is the last word and user answer correctly it means that quiz can be finished successfully.
        * */
        val isItLastWordToAnswer = correctlyAnsweredWordsBank.size == wordBank.size - 1

        return if (answerIsCorrect) {
            if (isItLastWordToAnswer) {
                CheckAnswerResult.QuizPassed
            } else {
                CheckAnswerResult.Correct
            }
        } else {
            CheckAnswerResult.Wrong
        }
    }
}