package com.actrabajoequipo.recipesapp.di

import android.app.Application
import androidx.room.Room
import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.recipesapp.FakeLocalDataSource
import com.actrabajoequipo.recipesapp.FakeRemoteDataSource
import com.actrabajoequipo.recipesapp.data.database.RecipeDatabase
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): RecipeDatabase = Room.databaseBuilder(
        app,
        RecipeDatabase::class.java, "recipe-db"
    ).build()

    @Provides
    @Singleton
    fun localDataSourceProvider(): LocalDataSource = FakeLocalDataSource()

    @Provides
    @Singleton
    fun remoteDataSourceProvider(): RemoteDataSource = FakeRemoteDataSource()

    @Provides
    @Singleton
    fun firebaseManagerProvider() = FirebaseManager()
}