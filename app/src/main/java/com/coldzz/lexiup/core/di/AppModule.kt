package com.coldzz.lexiup.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.core.data.local.AppDatabase
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.coldzz.lexiup.core.workers.PopulateDataWorker
import com.coldzz.lexiup.features.words.data.local.repository.WordRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Creates and schedule inserting initial data into the db right on db creation
                    val workRequest = OneTimeWorkRequest.Builder(PopulateDataWorker::class.java).build()
                    WorkManager.getInstance(context.applicationContext).enqueue(workRequest)
                }
            }
        ).build()
    }

    @Provides
    @Singleton
    fun provideWordRepository(database: AppDatabase): WordRepository {
        return WordRepositoryImpl(database.wordDao())
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}