package com.coldzz.lexiup.features.quiz.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.coldzz.lexiup.core.common.ResultDataState
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.quiz.domain.use_case.CheckAnswerResult
import com.coldzz.lexiup.features.quiz.domain.use_case.QuizUseCases
import com.coldzz.lexiup.features.quiz.presentation.QuizEvent
import com.coldzz.lexiup.features.quiz.presentation.QuizUiState
import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PickQuizViewModel"

/**
 * We can run quiz without saving progress in case with learned blocks for example.
 *
 * Set [saveProgressChanges] to false if you don't want to save progress.
 * */
@HiltViewModel
class PickQuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: QuizUseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<ResultUiState<QuizUiState>> =
        MutableStateFlow(ResultUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<QuizEvent> = MutableSharedFlow()
    val events: SharedFlow<QuizEvent> = _events.asSharedFlow()

    private val blockId = savedStateHandle.toRoute<NavRoutes.PickQuizScreen>().blockId
    private val saveProgressChanges = savedStateHandle.toRoute<NavRoutes.PickQuizScreen>().saveProgressChanges

    private val correctlyAnsweredWordsBank: MutableList<Int> = mutableListOf()

    // list of words for the quiz
    private var wordsBank: List<PickQuizWordsData> = emptyList()
    
    // store names of words that don't have definitions to show a warning in UI
    private var skippedWords: List<String> = emptyList()

    private var getDataJob: Job? = null

    init {
        loadData()
    }

    fun loadData() {
        // cancel flow before we rerun it
        getDataJob?.cancel()

        // here we set our state to loading before any other actions
        _uiState.value = ResultUiState.Loading

        getDataJob = viewModelScope.launch {
            useCases.getPickQuizWord(blockId).collect { flowValue ->
                when (flowValue) {
                    ResultDataState.Loading -> {
                        _uiState.value = ResultUiState.Loading
                    }

                    is ResultDataState.Success -> {
                        // Filter words right here to separate valid ones from placeholders
                        val filterResult = useCases.filterValidQuizWords(flowValue.data)
                        wordsBank = filterResult.validWords
                        skippedWords = filterResult.skippedWords

                        if (wordsBank.isEmpty()) {
                            _uiState.value = ResultUiState.Error(Throwable("No words with definitions found in this block."))
                            return@collect
                        }

                        updateQuiz()
                    }

                    is ResultDataState.Error -> {
                        val throwable = flowValue.throwable
                        Log.e(TAG, "An error occurred: ${throwable.message}")
                        _uiState.value = ResultUiState.Error(throwable)
                    }
                }
            }
        }
    }

    // based on current user progress update quiz with remaining words and new choices
    private fun updateQuiz() {
        _uiState.update { _ ->
            ResultUiState.Success(
                data = useCases.generateQuizStepState(
                    wordsBank = wordsBank,
                    correctlyAnsweredWordsBank = correctlyAnsweredWordsBank,
                ).copy(skippedWords = skippedWords)
            )
        }
    }

    /*
    * Check the answer, saves if it is correct and rerun the quiz, if answer is wrong then just rerun quiz.
    * Also, it ends the quiz when there are no words to answer.
    * */
    fun checkAnswer(answerWordId: Int) {
        val state = (_uiState.value as? ResultUiState.Success) ?: return
        val wordId = state.data.currentWordId ?: return

        val result = useCases.checkAnswer(
            answerWordId = answerWordId,
            wordId = wordId,
            correctlyAnsweredWordsBank = correctlyAnsweredWordsBank,
            wordBank = wordsBank
        )

        viewModelScope.launch {
            when(result) {
                CheckAnswerResult.Correct -> {
                    correctlyAnsweredWordsBank.add(wordId)
                    _events.emit(QuizEvent.ShowToast("Correct answer"))
                    updateQuiz()
                }

                CheckAnswerResult.QuizPassed -> {
                    if (saveProgressChanges) {
                        useCases.endQuiz(blockId = blockId)
                    }
                    showCongratulationDialog()
                    return@launch
                }

                CheckAnswerResult.Wrong -> {
                    _events.emit(QuizEvent.ShowToast("Wrong answer"))
                    updateQuiz()
                }
            }
        }
    }

    fun showCongratulationDialog() {
        _uiState.update { oldState ->
            if (oldState is ResultUiState.Success) {
                oldState.copy(
                    data = oldState.data.copy(
                        showCongratulationDialog = true
                    )
                )
            } else oldState
        }
    }

    fun closeQuiz() {
        viewModelScope.launch {
            _events.emit(QuizEvent.CloseQuiz)
        }
    }
}
