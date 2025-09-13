package com.coldzz.lexiup.core.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.coldzz.lexiup.core.Constants
import com.coldzz.lexiup.core.data.local.prepopulate.WordsPrepopulatedModel
import com.coldzz.lexiup.core.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.data.local.LevelCerf
import com.coldzz.lexiup.features.words.data.local.OxfordWords
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// class for populating db with data from json file on background thread
class PopulateDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    @Inject val moshi: Moshi,
    @Inject val repository: WordRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.i(TAG, "Work request triggered")
        // getting filename from input data and checking if our json file exists
        val filename = inputData.getString(KEY_FILENAME)
        try {
            if (filename != null) {
                val jsonAdapter: JsonAdapter<WordsPrepopulatedModel> =
                    moshi.adapter(WordsPrepopulatedModel::class.java)
                val wordsString =
                    applicationContext.assets.open("WordsJson.json").bufferedReader()
                        .use { it.readText() }
                val words = jsonAdapter.fromJson(wordsString)
                words?.let {
                    for (element in words.a1) {
                        repository.addWord(
                            OxfordWords(
                                word = element,
                                level = LevelCerf.A1
                            )
                        )
                    }
                    for (element in words.a2) {
                        repository.addWord(
                            OxfordWords(
                                word = element,
                                level = LevelCerf.A2
                            )
                        )
                    }
                    for (element in words.b1) {
                        repository.addWord(
                            OxfordWords(
                                word = element,
                                level = LevelCerf.B1
                            )
                        )
                    }
                    for (element in words.b2) {
                        repository.addWord(
                            OxfordWords(
                                word = element,
                                level = LevelCerf.B2
                            )
                        )
                    }
                }
                Result.success()
            } else {
                Log.e(TAG, "Error populating database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error populating database", ex)
            Result.failure()
        }

    }

    companion object {
        private const val TAG = "PopulateDataWorker"
        const val KEY_FILENAME = Constants.WORDS_JSON
    }
}