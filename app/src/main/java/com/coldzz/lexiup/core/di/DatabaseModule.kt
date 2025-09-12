package com.coldzz.lexiup.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coldzz.lexiup.core.Constants
import com.coldzz.lexiup.core.database.local.AppDatabase
import com.coldzz.lexiup.core.database.local.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideWordsDao(database: AppDatabase): WordDao {
        return database.wordDao()
    }
}