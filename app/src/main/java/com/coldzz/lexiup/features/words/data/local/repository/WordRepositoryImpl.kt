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
                    // when we have no data in the DB
                    try {
                        // run api request and write answer into the DB
                        val response =
                            dictionaryApi.getWord(flowValue.word.trim())
                                .toDatabaseEntity(wordId, flowValue.partOfSpeech)
                        dao.insertApiResponse(response.details, response.meanings)
                    } catch (e: HttpException) {
                        // catch any api errors
                        when (e.code()) {
                            404 -> {
                                Log.e(TAG, "Word not found (404) for wordId: $wordId. Inserting placeholder.")
                                // case when api doesn't have this definition
                                val placeholder = createPlaceholderDetails(wordId)
                                dao.insertApiResponse(placeholder.details, placeholder.meanings)
                            }
                            429 -> {
                                Log.w(TAG, "Rate limited (429) for wordId: $wordId. Retrying...")
                                // case when api returns "too many requests", we wait and retry
                                delay(2500.milliseconds)
                                try {
                                    val response = dictionaryApi.getWord(flowValue.word.trim())
                                        .toDatabaseEntity(wordId, flowValue.partOfSpeech)
                                    dao.insertApiResponse(response.details, response.meanings)
                                } catch (retryException: Exception) {
                                    Log.e(TAG, "Retry failed for wordId: $wordId after 429.", retryException)
                                    // If retry fails, we can either throw and insert a placeholder to avoid infinite loading
                                    val placeholder = createPlaceholderDetails(wordId)
                                    dao.insertApiResponse(placeholder.details, placeholder.meanings)
                                }
                            }
                            else -> {
                                Log.e(TAG, "HttpException ${e.code()} for wordId: $wordId")
                                throw e
                            }
                        }
                    }
                    catch (otherException: Exception) {
                        if (otherException.isNetworkError()) {
                            Log.e(TAG, "IOException during fetch for word: ${flowValue.word}", otherException)
                        } else {
                            Log.e(TAG, "Unexpected exception during fetch for word: ${flowValue.word}", otherException)
                        }
                        throw otherException
                    }
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
                    try {
                        // Base delay to prevent http 429
                        delay(500.milliseconds)
                        val response =
                            dictionaryApi.getWord(element.word.trim()).toDatabaseEntity(
                                element.id,
                                element.partOfSpeech
                            )
                        dao.insertApiResponse(response.details, response.meanings)
                    } catch (e: HttpException) {
                        if (e.code() == 404) {
                            Log.e(TAG, "Word not found (404) for element: ${element.word} (ID: ${element.id})")
                            val placeholder = createPlaceholderDetails(element.id)
                            dao.insertApiResponse(placeholder.details, placeholder.meanings)
                        } else if (e.code() == 429) {
                            Log.w(TAG, "Rate limited (429) for element: ${element.word}. Retrying...")
                            // On 429, wait longer and try again
                            delay(5000.milliseconds)
                            try {
                                val response = dictionaryApi.getWord(element.word.trim()).toDatabaseEntity(
                                    element.id,
                                    element.partOfSpeech
                                )
                                dao.insertApiResponse(response.details, response.meanings)
                            } catch (e2: Exception) {
                                Log.e(TAG, "Retry failed for element: ${element.word} after 429.", e2)
                                // If it still fails after retry, insert placeholder to stop infinite loading
                                val placeholder = createPlaceholderDetails(element.id)
                                dao.insertApiResponse(placeholder.details, placeholder.meanings)
                            }
                        } else {
                            Log.e(TAG, "HttpException ${e.code()} during batch fetch for word: ${element.word}")
                            throw e
                        }
                    } catch (otherException: Exception) {
                        if (otherException.isNetworkError()) {
                            Log.e(TAG, "IOException during fetch for word: ${element.word}", otherException)
                        } else {
                            Log.e(TAG, "Unexpected exception during fetch for word: ${element.word}", otherException)
                        }
                        throw otherException
                    }
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
}