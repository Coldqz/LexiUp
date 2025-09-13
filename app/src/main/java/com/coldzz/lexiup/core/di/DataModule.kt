package com.coldzz.lexiup.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.coldzz.lexiup.core.Constants
import com.coldzz.lexiup.core.data.local.AppDatabase
import com.coldzz.lexiup.core.data.local.WordDao
import com.coldzz.lexiup.core.data.repository.WordRepositoryImpl
import com.coldzz.lexiup.core.domain.repository.WordRepository
import com.coldzz.lexiup.core.workers.PopulateDataWorker
import com.coldzz.lexiup.core.workers.PopulateDataWorker.Companion.KEY_FILENAME
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).addCallback(
            object: RoomDatabase.Callback() {
                override fun onCreate(connection: SQLiteConnection) {
                    super.onCreate(connection)
                    val request = OneTimeWorkRequestBuilder<PopulateDataWorker>()
                        .setInputData(workDataOf(KEY_FILENAME to Constants.WORDS_JSON))
                        .build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            }
        )
            .build()
    }

    @Provides
    fun provideWordsDao(database: AppDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    @Singleton
    fun provideWordRepository(dao: WordDao): WordRepository {
        return WordRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}