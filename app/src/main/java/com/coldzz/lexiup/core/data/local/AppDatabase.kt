package com.coldzz.lexiup.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coldzz.lexiup.features.blocks.data.local.WordBlockDao
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockOxfordWords
import com.coldzz.lexiup.features.stats.data.local.StatsDao
import com.coldzz.lexiup.features.user.data.local.UserDao
import com.coldzz.lexiup.features.user.data.local.entities.UserStats
import com.coldzz.lexiup.features.words.data.local.WordDao
import com.coldzz.lexiup.features.words.data.local.entities.GptAnswers
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordDetails
import com.coldzz.lexiup.features.words.data.local.entities.WordMeaning

@Database(
    entities = [
        GptAnswers::class,
        OxfordWords::class,
        UserStats::class,
        WordBlock::class,
        WordBlockOxfordWords::class,
        WordMeaning::class,
        WordDetails::class,
    ], version = 1
)
@TypeConverters(AppTypeConverters:: class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    abstract fun wordBlockDao(): WordBlockDao

    abstract fun userDao(): UserDao

    abstract fun statsDao(): StatsDao
}