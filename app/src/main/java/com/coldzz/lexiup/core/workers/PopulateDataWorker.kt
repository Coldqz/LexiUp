package com.coldzz.lexiup.core.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.words.data.local.prepopulate.WordsPrepopulatedModel
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.domain.model.LevelCerf
import com.coldzz.lexiup.features.words.domain.model.OxfordWords
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// worker for populating db with data from json file on background thread
@HiltWorker
class PopulateDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    val moshi: Moshi,
    val repository: WordRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.i(TAG, "Work request triggered")
        try {
            // reading data from json file
            val jsonAdapter: JsonAdapter<WordsPrepopulatedModel> =
                moshi.adapter(WordsPrepopulatedModel::class.java)
            val wordsString = applicationContext.assets.open(Constants.WORDS_JSON).bufferedReader()
                .use { it.readText() }
            val words = jsonAdapter.fromJson(wordsString)

            // creating list of OxfordWords out of json to bulk insert it into the db
            val wordsList = mutableListOf<OxfordWords>()
            words?.let {
                val levels = listOf(
                    LevelCerf.A1 to it.a1,
                    LevelCerf.A2 to it.a2,
                    LevelCerf.B1 to it.b1,
                    LevelCerf.B2 to it.b2
                )

                levels.flatMap { (level, elements) ->
                    elements.map { word -> OxfordWords(word = word, level = level) }
                }.let { generatedWords ->
                    wordsList.addAll(generatedWords)
                }
            }
            repository.insertAllWords(wordsList)

            Log.i(TAG, "Work request ended")
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error populating database", ex)
            Result.failure()
        }

    }

    companion object {
        private const val TAG = "PopulateDataWorker"
    }
}