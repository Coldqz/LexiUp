package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.quiz.presentation.QuizUiState
import com.coldzz.lexiup.features.quiz.presentation.components.AnswerOptionUiModel
import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import javax.inject.Inject

class GenerateQuizStepStateUseCase @Inject constructor(
    val generateOptions: GenerateOptionsUseCase,
    val pickRandomWord: PickRandomWordUseCase
) {
    operator fun invoke(
        wordsBank: List<PickQuizWordsData>,
        correctlyAnsweredWordsBank: List<Int>,
    ): QuizUiState {

        val currentWord = pickRandomWord(
            wordsBank = wordsBank,
            correctlyAnsweredWordsBank = correctlyAnsweredWordsBank
        )

        val currentWordId = currentWord.id
        val duplicates = wordsBank.groupBy { it.word }.filter { it.value.size > 1 }.keys
        val optionsCountWithoutCorrectOne = Constants.QUIZ_ANSWER_OPTIONS_COUNT - 1

        val options = generateOptions(
            wordsBank = wordsBank,
            correctlyAnsweredWordsBank = correctlyAnsweredWordsBank,
            currentWord = currentWord,
            optionsCount = optionsCountWithoutCorrectOne
        )

        // Find a valid definition to show from the current word
        val definitionToShow = currentWord.definition.find { 
            it.definition != "No definition found for this word in dictionary." &&
            it.definition != "No definitions for this part of speech were found"
        }?.definition ?: currentWord.definition.firstOrNull()?.definition ?: "No definition available"

        return QuizUiState(
            choices = options.map { element ->
                if (element.word in duplicates) {
                    AnswerOptionUiModel(element.id, element.word, element.partOfSpeech)
                } else {
                    AnswerOptionUiModel(element.id, element.word)
                }
            },
            definition = definitionToShow,
            partOfSpeech = currentWord.partOfSpeech,
            currentProgressValue = correctlyAnsweredWordsBank.size,
            maxProgressValue = wordsBank.size,
            currentWordId = currentWordId,
        )
    }
}