package com.coldzz.lexiup.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.WordBlockDao
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.profile.data.local.UserStats
import com.coldzz.lexiup.features.words.data.local.entities.GptAnswers
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.WordDao
import com.coldzz.lexiup.features.words.data.local.entities.WordDefinitions
import com.coldzz.lexiup.features.words.data.local.entities.WordDetails
import com.coldzz.lexiup.features.words.data.local.entities.WordExamples

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

    abstract fun wordBlockDao(): WordBlockDao
}