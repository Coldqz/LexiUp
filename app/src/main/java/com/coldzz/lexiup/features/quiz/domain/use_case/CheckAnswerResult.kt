package com.coldzz.lexiup.features.quiz.domain.use_case

sealed class CheckAnswerResult {
    object Correct: CheckAnswerResult()
    object Wrong: CheckAnswerResult()
    object QuizPassed: CheckAnswerResult()
}