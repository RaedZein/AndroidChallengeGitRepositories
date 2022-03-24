package com.raedzein.assignment.application.di

import com.raedzein.assignment.domain.repositories.Repository
import com.raedzein.assignment.data.repositories.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(repositoryImpl: RepositoryImpl): Repository
}