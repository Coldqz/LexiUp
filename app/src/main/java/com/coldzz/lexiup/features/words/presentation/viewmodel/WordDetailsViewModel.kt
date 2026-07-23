package com.coldzz.lexiup.features.words.presentation.viewmodel

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.coldzz.lexiup.core.common.ResultDataState
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases.WordBlockUseCases
import com.coldzz.lexiup.features.words.domain.use_case.WordUseCases
import com.coldzz.lexiup.features.words.presentation.WordDetailsEvent
import com.coldzz.lexiup.features.words.presentation.WordDetailsUiState
import com.coldzz.lexiup.features.words.presentation.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "WordDetailsViewModel"

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
    private val wordUseCases: WordUseCases,
    private val blockUseCases: WordBlockUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var getDataJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioJob: Job? = null

    private val _uiState =
        MutableStateFlow<ResultUiState<WordDetailsUiState>>(ResultUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<WordDetailsEvent>()
    val events = _events.asSharedFlow()

    init {
        loadData()
    }

    fun playBritishAudio(audioUrl: String) {
        playAudio(
            audioUrl = audioUrl,
            actionOnStartLoading = {
                _uiState.update { oldState ->
                    if (oldState is ResultUiState.Success) {
                        oldState.copy(
                            data = oldState.data.copy(
                                isUkAudioLoading = true
                            )
                        )
                    } else {
                        oldState
                    }
                }
            },
            actionOnLoaded = {
                _uiState.update { oldState ->
                    if (oldState is ResultUiState.Success) {
                        oldState.copy(
                            data = oldState.data.copy(
                                isUkAudioLoading = false
                            )
                        )
                    } else oldState
                }
            }
        )
    }

    fun playAmericanAudio(audioUrl: String) {
        playAudio(
            audioUrl = audioUrl,
            actionOnStartLoading = {
                // start loading indicator
                _uiState.update { oldState ->
                    if (oldState is ResultUiState.Success) {
                        oldState.copy(
                            data = oldState.data.copy(
                                isUsAudioLoading = true
                            )
                        )
                    } else {
                        oldState
                    }
                }
            },
            actionOnLoaded = {
                // stop loading indicator
                _uiState.update { oldState ->
                    if (oldState is ResultUiState.Success) {
                        oldState.copy(
                            data = oldState.data.copy(
                                isUsAudioLoading = false
                            )
                        )
                    } else oldState
                }
            }
        )
    }

    private fun playAudio(
        audioUrl: String,
        actionOnStartLoading: () -> Unit,
        actionOnLoaded: () -> Unit
    ) {
        if (audioUrl.isBlank()) return

        // If media player is currently playing, ignore new play requests
        if (mediaPlayer?.isPlaying == true) {
            return
        }

        // Cancel any existing audio job before starting a new one
        audioJob?.cancel()

        try {
            // Release any existing player (even if not playing) before creating a new one
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                //action on start loading
                actionOnStartLoading()
                setDataSource(audioUrl)
                setOnPreparedListener {
                    audioJob = viewModelScope.launch {
                        // UX delay to prevent ui flashing
                        delay(200.milliseconds)
                        // Trigger the loaded action once the media is ready
                        actionOnLoaded()
                        // Begin audio playback
                        start()
                        Log.d(TAG, "Audio was started")
                    }
                }
                // Here we have started the loading process
                prepareAsync()
                setOnCompletionListener {
                    // We should ensure it's ready for the next call or reset.
                    Log.d(TAG, "Audio finished and released")
                }
                setOnErrorListener { _, what, extra ->
                    actionOnLoaded()
                    Log.e(TAG, "Audio error: $what, extra: $extra")

                    val message = when (extra) {
                        MediaPlayer.MEDIA_ERROR_IO -> "Network error. Check your connection."
                        MediaPlayer.MEDIA_ERROR_TIMED_OUT -> "Connection timed out."
                        // Int.MIN_VALUE (-2147483648) often means "System/IO failure"
                        // but is too generic to blame ONLY the internet.
                        Int.MIN_VALUE -> "Unable to load audio. Check your connection or try again later."
                        else -> "An unexpected error occurred. Try again later"
                    }
                    viewModelScope.launch {
                        _events.emit(WordDetailsEvent.ShowToast(message))
                    }
                    true
                }
            }
        } catch (e: Exception) {
            // reset the loading indicator
            actionOnLoaded()
            Log.e(TAG, "Error playing audio: $e")
        }
    }

    fun addWordToReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blockUseCases.addWordToReviewBlock(wordId)
        }
    }

    fun removeWordFromReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blockUseCases.removeWordFromReviewBlock(wordId)
        }
    }

    fun loadData() {
        // cancel flow before we rerun it
        getDataJob?.cancel()

        // here we set our state to loading before any other actions
        _uiState.value = ResultUiState.Loading

        // when user in case of connection error press try again
        // we cancel previous collect and run the new one to trigger api request
        getDataJob = viewModelScope.launch {
            val wordId = savedStateHandle.toRoute<NavRoutes.WordDetailsScreen>().wordId

            wordUseCases.getWordDetailsUseCase(
                wordId = wordId,
            ).collect { wordWithDetails ->
                _uiState.value = when (wordWithDetails) {

                    ResultDataState.Loading -> {
                        ResultUiState.Loading
                    }

                    is ResultDataState.Success -> {
                        ResultUiState.Success(wordWithDetails.data.toUiState())
                    }

                    is ResultDataState.Error -> {
                        Log.e(TAG, "An error occurred: ${wordWithDetails.throwable.message}")
                        ResultUiState.Error(throwable = wordWithDetails.throwable)
                    }
                }
            }
        }
    }

    fun closeScreen() {
        viewModelScope.launch {
            _events.emit(WordDetailsEvent.CloseScreen)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}