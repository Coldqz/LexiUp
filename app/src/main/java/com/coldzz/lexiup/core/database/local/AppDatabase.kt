package com.coldzz.lexiup.core.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coldzz.lexiup.features.blocks.data.local.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.WordBlockOxfordWords
import com.coldzz.lexiup.features.profile.data.local.UserStats
import com.coldzz.lexiup.features.words.data.local.GptAnswers
import com.coldzz.lexiup.features.words.data.local.OxfordWords
import com.coldzz.lexiup.features.words.data.local.WordDefinitions
import com.coldzz.lexiup.features.words.data.local.WordDetails
import com.coldzz.lexiup.features.words.data.local.WordExamples

@Database(
    entities = [
        GptAnswers::class,
        OxfordWords::class,
        UserStats::class,
        WordBlock::class,
        WordBlockOxfordWords::class,
        WordDefinitions::class,
        WordDetails::class,
        WordExamples::class
    ], version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}