package com.coldzz.lexiup.core.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.coldzz.lexiup.core.Constants
import com.coldzz.lexiup.core.data.local.prepopulate.WordsPrepopulatedModel
import com.coldzz.lexiup.core.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.data.local.LevelCerf
import com.coldzz.lexiup.features.words.data.local.OxfordWords
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// class for populating db with data from json file on background thread
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
            val jsonAdapter: JsonAdapter<WordsPrepopulatedModel> =
                moshi.adapter(WordsPrepopulatedModel::class.java)
            val wordsString = applicationContext.assets.open(Constants.WORDS_JSON).bufferedReader()
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