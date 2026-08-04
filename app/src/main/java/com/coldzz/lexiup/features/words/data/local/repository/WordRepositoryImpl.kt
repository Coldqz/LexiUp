package com.coldzz.lexiup.features.words.data.local.repository

import android.util.Log
import com.coldzz.lexiup.core.common.ResultDataState
import com.coldzz.lexiup.core.common.isNetworkError
import com.coldzz.lexiup.core.data.remote.DictionaryApi
import com.coldzz.lexiup.features.words.data.local.WordDao
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import com.coldzz.lexiup.features.words.data.local.projection.WordWithDetails
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.presentation.createPlaceholderDetails
import com.coldzz.lexiup.features.words.presentation.toDatabaseEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "WordRepositoryImpl"

class WordRepositoryImpl @Inject constructor(
    private val dao: WordDao,
    private val dictionaryApi: DictionaryApi
) : WordRepository {

    override suspend fun getWordsCount(): Flow<Int> {
        return dao.getWordsCount()
    }

    override suspend fun insertWords(wordsList: List<OxfordWords>) {
        return dao.insertWords(wordsList)
    }

    override suspend fun getWords(wordIdList: List<Int>): List<OxfordWords> {
        return dao.getWords(wordIdList)
    }

    override fun getAllWordsFlow(): Flow<List<OxfordWords>> {
        return dao.getAllWordsFlow()
    }

    override suspend fun getSingleWordDetailsFlow(
        wordId: Int,
        reviewBlockId: Int
    ): Flow<ResultDataState<WordWithDetails>> {
        return dao.getSingleWordDetailsFlow(wordId, reviewBlockId)
            .onEach { flowValue ->
                if (flowValue.wordDetails == null) {
                    fetchAndCacheWordDetails(
                        word = flowValue.word,
                        wordId = wordId,
                        partOfSpeech = flowValue.partOfSpeech
                    )
                }
            }
            .map { flowValue ->
                // then we map everything to our ResultUiState
                // if flow value(database query) is null(i.e. db have no details data) then emit loading state,
                // else if value is not null then emit that data
                if (flowValue.wordDetails == null) {
                    ResultDataState.Loading
                } else {
                    ResultDataState.Success(flowValue)
                }
            }
            .catch { error ->
                emit(ResultDataState.Error(error))
            }
    }

    override fun getPickQuizWordsFlow(blockId: Int): Flow<ResultDataState<List<PickQuizWordsData>>> {
        var cachedWordIds = emptyList<Int>()

        return dao.getPickQuizWordFlow(blockId).onEach { flowValue ->
            // We check if there are any words that need to be downloaded and cached and do it if they are.
            cachedWordIds = dao.isWordsCached(flowValue.map { it.id })
            flowValue.forEach { element ->
                if (element.id !in cachedWordIds) {
                    // Base delay to prevent http 429 during batch fetch
                    delay(500.milliseconds)
                    fetchAndCacheWordDetails(
                        word = element.word,
                        wordId = element.id,
                        partOfSpeech = element.partOfSpeech
                    )
                }
            }
        }.map { flowValue ->
            /*
            * Here we create list of words which still need to be cached, if there are any we emit loading state.
            * If there are no words to be cached we just emit success and data with it
            * */
            val notCachedWordIds = flowValue.filterNot { it.id in cachedWordIds }
            if (notCachedWordIds.isEmpty()) {
                ResultDataState.Success(flowValue)
            } else {
                ResultDataState.Loading
            }
        }.catch { error ->
            emit(ResultDataState.Error(error))
        }
    }

    override fun getWordsAndReviewBlockIndicator(reviewBlockId: Int): Flow<List<WordsWithReviewBlockIndicator>> {
        return dao.getWordsAndReviewBlockIndicator(reviewBlockId = reviewBlockId)
    }

    override suspend fun getRandomWords(
        limit: Int,
        reviewBlockId: Int,
        avoidIds: List<Int>
    ): List<OxfordWords> {
        return dao.getRandomWords(limit, reviewBlockId, avoidIds)
    }

    private suspend fun fetchAndCacheWordDetails(
        word: String,
        wordId: Int,
        partOfSpeech: String,
        maxRetries: Int = 3
    ) {
        var currentAttempt = 0
        var delayTime = 1000L

        while (currentAttempt <= maxRetries) {
            try {
                val response = dictionaryApi.getWord(word.trim()).toDatabaseEntity(wordId, partOfSpeech)
                dao.insertApiResponse(response.details, response.meanings)
                return
            } catch (e: HttpException) {
                val code = e.code()
                if (code == 404) {
                    Log.e(TAG, "Word not found (404) for word: $word. Inserting placeholder.")
                    val placeholder = createPlaceholderDetails(wordId)
                    dao.insertApiResponse(placeholder.details, placeholder.meanings)
                    return
                }

                if (code in listOf(429, 500, 502, 503, 504)) {
                    currentAttempt++
                    if (currentAttempt > maxRetries) {
                        Log.e(TAG, "Max retries reached for word: $word after HTTP $code")
                        throw e
                    }
                    val waitTime = if (code == 429) 5000L else delayTime
                    Log.w(TAG, "HTTP $code for word: $word. Retry attempt $currentAttempt after ${waitTime}ms...")
                    delay(waitTime.milliseconds)
                    if (code != 429) delayTime *= 2
                } else {
                    Log.e(TAG, "Non-retryable HttpException $code for word: $word")
                    throw e
                }
            } catch (e: Exception) {
                if (e.isNetworkError()) {
                    currentAttempt++
                    if (currentAttempt > maxRetries) {
                        Log.e(TAG, "Max retries reached for word: $word after network error")
                        throw e
                    }
                    Log.w(TAG, "Network error for word: $word. Retry attempt $currentAttempt after ${delayTime}ms...")
                    delay(delayTime.milliseconds)
                    delayTime *= 2
                } else {
                    Log.e(TAG, "Unexpected exception during fetch for word: $word", e)
                    throw e
                }
            }
        }
    }
}
