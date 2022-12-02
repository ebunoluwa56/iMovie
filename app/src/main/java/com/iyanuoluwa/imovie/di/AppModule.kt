package com.iyanuoluwa.imovie.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iyanuoluwa.imovie.data.local.MovieDao
import com.iyanuoluwa.imovie.data.local.MovieDatabase
import com.iyanuoluwa.imovie.data.remote.MovieApi
import com.iyanuoluwa.imovie.ui.main.MovieRepository
import com.iyanuoluwa.imovie.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit.Builder): MovieApi {
        return retrofit
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDb(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(movieDatabase: MovieDatabase): MovieDao{
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        movieDao: MovieDao
    ) : MovieRepository = MovieRepository(movieDao, movieApi)
}