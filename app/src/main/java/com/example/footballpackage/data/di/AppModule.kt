package com.example.footballpackage.data.di

import android.app.Application
import androidx.room.Room
import com.example.footballpackage.data.FootballAPI
import com.example.footballpackage.data.local.FootballDao
import com.example.footballpackage.data.local.FootballDatabase
import com.example.footballpackage.data.repository.FootballFixturesRepositoryImpl
import com.example.footballpackage.repository.FootballFixturesRepository
import com.example.footballpackage.utils.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideFootballFixturesApi(client: OkHttpClient): FootballAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(FootballAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideFootballFixturesDatabase(app: Application): FootballDatabase {
        return Room.databaseBuilder(
            app,
            FootballDatabase::class.java,
            "football_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFootballFixturesRepository(dao: FootballDao, api: FootballAPI): FootballFixturesRepository {
        return FootballFixturesRepositoryImpl(dao, api)
    }

    @Singleton
    @Provides
    fun providesFootballFixturesDao(database: FootballDatabase): FootballDao {
        return database.footballFixturesDao
    }
}