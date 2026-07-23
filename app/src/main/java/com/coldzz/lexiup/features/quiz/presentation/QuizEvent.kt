package com.coldzz.lexiup.features.quiz.presentation

sealed interface QuizEvent {
    data class ShowToast(val message: String) : QuizEvent
    object CloseQuiz: QuizEvent
}