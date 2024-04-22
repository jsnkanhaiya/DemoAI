package com.example.demoai.di

import com.example.demoai.datasource.moshiadapter.PairAdapterFactoryMoshi
import com.example.demoai.datasource.remote.APISolutionsDataSource
import com.example.demoai.datasource.remote.IpmSolutionsDataSourceImpl
import com.example.demoai.datasource.remote.api.APISolutionsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Singleton
    @Provides
    fun providesAPISolutionsDataSource(
        apiSolutionsService: APISolutionsService
    ): APISolutionsDataSource = IpmSolutionsDataSourceImpl(apiSolutionsService)

    @Singleton
    @Provides
    fun providesCoreRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient().newBuilder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).add(PairAdapterFactoryMoshi())
                        .build()
                ).asLenient()
                    .withNullSerialization()
            )
            .client(
                httpClient
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit): APISolutionsService {
        return retrofit.create(APISolutionsService::class.java)
    }

}