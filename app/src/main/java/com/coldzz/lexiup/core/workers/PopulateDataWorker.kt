package com.coldzz.lexiup.core.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.words.data.local.prepopulate.WordsPrepopulatedModel
import com.coldzz.lexiup.features.words.data.local.entities.LevelCerf
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
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
            val type = Types.newParameterizedType(List::class.java, WordsPrepopulatedModel::class.java)
            val jsonAdapter = moshi.adapter<List<WordsPrepopulatedModel>>(type)
            val wordsString = applicationContext.assets.open(Constants.WORDS_JSON).bufferedReader()
                .use { it.readText() }
            val wordsList = jsonAdapter.fromJson(wordsString)

            // creating list of OxfordWords out of json to bulk insert it into the db
            wordsList?.map { element ->
                OxfordWords(
                    word = element.word,
                    partOfSpeech = element.partOfSpeech,
                    level = when (element.level) {
                        "a1" -> LevelCerf.A1
                        "a2" -> LevelCerf.A2
                        "b1" -> LevelCerf.B1
                        "b2" -> LevelCerf.B2
                        "c1" -> LevelCerf.C1
                        "c2" -> LevelCerf.C2
                        else -> throw IllegalArgumentException("Unknown cerf level: ${element.level}, word: ${element.word}")
                    }
                )
            }?.let { generatedWords ->
                repository.insertAllWords(generatedWords)
            }

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