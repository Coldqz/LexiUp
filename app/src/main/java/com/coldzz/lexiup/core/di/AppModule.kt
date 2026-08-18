package com.coldzz.lexiup.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.core.data.datastore.DataStoreManager
import com.coldzz.lexiup.core.data.datastore.DataStoreManagerImpl
import com.coldzz.lexiup.core.data.local.AppDatabase
import com.coldzz.lexiup.core.data.remote.DictionaryApi
import com.coldzz.lexiup.core.data.remote.WiktionaryApi
import com.coldzz.lexiup.core.workers.PopulateDataWorker
import com.coldzz.lexiup.features.blocks.data.local.repository.WordBlockRepositoryImpl
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.stats.data.local.repository.StatsRepository
import com.coldzz.lexiup.features.stats.data.local.repository.StatsRepositoryImpl
import com.coldzz.lexiup.features.user.data.local.repository.UserRepository
import com.coldzz.lexiup.features.user.data.local.repository.UserRepositoryImpl
import com.coldzz.lexiup.features.words.data.local.repository.WordRepositoryImpl
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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
    fun provideWordRepository(database: AppDatabase, dictionaryApi: DictionaryApi, wiktionaryApi: WiktionaryApi): WordRepository {
        return WordRepositoryImpl(database.wordDao(),dictionaryApi, wiktionaryApi)
    }

    @Provides
    @Singleton
    fun provideWordBlockRepository(database: AppDatabase): WordBlockRepository {
        return WordBlockRepositoryImpl(database.wordBlockDao())
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: AppDatabase): UserRepository {
        return UserRepositoryImpl(database.userDao())
    }

    @Provides
    @Singleton
    fun provideStatsRepository(database: AppDatabase): StatsRepository {
        return StatsRepositoryImpl(database.statsDao())
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    @DictionaryRetrofit
    fun provideDictionaryRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @WiktionaryRetrofit
    fun provideWiktionaryRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "LexiUpApp/1.0 (arsen.tsiurak@gmail.com)")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://en.wiktionary.org/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWiktionaryApi(@WiktionaryRetrofit retrofit: Retrofit): WiktionaryApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(@DictionaryRetrofit retrofit: Retrofit): DictionaryApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(
        @ApplicationContext context: Context
    ): DataStoreManager {
        return DataStoreManagerImpl(
            dataStore = context.dataStore
        )
    }
}