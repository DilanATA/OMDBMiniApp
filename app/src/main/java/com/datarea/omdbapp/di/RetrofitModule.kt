package com.datarea.omdbapp.di

import com.datarea.omdbapp.api.OMDBApi
import com.datarea.omdbapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
open class RetrofitModule {

    val contentType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideRetrofitJson(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonApi(retrofit: Retrofit): OMDBApi {
        return retrofit.create(OMDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttp(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }

    @Provides
    @Singleton
    fun setLogLevel(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.HEADERS

    @Provides
    @Singleton
    fun setupOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .also {
                        it.level = setLogLevel()
                    }
            )
            .followRedirects(true)
            .followSslRedirects(true)
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
    }
}
