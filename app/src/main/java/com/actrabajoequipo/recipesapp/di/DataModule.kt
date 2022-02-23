package com.actrabajoequipo.recipesapp.di

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun recipesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ) = RecipesRepository(localDataSource, remoteDataSource)

    @Provides
    fun userRepositoryProvider(
        remoteDataSource: RemoteDataSource
    ) = UserRepository(remoteDataSource)
}