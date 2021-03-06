package com.raedzein.assignment.application.di

import android.content.Context
import com.raedzein.assignment.data.api.GitReposService
import com.raedzein.assignment.data.api.MostStarredReposQueryBuilder
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raedzein.assignment.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteApiModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(appContext).build())
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .retryOnConnectionFailure(true)
            .build()

    }

    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext appContext: Context): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(providesOkHttpClient(appContext))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    }

    @Provides
    @Singleton
    fun providesGitReposeService(@ApplicationContext appContext: Context): GitReposService {
        return providesRetrofit(appContext).create(GitReposService::class.java)
    }

    @Provides
    fun providesMostStarredQueryBuilder() = MostStarredReposQueryBuilder()
}