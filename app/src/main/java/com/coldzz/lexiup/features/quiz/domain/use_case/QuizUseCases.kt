package com.coldzz.lexiup.features.quiz.domain.use_case

import javax.inject.Inject

class QuizUseCases @Inject constructor(
    val getPickQuizWord: GetPickQuizWordUseCase,
    val generateQuizStepState: GenerateQuizStepStateUseCase,
    val checkAnswer: CheckAnswerUseCase,
    val endQuiz: EndQuizUseCase,
    val filterValidQuizWords: FilterValidQuizWordsUseCase
)