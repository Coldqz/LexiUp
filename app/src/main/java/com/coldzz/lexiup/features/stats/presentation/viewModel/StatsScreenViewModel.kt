package com.coldzz.lexiup.features.stats.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.features.stats.data.local.repository.StatsRepository
import com.coldzz.lexiup.features.stats.domain.StatsUseCases
import com.coldzz.lexiup.features.stats.presentation.LevelProgressDataModel
import com.coldzz.lexiup.features.stats.presentation.StatsScreenEvent
import com.coldzz.lexiup.features.stats.presentation.StatsScreenUiState
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "StatsScreenViewModel"

@HiltViewModel
class StatsScreenViewModel @Inject constructor(
    private val statsUseCases: StatsUseCases,
    private val wordRepository: WordRepository,
    private val statsRepository: StatsRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ResultUiState<StatsScreenUiState>> =
        MutableStateFlow(ResultUiState.Loading)
    val uiState: StateFlow<ResultUiState<StatsScreenUiState>> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<StatsScreenEvent>()
    val events = _events.asSharedFlow()

    private var getDataJob: Job? = null

    init {
        loadData()
    }

    fun closeScreen() {
        viewModelScope.launch {
            _events.emit(StatsScreenEvent.CloseScreen)
        }
    }

    fun loadData() {
        getDataJob?.cancel()

        getDataJob = viewModelScope.launch {
            combine(
                wordRepository.getWordsCount(),
                statsRepository.getLearnedWordsNumberFromAllBlocks(),
                statsRepository.getTotalNumberOfWordsByCerfLevels()
            ) { totalWordsNumber, learnedWordsNumber, totalWordNumbersByCerfLevels ->
                StatsScreenUiState(

                    currentlyLearnedWords = learnedWordsNumber,
                    // if this is zero then we just pass max words number to learn
                    remainingWords = if (learnedWordsNumber != 0) {
                        totalWordsNumber - learnedWordsNumber
                    } else totalWordsNumber,

                    totalWordsNumber = totalWordsNumber,

                    learnedPercentage = statsUseCases.calculateProgressPercentageUseCase(
                        totalWords = totalWordsNumber,
                        currentlyLearnedWords = learnedWordsNumber
                    ),
                    //map db result to uiModel
                    levelProgressDataModel = totalWordNumbersByCerfLevels.map { flowValue ->
                        LevelProgressDataModel(
                            level = flowValue.level,
                            percentage = statsUseCases.calculateProgressPercentageUseCase(
                                currentlyLearnedWords = flowValue.learnedWords,
                                totalWords = flowValue.totalWords
                            )
                        )
                    }
                )
            }
                .map { state ->
                    ResultUiState.Success(state) as ResultUiState<StatsScreenUiState>
                }
                .onStart {
                    emit(ResultUiState.Loading)
                    // UX delay to avoid flashing loading state
                    delay(200.milliseconds)
                }.catch { e ->
                    Log.e(TAG, "An error occurred: ${e.message}")
                    emit(ResultUiState.Error(e))
                }.collect {
                    _uiState.value = it
                }
        }
    }
}